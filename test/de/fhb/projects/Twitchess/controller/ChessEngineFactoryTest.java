package de.fhb.projects.Twitchess.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystem;

public class ChessEngineFactoryTest {
	@Test
	public void testGetUCIEngine() throws IOException {
		assertEquals("chessengines/stockfish-211-32-mac", ChessEngineFactory
				.getUCIEngine(OperatingSystem.MAC).getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-windows.exe",
				ChessEngineFactory.getUCIEngine(OperatingSystem.WINDOWS)
						.getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-linux",
				ChessEngineFactory.getUCIEngine(OperatingSystem.UNIX)
						.getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-windows.exe",
				ChessEngineFactory.getUCIEngine(null).getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-windows.exe",
				ChessEngineFactory.getUCIEngine(OperatingSystem.SUN)
						.getFilename());
	}
}
