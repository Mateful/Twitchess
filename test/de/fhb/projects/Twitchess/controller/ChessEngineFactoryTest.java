package de.fhb.projects.Twitchess.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import de.fhb.projects.Twitchess.controller.configuration.Configuration;
import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystem;

public class ChessEngineFactoryTest {
	@Test
	public void testGetUCIEngine() throws IOException {
		assertEquals(Configuration.getString("ChessEngineFactory.Mac"),
				ChessEngineFactory.getUCIEngine(OperatingSystem.MAC)
						.getFilename());
		assertEquals(Configuration.getString("ChessEngineFactory.Windows"),
				ChessEngineFactory.getUCIEngine(OperatingSystem.WINDOWS)
						.getFilename());
		assertEquals(Configuration.getString("ChessEngineFactory.Linux"),
				ChessEngineFactory.getUCIEngine(OperatingSystem.UNIX)
						.getFilename());
		assertEquals(Configuration.getString("ChessEngineFactory.Windows"),
				ChessEngineFactory.getUCIEngine(null).getFilename());
		assertEquals(Configuration.getString("ChessEngineFactory.Windows"),
				ChessEngineFactory.getUCIEngine(OperatingSystem.SUN)
						.getFilename());
	}
}
