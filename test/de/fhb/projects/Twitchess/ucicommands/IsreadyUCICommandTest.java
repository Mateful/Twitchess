package de.fhb.projects.Twitchess.ucicommands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.ucicommands.IsreadyUCICommand;

public class IsreadyUCICommandTest {

	private IsreadyUCICommand uciCommand;

	@Before
	public void init() {
		uciCommand = new IsreadyUCICommand();
	}

	@Test
	public void testProcessResponse() {
		uciCommand.processResponse("readyok");
		assertTrue(uciCommand.isFinished());
	}

	@Test
	public void testProcessResponsNull() {
		uciCommand.processResponse(null);
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("null");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse(String.valueOf(0));
		assertTrue(!uciCommand.isFinished());
	}

	@Test
	public void testProcessResponsInvalidCommand() {
		uciCommand.processResponse("thats a test");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("READYOK");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("READY OK");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("ready ok");
		assertTrue(!uciCommand.isFinished());
	}
}
