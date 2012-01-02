package de.fhb.projects.Twitchess.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.fhb.projects.Twitchess.controller.chesscommands.CancelGameChessCommand;
import de.fhb.projects.Twitchess.controller.chesscommands.ChessCommand;
import de.fhb.projects.Twitchess.controller.chesscommands.MoveChessCommand;
import de.fhb.projects.Twitchess.controller.chesscommands.NewGameChessCommand;
import de.fhb.projects.Twitchess.controller.chesscommands.PrintGameChessCommand;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class ChessManager implements ManagerInterface {
	public static final String indicator = "chess";

	protected Map<String, ChessCommand> commands;

	public ChessManager(ChessStateDAOInterface dao, UCIEngineInterface uciEngine) {
		commands = new HashMap<String, ChessCommand>();

		commands.put(NewGameChessCommand.commandText, new NewGameChessCommand(
				dao));
		commands.put(CancelGameChessCommand.commandText,
				new CancelGameChessCommand(dao));
		commands.put(PrintGameChessCommand.commandText,
				new PrintGameChessCommand(dao));
		commands.put(MoveChessCommand.commandText, new MoveChessCommand(dao,
				uciEngine));
	}

	public static void main(String[] args) {
		ChessManager m;
		try {

			m = (ChessManager) ManagerFactory
					.getRelevantManager("@MatefulBot chess");
			ChessStateDAOInterface dao = ((NewGameChessCommand) m.commands
					.get("new")).getDao();

			dao.truncateTable();
			m.processInput("hey", "@MatefulBot chess new");
			m.processInput("hey", "@MatefulBot chess cancel");
			m.processInput("hey", "@MatefulBot chess new");
			m.processInput("hey", "@MatefulBot chess print");
			m.processInput("hey", "@MatefulBot chess move e2e3");
			m.processInput("hey", "@MatefulBot chess move ai");
			m.processInput("hey", "@MatefulBot chess move ai");
			m.processInput("hey", "@MatefulBot chess move ai");
			m.processInput("hey", "@MatefulBot chess move ai");
			m.processInput("hey", "@MatefulBot chess move ai");
			m.processInput("hey", "@MatefulBot chess print");

			dao.showAll();
			dao.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String processInput(String player, String message) {
		String result = null;
		try {
			List<String> parameters = getParameters(message);
			String commandString = parameters.remove(0);

			ChessCommand command = commands.get(commandString);

			if (command != null) {
				result = command.processInput(player, parameters);
			} else {
				throw new ChessManagerException("This is not a valid command!");
			}

		} catch (ChessManagerException e) {
			result = "Error while processing your request: " + e.getMessage();
		}

		System.out.println("return value of ChessManager.processInput(...) = "
				+ result);

		return result;
	}

	protected List<String> getParameters(String message)
			throws ChessManagerException {
		String[] s = message.split("\\s+");
		List<String> result = new ArrayList<String>();
		boolean startCommand = false;

		for (int i = 0; i < s.length; i++) {
			if (!s[i].startsWith("@") && !s[i].startsWith("#")) {
				if (!startCommand) {
					if (s[i].equalsIgnoreCase(ChessManager.indicator))
						startCommand = true;
				} else
					result.add(s[i]);
			}
		}

		if (!startCommand)
			throw new ChessManagerException("Message does not contain \""
					+ ChessManager.indicator + "\".");

		if (result.size() < 1)
			throw new ChessManagerException(
					"Message does not contain a command.");

		return result;
	}
}
