package de.fhb.projects.Twitchess.controller;

import de.fhb.projects.Twitchess.data.ChessStateDAO;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;


public final class ManagerFactory {
	public static ManagerInterface getRelevantManager(final String message) {
		String[] s = message.split("\\s+");
		
		if (s != null && s.length > 2 && s[1].equalsIgnoreCase(ChessManager.indicator)) {
			try {
				return new ChessManager((ChessStateDAOInterface) new ChessStateDAO(ChessManager.indicator + ".db"));
			} catch (Exception e) {
				return null;
			}
		}
		
		return null;
	}
}
