package de.fhb.projects.Twitchess.controller.chesscommands;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.games.chess.Fen;

public class NewGameChessCommand implements ChessCommand {
	public static String commandText = "new";
	protected ChessStateDAOInterface dao;

	public NewGameChessCommand(ChessStateDAOInterface dao) {
		setDao(dao);
	}

	@Override
	public String processInput(String player, List<String> parameters)
			throws ChessManagerException {
		String result = null;

		if (parameters != null && parameters.size() > 0) {
			throw new ChessManagerException("Error! \"" + commandText
					+ "\" command format: " + commandText);
		}

		try {

			List<ChessStateVO> state = dao.findNotFinishedGameByPlayer(player);
			if (state != null && state.size() > 0) {
				throw new ChessManagerException("You already have an ongoing game. Finish it in order to play another game.");
			} else {
				ChessStateVO game = new ChessStateVO();
				game.setPlayerName(player);
				game.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
				game.setFen(Fen.START_POSITION);
				dao.insertIntoTable(game);

				result = "A new game has been successfully started!";
			}
		} catch (SQLException e) {
			throw new ChessManagerException("Error! Could not create new game: " + e.getMessage());
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
