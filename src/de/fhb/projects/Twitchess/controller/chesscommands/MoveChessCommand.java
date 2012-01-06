package de.fhb.projects.Twitchess.controller.chesscommands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.controller.UCIEngineInterface;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.exception.UCIException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class MoveChessCommand implements ChessCommand {
	public static String commandText = "move";
	protected ChessStateDAOInterface dao;
	protected UCIEngineInterface uciEngine;

	protected static enum MoveType {
		AI, PLAYER, AI_AFTER_PLAYER;
	}

	public MoveChessCommand(ChessStateDAOInterface dao,
			UCIEngineInterface uciEngine) {
		setDao(dao);
		setUciEngine(uciEngine);
	}

	@Override
	public String processInput(String player, List<String> parameters)
			throws ChessManagerException {
		String result = null;
		MoveType t = null;
		Move playersMove;
		Move aiMove;

		if (parameters == null || parameters.size() < 1
				|| parameters.size() > 2) {
			throwCommandSyntaxException();
		}

		t = parseMoveType(parameters);
		playersMove = parseMove(parameters, t);

		if (t == null && playersMove != null)
			t = MoveType.AI_AFTER_PLAYER;

		ChessStateVO vo = getCurrentGame(player);
		Fen fen = new Fen(vo.getFen());

		GameState state = fen.getGameState();

		if ((t == MoveType.PLAYER || t == MoveType.AI_AFTER_PLAYER)
				&& playersMove != null) {
			try {
				ChessLogic.isValidMove(state, playersMove);
			} catch (RuntimeException e) {
				throw new ChessManagerException("Your move is invalid!");
			}

			state = new GameState(state, playersMove);
			fen = new Fen(state);
			result = "Your move has been executed.";
		}

		if (t == MoveType.AI || t == MoveType.AI_AFTER_PLAYER) {

			try {
				aiMove = calculateMove(fen);
			} catch (Throwable e) {
				throw new ChessManagerException(
						"Error while calculating your move: " + e.getMessage());
			}

			state = new GameState(state, aiMove);
			fen = new Fen(state);

			System.out.println("POS AFTER COMPUTER MOVE: " + fen.getFen());
		}

		vo.setFen(fen.getFen());

		try {
			dao.updateTable(vo);
		} catch (SQLException e) {
			throw new ChessManagerException(
					"Error while writing to the database.");
		}

		return result;
	}
 
	protected ChessStateVO getCurrentGame(String player)
			throws ChessManagerException {
		List<ChessStateVO> listVO;
		try {
			listVO = dao.findNotFinishedGameByPlayer(player);
		} catch (SQLException e) {
			throw new ChessManagerException(
					"Error while loading from the database.");
		}

		if (listVO == null || listVO.size() > 1) {
			throw new ChessManagerException(
					"You must have a single ongoing game in order to do a move.");
		}
		return listVO.get(0);
	}
	protected Move parseMove(List<String> parameters, MoveType t)
			throws ChessManagerException {
		Move playersMove = null;

		if (t != MoveType.AI) {
			if (t == null) {
				playersMove = getMove(parameters.get(0));
			} else if (parameters.size() != 2) {
				throwCommandSyntaxException();
			} else {
				playersMove = getMove(parameters.get(1));
			}
		}

		return playersMove;
	}

	protected MoveType parseMoveType(List<String> parameters)
			throws ChessManagerException {
		MoveType t = null;
		String p = parameters.get(0);

		if (p.equalsIgnoreCase("ai")) {
			if (parameters.size() != 1)
				throwCommandSyntaxException();
			t = MoveType.AI;
		} else if (p.equalsIgnoreCase("player")) {
			if (parameters.size() != 2)
				throwCommandSyntaxException();
			t = MoveType.PLAYER;
		} else if (p.equalsIgnoreCase("both")) {
			if (parameters.size() != 2)
				throwCommandSyntaxException();
			t = MoveType.AI_AFTER_PLAYER;
		}

		return t;
	}

	protected void throwCommandSyntaxException() throws ChessManagerException {
		throw new ChessManagerException("Error! \"" + commandText
				+ " ([both|player] <move>) | [ai]\" expected");
	}

	protected Move calculateMove(Fen fen) throws IOException, UCIException,
			Throwable, ChessManagerException {
		Move move;
		uciEngine.init();
		String calculatedMove = uciEngine.calculateMove(fen.getFen(), 2000);
		uciEngine.destroy();

		move = getMove(calculatedMove);
		return move;
	}

	protected Move getMove(String s) throws ChessManagerException {
		Position start = null, destination = null;
		if (!s.matches("([a-hA-H][1-8]){2}"))
			throw new ChessManagerException("Invalid move. Could not parse it.");

		for (int i = 0; i < 2; ++i) {
			int x = Character.toLowerCase(s.charAt(i * 2)) - 'a';
			int y = s.charAt(i * 2 + 1) - '1';

			if (i == 0)
				start = new Position(x, y);
			else
				destination = new Position(x, y);
		}

		return new Move(start, destination);
	}

	public ChessStateDAOInterface getDao() {
		return dao;
	}

	public void setDao(ChessStateDAOInterface dao) {
		this.dao = dao;
	}

	public UCIEngineInterface getUciEngine() {
		return uciEngine;
	}

	public void setUciEngine(UCIEngineInterface uciEngine) {
		this.uciEngine = uciEngine;
	}

}
