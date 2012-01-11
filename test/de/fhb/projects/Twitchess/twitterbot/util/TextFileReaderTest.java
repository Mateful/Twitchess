package de.fhb.projects.Twitchess.twitterbot.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.util.TextFileReader;

public class TextFileReaderTest {

	@Test (expected = IOException.class)
	public void testReadTextFileLineByLineFileNotFound() throws IOException{
		TextFileReader.readTextFileLineByLine("");
	}
	
	@Test
	public void testReadTextFileByLine() throws IOException{
		List<String> list= TextFileReader.readTextFileLineByLine("test-files/configuration.properties");
		assertEquals("ChessEngineFactory.Linux=chessengines/stockfish-211-32-ja-linux",list.get(0));
		assertEquals("AcceptDraw.FullMoveCountGreaterThan=10",list.get(list.size()-1));
		TextFileReader.readTextFileLineByLine(null);
	}
}
