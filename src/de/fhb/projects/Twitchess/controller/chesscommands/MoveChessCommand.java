package de.fhb.projects.Twitchess.controller.chesscommands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.controller.UCIEngineInterface;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.data.ResultType;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.exception.ChessManagerGameAlreadyOverException;
import de.fhb.projects.Twitchess.exception.UCIException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;

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

		if (setResultInChessState(vo, state)) {
			updateInDatabase(vo);
			throw new ChessManagerGameAlreadyOverException(
					"Game's already ended!");
		}

		if ((t == MoveType.PLAYER || t == MoveType.AI_AFTER_PLAYER)
				&& playersMove != null) {

			try {
				ChessLogic.isValidMove(state, playersMove);
			} catch (RuntimeException e) {
				throw new ChessManagerException("Your move is invalid!");
			}

			state = new GameState(state, playersMove);

			if (setResultInChessState(vo, state)) {
				if (vo.getResult() == ResultType.REMIS.getNumber())
					result = "Game's remis! Well played.";
				else
					result = "You won the game! Congratulations!";
			} else {
				result = "Your move has been executed.";
			}
		}

		if ((t == MoveType.AI || t == MoveType.AI_AFTER_PLAYER)
				&& vo.getResult() == null) {
			fen = new Fen(state);
			try {
				aiMove = calculateMove(fen);
			} catch (Throwable e) {
				throw new ChessManagerException(
						"Error while calculating your move: " + e.getMessage());
			}

			try {
				ChessLogic.isValidMove(state, aiMove);
			} catch (RuntimeException e) {
				throw new ChessManagerException("Computer's move is invalid!");
			}

			state = new GameState(state, aiMove);

			result = "Computer move: " + aiMove.getLongNotation();

			if (setResultInChessState(vo, state)) {
				if (vo.getResult() == ResultType.REMIS.getNumber())
					result = " -> Game ends with remis.";
				else
					result = "#";
			}
		}

		fen = new Fen(state);
		vo.setFen(fen.getFen());

		updateInDatabase(vo);

		return result;
	}

	protected boolean setResultInChessState(ChessStateVO vo, GameState state) {
		if (ChessLogic.isCheckmate(state, state.getCurrentColor())) {
			if (state.getCurrentColor() == Color.BLACK) {
				vo.setResult(ResultType.WHITE_WINS.getNumber());
			} else {
				vo.setResult(ResultType.BLACK_WINS.getNumber());
			}
			return true;
		} else if (ChessLogic.isDraw(state)) {
			vo.setResult(ResultType.REMIS.getNumber());
			return true;
		}

		return false;
	}

	protected void updateInDatabase(ChessStateVO vo)
			throws ChessManagerException {
		try {
			dao.updateTable(vo);
		} catch (SQLException e) {
			throw new ChessManagerException(
					"Error while writing to the database.");
		}
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
		if (!s.matches("([a-hA-H][1-8]){2}[QqRrBbNn]?"))
			throw new ChessManagerException("Invalid move. Could not parse it.");

		for (int i = 0; i < 2; ++i) {
			int x = Character.toLowerCase(s.charAt(i * 2)) - 'a';
			int y = s.charAt(i * 2 + 1) - '1';

			if (i == 0)
				start = new Position(x, y);
			else
				destination = new Position(x, y);
		}

		Move m = new Move(start, destination);

		if (s.length() == 5) {
			switch (Character.toLowerCase(s.charAt(4))) {
				case 'q' :
					m.setPromoteTo(new Queen(new Position(0, 0)));
					break;
				case 'r' :
					m.setPromoteTo(new Rook(new Position(0, 0)));
					break;
				case 'b' :
					m.setPromoteTo(new Bishop(new Position(0, 0)));
					break;
				case 'n' :
					m.setPromoteTo(new Knight(new Position(0, 0)));
					break;
			}
		}

		return m;
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
