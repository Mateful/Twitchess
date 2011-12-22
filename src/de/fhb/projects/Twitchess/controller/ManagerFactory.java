package de.fhb.projects.Twitchess.controller;

public final class ManagerFactory {
	public static ManagerInterface getRelevantManager(final String message) {
		String[] s = message.split("\\s+");
		
		if (s != null && s.length > 2 && s[1].equalsIgnoreCase("chess"))
			return new ChessManager();
		
		return null;
	}
}
