package de.fhb.projects.Twitchess.controller;

public class ChessManager implements ManagerInterface {

	@Override
	public String processInput(String player, String message) {
		
		return "processInput(player = " + player + ", message = " + message + ")";
	}

}
