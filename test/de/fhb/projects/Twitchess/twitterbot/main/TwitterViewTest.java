package de.fhb.projects.Twitchess.twitterbot.main;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TwitterViewTest extends EasyMockSupport {

	private TwitterView tw;
	private TwitterViewStub ts;
	private BufferedReader br;

	@Before
	public void init() throws FileNotFoundException{
		ts = new TwitterViewStub();
		br = createMockBuilder(BufferedReader.class).withConstructor(Reader.class).withArgs(new InputStreamReader(System.in)).addMockedMethod("readLine", new Class<?>[0]).createMock();
		tw = new TwitterView(ts);
		tw.setInputReader(br);
	}

	@Ignore
	@Test
	public void TestProcessInput() throws IOException{
		EasyMock.expect(br.readLine()).andReturn("");
		EasyMock.replay(br);
		tw = new TwitterView(ts);
		tw.processInput("0");
		assertTrue(ts.isExitCommand());		
		EasyMock.verify(br);
	}
}
