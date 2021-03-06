package de.fhb.projects.Twitchess.ucicommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.ucicommands.GoUCICommand;
import de.fhb.projects.Twitchess.exception.UCIException;

public class GoUCICommandTest {

	private GoUCICommand uciCommand;

	@Before
	public void init() {
		uciCommand = new GoUCICommand();
	}

	@Test
	public void testProcessResponseBestMove() {
		uciCommand.processResponse("bestmove g1f3 ponder g8f6");
		assertEquals("{bestMove=g1f3}", uciCommand.getResult().toString());
	}

	@Test
	public void testProcessResponseNullString() {
		uciCommand.processResponse(null);
		assertEquals(new HashMap<String, String>(), uciCommand.getResult());
	}

	@Test
	public void testProcessResponseInfoScoreMate() {
		uciCommand.processResponse("info ... score mate 10 ..");
		assertEquals("{score=" + GoUCICommand.MATE_SCORE + "}", uciCommand
				.getResult().toString());
	}

	@Test
	public void testProcessResponseInfoOnly() {
		uciCommand.processResponse("info");
		assertEquals("{}", uciCommand.getResult().toString());

		uciCommand.processResponse("info dvbfuja nsdfaf nasdkl .. . .!!!");
		assertEquals("{}", uciCommand.getResult().toString());
	}
	@Test
	public void testProcessResponseEmptyString() {
		uciCommand.processResponse("");
		assertEquals("{}", uciCommand.getResult().toString());
	}

	@Test
	public void testProcessResponseInfoScoreCP() {
		uciCommand.processResponse("info score cp 100");
		assertEquals("{score=100}", uciCommand.getResult().toString());
	}

	@Test
	public void testProcessResponseSwitchInput() {
		uciCommand.processResponse("score info cp 100");
		assertEquals("{}", uciCommand.getResult().toString());
		uciCommand.processResponse("info cp 100 score mate");
		assertEquals("{}", uciCommand.getResult().toString());
	}

	@Test(expected = UCIException.class)
	public void testGetBestMoveIsNotFinished() throws UCIException {
		assertNotNull(uciCommand.getBestMove());
	}
	@Test
	public void testGetBestMove() throws UCIException {
		uciCommand.processResponse("bestmove g1f3 ponder g8f6");
		uciCommand.getBestMove();
	}

	@Test(expected = UCIException.class)
	public void testGetBestMoveNone() throws UCIException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("bestMove", "(none)");
		uciCommand.setFinished(true);
		uciCommand.setResult(map);
		uciCommand.getResult();
		uciCommand.getBestMove();
	}

	@Test
	public void testGenerateFullCommandStringDepthNull() {
		uciCommand.setDepth(null);
		assertEquals("go movetime " + uciCommand.getMovetime(),
				uciCommand.generateFullCommandString());
	}

	@Test
	public void testGenerateFullCommandStringDepthNotNull() {
		uciCommand.setDepth(1);
		assertEquals("go depth " + uciCommand.getDepth(),
				uciCommand.generateFullCommandString());
	}

	@Test
	public void testGenerateFullDepthMaxMINInt() {
		uciCommand.setDepth(Integer.MIN_VALUE);
		assertEquals("go movetime " + uciCommand.getMovetime(),
				uciCommand.generateFullCommandString());
		uciCommand.setDepth(Integer.MAX_VALUE);
		assertEquals("go depth " + uciCommand.getDepth(),
				uciCommand.generateFullCommandString());
	}

	@Test
	public void testGenerateFullCommandStringMovetimeNull() {
		uciCommand.setMovetime(null);
		assertEquals("go movetime " + uciCommand.getMovetime(),
				uciCommand.generateFullCommandString());
	}

	@Test(expected = UCIException.class)
	public void testGetScoreNoScore() throws UCIException {
		uciCommand.setFinished(true);
		uciCommand.getScore();
	}

	@Test
	public void testGetScore() throws UCIException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("score", "1");
		uciCommand.setResult(map);
		uciCommand.setFinished(true);
		assertEquals(1, uciCommand.getScore());
	}

	@Test(expected = UCIException.class)
	public void testGetScoreNotFinished() throws UCIException {
		uciCommand.getScore();
	}

	@Test
	public void testSetMoveTimeInvalidInput() {
		uciCommand.setMovetime(null);
		assertTrue(10000 == uciCommand.getMovetime());
		uciCommand.setMovetime(Integer.MIN_VALUE);
		assertTrue(10000 == uciCommand.getMovetime());
	}

	@Test
	public void testMoveTimeNullDepthTimeNull() {
		uciCommand.processResponse("bestmove");
		assertEquals("{bestMove=}", uciCommand.getResult().toString());

	}

	@Test
	public void testProcessResponseInfoScore() {
		uciCommand.processResponse("info score fail input");
		assertEquals("{}", uciCommand.getResult().toString());
	}

}
