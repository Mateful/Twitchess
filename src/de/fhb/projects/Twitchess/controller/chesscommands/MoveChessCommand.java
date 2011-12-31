package de.fhb.projects.Twitchess.controller.chesscommands;

import java.io.IOException;
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

	public MoveChessCommand(ChessStateDAOInterface dao,
			UCIEngineInterface uciEngine) {
		setDao(dao);
		setUciEngine(uciEngine);
	}

	@Override
	public String processInput(String player, List<String> parameters)
			throws ChessManagerException {
		String result;

		if (parameters.size() != 1) {
			throw new ChessManagerException("Error! \"" + commandText
					+ "\" command format: " + commandText + " <move>");
		}

		try {
			List<ChessStateVO> listVO = dao.findNotFinishedGameByPlayer(player);
			if (listVO == null || listVO.size() > 1) {
				return "You must have a single ongoing game in order to do a move.";
			}

			ChessStateVO vo = listVO.get(0);
			Fen fen = new Fen(vo.getFen());

			if (!fen.isValid())
				throw new ChessManagerException("Fen is invalid!");

			GameState state = fen.getGameState();
			Move playersMove = getMove(parameters.get(0));
			Move calculatedMove;

			try {
				if (!ChessLogic.isValidMove(state, playersMove))
					throw new ChessManagerException("Your move is invalid!");
			} catch (RuntimeException e) {
				throw new ChessManagerException("Your move is invalid! ("
						+ e.getMessage() + ")");
			}

			state = new GameState(state, playersMove);
			fen = new Fen(state);

			calculatedMove = calculateMove(fen);

			state = new GameState(state, calculatedMove);
			fen = new Fen(state);

			result = "engine move: " + calculatedMove.getLongNotation();

			System.out.println("POS AFTER COMPUTER MOVE: " + fen.getFen());

			vo.setFen(fen.getFen());
			dao.updateTable(vo);

		} catch (Throwable e) {
			return "Error! " + e.getMessage();
		}

		return result;
	}
	protected Move calculateMove(Fen fen) throws IOException, UCIException,
			Throwable, ChessManagerException {
		Move move;
		uciEngine.init();
		String calculatedMove = uciEngine.calculateMove(fen.getFen(), 20000);
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
