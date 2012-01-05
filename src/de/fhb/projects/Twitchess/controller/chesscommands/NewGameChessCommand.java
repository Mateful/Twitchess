package de.fhb.projects.Twitchess.controller.chesscommands;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.player.Color;

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
		Color c = getColor(parameters);

		if (c == Color.NOCOLOR) {
			throw new ChessManagerException("Error! \"" + commandText
					+ "\" command format: " + commandText + " [w|b]");
		}

		try {

			List<ChessStateVO> state = dao.findNotFinishedGameByPlayer(player);
			if (state != null && state.size() > 0) {
				return "You already have a running game. You have to cancel it first before you can play another game.";
			} else {
				ChessStateVO game = new ChessStateVO();
				game.setPlayerName(player);
				game.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
				game.setFen(Fen.START_POSITION);
				dao.insertIntoTable(game);

				result = "new game successfully started, you are now playing as "
						+ c.name();
			}
		} catch (SQLException e) {
			return "Error! Could not create new game: " + e.getMessage();
		}

		return result;
	}

	private Color getColor(List<String> parameters) {
		Color c = Color.NOCOLOR;

		switch (parameters.size()) {
			case 0 :
				c = Color.WHITE;
				break;
			case 1 :
				if (parameters.get(0).equalsIgnoreCase("w"))
					c = Color.WHITE;
				else if (parameters.get(0).equalsIgnoreCase("b"))
					c = Color.BLACK;
				break;
		}
		return c;
	}

	public ChessStateDAOInterface getDao() {
		return dao;
	}

	public void setDao(ChessStateDAOInterface dao) {
		this.dao = dao;
	}

}
