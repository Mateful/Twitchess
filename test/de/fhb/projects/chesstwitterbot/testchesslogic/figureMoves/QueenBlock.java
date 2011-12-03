package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
public class QueenBlock {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initGame() {
		cl = new ChessLogic();
		start = new Position(3, 3);
		cl.white.add(new Queen(start));

		cl.white.add(new Pawn(new Position(3, 4), WHITE));
		cl.white.add(new Pawn(new Position(4, 4), WHITE));
		cl.white.add(new Pawn(new Position(4, 3), WHITE));
		cl.white.add(new Pawn(new Position(4, 2), WHITE));
		cl.white.add(new Pawn(new Position(3, 2), WHITE));
		cl.white.add(new Pawn(new Position(2, 2), WHITE));
		cl.white.add(new Pawn(new Position(2, 3), WHITE));
		cl.white.add(new Pawn(new Position(2, 4), WHITE));
		cl.updatePositions();
	}

	@Test
	public void isQueenUpMove1StepBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(3, 4))));
	}
	
	@Test
	public void isQueenUpMove2StepsBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(3, 5))));
	}
	
	@Test
	public void isQueenRightMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 3))));
	}

	@Test
	public void isQueenLeftMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 3))));
	}
	
	@Test
	public void isQueenDownMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(3, 2))));
	}

	@Test
	public void isQueenUpRightMove1StepBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 4))));
	}
	
	@Test
	public void isQueenUpRightMove2StepsBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(5, 5))));
	}
	
	@Test
	public void isQueenDownRightMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 2))));
	}

	@Test
	public void isQueenDownLeftMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 2))));
	}

	@Test
	public void isQueenUpLeftMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 4))));
	}
}
