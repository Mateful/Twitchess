package de.fhb.projects.Twitchess.controller;

import java.io.IOException;

import de.fhb.projects.Twitchess.controller.configuration.Configuration;
import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystem;

public final class ChessEngineFactory {
	public static UCIEngine getUCIEngine(OperatingSystem os) throws IOException {
		String fileName;

		switch (os) {
			case WINDOWS :
				fileName = Configuration
						.getString("ChessEngineFactory.Windows");
				break;
			case UNIX :
				fileName = Configuration.getString("ChessEngineFactory.Linux");
				break;
			case MAC :
				fileName = Configuration.getString("ChessEngineFactory.Mac");
				break;
			default :
				fileName = Configuration
						.getString("ChessEngineFactory.Windows");
		}

		UCIEngine uciEngine = new UCIEngine(fileName);

		return uciEngine;
	}
}
