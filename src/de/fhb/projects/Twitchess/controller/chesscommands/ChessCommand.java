package de.fhb.projects.Twitchess.controller.chesscommands;

import java.util.List;

import de.fhb.projects.Twitchess.exception.ChessManagerException;

public abstract interface ChessCommand {
	public String processInput(String player, List<String> parameters) throws ChessManagerException;
}
