package de.fhb.projects.Twitchess.controller.chesscommands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.controller.UCIEngineInterface;
import de.fhb.projects.Twitchess.controller.configuration.Configuration;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.data.ResultType;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;

public class OfferDrawChessCommand implements ChessCommand {
	public static String commandText = "offerdraw";
	protected ChessStateDAOInterface dao;
	protected UCIEngineInterface uciEngine;

	public OfferDrawChessCommand(ChessStateDAOInterface dao,
			UCIEngineInterface uciEngine) {
		setDao(dao);
		setUciEngine(uciEngine);
	}

	@Override
	public String processInput(String player, List<String> parameters)
			throws ChessManagerException {
		String result = null;

		if (parameters == null || parameters.size() > 0) {
			throw new ChessManagerException("Error! \"" + commandText
					+ "\" command format: " + commandText);
		}

		try {
			List<ChessStateVO> state = dao.findNotFinishedGameByPlayer(player);
			if (state == null || state.size() <= 0) {
				throw new ChessManagerException(
						"You have not a running game hence you cannot offer a draw.");
			} else if (state.size() > 1) {
				throw new ChessManagerException(
						"You have several running games, something is fishy.");
			} else {
				ChessStateVO vo = state.get(0);
				Fen fen = new Fen(vo.getFen());
				GameState s = fen.getGameState();

				try {
					uciEngine.init();
					int score = uciEngine.calculateScore(fen.getFen(),
							Configuration.getInt("Engine.TimePerMove", 2000));

					if (acceptDraw(s, score)) {
						vo.setResult(ResultType.REMIS.getNumber());
						dao.updateTable(vo);
						result = "Fair enough, I accept your offer! {"
								+ vo.getFen() + "}";
					} else {
						result = "It is too early to call it a draw!";
					}

					uciEngine.destroy();
				} catch (IOException e) {
					throw new ChessManagerException(
							"Error while accessing the chess engine.");
				} catch (Throwable e) {
					throw new ChessManagerException(
							"Error while closing the chess engine.");
				}
			}
		} catch (SQLException e) {
			throw new ChessManagerException(
					"Error! Could not create new game: " + e.getMessage());
		}

		return result;
	}

	public boolean acceptDraw(GameState s, int score)
			throws ChessManagerException {
		if (s != null)
			return Math.abs(score) < Configuration.getInt(
					"AcceptDraw.AbsScoreLessThan", 100)
					&& s.getFullMoveNumber() > Configuration.getInt(
							"AcceptDraw.FullMoveCountGreaterThan", 10);

		throw new ChessManagerException("Gamestate read fault.");
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
