package de.fhb.projects.Twitchess.controller.chesscommands;

import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.data.ResultType;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class CancelGameChessCommand implements ChessCommand {
	public static String commandText = "cancel";
	protected ChessStateDAOInterface dao;

	public CancelGameChessCommand(ChessStateDAOInterface dao) {
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
				return "You have not a running game hence you cannot cancel it.";
			} else if (state.size() > 1){
				return "You have several running games, something is fishy.";
			} else {
				ChessStateVO vo = state.get(0);
				
				vo.setResult(ResultType.ABORTED.getNumber());
				dao.updateTable(vo);
				
				result = "Game has been successfully aborted!";
			}
		} catch (SQLException e) {
			return "Error! Could not create new game: " + e.getMessage();
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
