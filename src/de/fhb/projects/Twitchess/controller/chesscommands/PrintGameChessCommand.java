package de.fhb.projects.Twitchess.controller.chesscommands;

import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class PrintGameChessCommand implements ChessCommand {
	public static String commandText = "print";
	protected ChessStateDAOInterface dao;

	public PrintGameChessCommand(ChessStateDAOInterface dao) {
		setDao(dao);
	}

	@Override
	public String processInput(String player, List<String> parameters)
			throws ChessManagerException {
		String result = null;

		if (parameters.size() > 0) {
			throw new ChessManagerException("Error! \"" + commandText
					+ "\" command format: " + commandText);
		}

		try {
			List<ChessStateVO> state = dao.findNotFinishedGameByPlayer(player);
			if (state == null || state.size() <= 0) {
				return "You have not a running game hence you cannot print it.";
			} else if (state.size() > 1) {
				return "You have several running games, something is fishy.";
			} else {
				ChessStateVO vo = state.get(0);

				result = "Current Position: " + vo.getFen();
			}
		} catch (SQLException e) {
			return "Error! Could not retrieve game from database: "
					+ e.getMessage();
		}

		return result;
	}

	public ChessStateDAOInterface getDao() {
		return dao;
	}

	public void setDao(ChessStateDAOInterface dao) {
		this.dao = dao;
	}

}
