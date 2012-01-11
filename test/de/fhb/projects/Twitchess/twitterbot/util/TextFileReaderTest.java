package de.fhb.projects.Twitchess.twitterbot.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.util.TextFileReader;

public class TextFileReaderTest {

	private TextFileReader tfr;
	
	@Before
	public void init(){
		tfr = new TextFileReader();
	}
	
	@Test (expected = IOException.class)
	public void testReadTextFileLineByLineFileNotFound() throws IOException{
		tfr.readTextFileLineByLine("");
	}
	
	@Test
	public void testReadTextFileByLine() throws IOException{
		List<String> list= tfr.readTextFileLineByLine("test-files/configuration.properties");
		assertEquals("ChessEngineFactory.Linux=chessengines/stockfish-211-32-ja-linux",list.get(0));
		assertEquals("AcceptDraw.FullMoveCountGreaterThan=10",list.get(list.size()-1));
		tfr.readTextFileLineByLine(null);
	}
}
