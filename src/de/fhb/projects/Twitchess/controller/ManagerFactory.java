package de.fhb.projects.Twitchess.controller;

import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystem;
import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystemValidator;
import de.fhb.projects.Twitchess.data.ChessStateDAO;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;


public final class ManagerFactory {
	public static ManagerInterface getRelevantManager(final String message) {
		String[] s = message.split("\\s+");
		
		if (s != null && s.length >= 2 && s[1].equalsIgnoreCase(ChessManager.indicator)) {
			try {
				ChessStateDAOInterface dao =  new ChessStateDAO(ChessManager.indicator + ".db");
				
				OperatingSystem os = OperatingSystemValidator.getOperatingSystem();
				UCIEngineInterface  uciEngine = ChessEngineFactory.getUCIEngine(os);
				
				return new ChessManager(dao, uciEngine);
			} catch (Exception e) {
				return null;
			}
		}
		
		return null;
	}
}
