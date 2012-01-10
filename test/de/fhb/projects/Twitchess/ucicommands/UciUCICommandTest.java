package de.fhb.projects.Twitchess.ucicommands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.ucicommands.UciUCICommand;

public class UciUCICommandTest {

	private UciUCICommand uciCommand;

	@Before
	public void init() {
		uciCommand = new UciUCICommand();
	}

	@Test
	public void processResponseTest() {
		uciCommand.processResponse("uciok");
		assertTrue(uciCommand.isFinished());
	}

	@Test
	public void processResponsNullTest() {
		uciCommand.processResponse(null);
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("null");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse(String.valueOf(0));
		assertTrue(!uciCommand.isFinished());
	}

	@Test
	public void processResponsInvalidCommandTest() {
		uciCommand.processResponse("thats a test");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("UCIOK");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("uciok!");
		assertTrue(!uciCommand.isFinished());
		uciCommand.processResponse("uci ok");
		assertTrue(!uciCommand.isFinished());
	}
}