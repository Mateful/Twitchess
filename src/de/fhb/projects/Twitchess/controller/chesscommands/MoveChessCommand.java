package de.fhb.projects.Twitchess.controller.chesscommands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.controller.UCIEngine;
import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystemValidator;
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

	public MoveChessCommand(ChessStateDAOInterface dao) {
		setDao(dao);
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
			} else {
				ChessStateVO vo = listVO.get(0);
				Fen fen = new Fen(vo.getFen());

				if (!fen.isValid())
					throw new ChessManagerException("Fen is invalid!");

				GameState state = fen.getGameState();
				Move move = getMove(parameters.get(0));
				System.out.println("SPIELER: " + state.getCurrentPlayer());

				try {
					if (!ChessLogic.isValidMove(state, move))
						throw new ChessManagerException("Your move is invalid!");
				} catch (RuntimeException e) {
					throw new ChessManagerException("Your move is invalid! ("
							+ e.getMessage() + ")");
				}

				state = new GameState(state, move);
				fen = new Fen(state);

				String fileName = "stockfish-211-32-ja-windows.exe";

				switch (OperatingSystemValidator.getOperatingSystem()) {
					case WINDOWS :
						fileName = "stockfish-211-32-ja-windows.exe";
						break;
					case UNIX :
						fileName = "stockfish-211-32-ja-linux";
						break;
					case MAC :
						fileName = "stockfish-211-32-mac";
						break;
				}

				UCIEngine uciEngine = new UCIEngine("chessengines/" + fileName);

				String calculatedMove = uciEngine.calculateMove(fen.getFen(), 20000);
				uciEngine.destroy();
				
				move = getMove(calculatedMove);
				state = new GameState(state, move);
				fen = new Fen(state);

				result = "engine move: " + move.getLongNotation() + " (position: " + fen.getFen() + ")";
				vo.setFen(fen.getFen());
//				vo.setFen(Fen.START_POSITION);
				dao.updateTable(vo);

			}
		} catch (SQLException e) {
			return "Error! Could not process your move: " + e.getMessage();
		} catch (IOException e) {
			return "Error (engine related)! Could not process your move: "
					+ e.getMessage();
		} catch (UCIException e) {
			return "Error (engine related)! Could not process your move: "
					+ e.getMessage();
		} catch (Throwable e) {
			return "Error (engine related)! Could not process your move: "
					+ e.getMessage();
		}

		return result;
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

}
