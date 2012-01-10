package de.fhb.projects.Twitchess.controller;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.osvalidator.OperatingSystem;

public class ChessEngineFactoryTest {
	private ChessEngineFactory cef;
	
	@Before
	public void init(){
		cef = new ChessEngineFactory();
	}
	
	@Test
	public void getUCIEngineMacTest() throws IOException{
		
		assertEquals("chessengines/stockfish-211-32-mac",cef.getUCIEngine(OperatingSystem.MAC).getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-windows.exe",cef.getUCIEngine(OperatingSystem.WINDOWS).getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-linux",cef.getUCIEngine(OperatingSystem.UNIX).getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-windows.exe",cef.getUCIEngine(null).getFilename());
		assertEquals("chessengines/stockfish-211-32-ja-windows.exe",cef.getUCIEngine(OperatingSystem.SUN).getFilename());
	}
}
