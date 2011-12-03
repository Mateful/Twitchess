package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
public class KnightBlock {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initGame() {
		cl = new ChessLogic();
		start = new Position(3, 3);
		cl.white.add(new Knight(start));

		cl.white.add(new Pawn(new Position(4, 5), WHITE));
		cl.white.add(new Pawn(new Position(5, 4), WHITE));
		cl.white.add(new Pawn(new Position(2, 1), WHITE));
		cl.white.add(new Pawn(new Position(1, 2), WHITE));
		cl.white.add(new Pawn(new Position(2, 5), WHITE));
		cl.white.add(new Pawn(new Position(5, 2), WHITE));
		cl.white.add(new Pawn(new Position(4, 1), WHITE));
		cl.white.add(new Pawn(new Position(1, 4), WHITE));
		cl.updatePositions();
	}

	@Test
	public void isKnightMove1Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 5))));
	}

	@Test
	public void isKnightMove2Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(5, 4))));
	}

	@Test
	public void isKnightMove3Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 1))));
	}

	@Test
	public void isKnightMove4Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(1, 2))));
	}

	@Test
	public void isKnightMove5Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 5))));
	}

	@Test
	public void isKnightMove6Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(5, 2))));
	}

	@Test
	public void isKnightMove7Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 1))));
	}

	@Test
	public void isKnightMove8Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(1, 4))));
	}
}
