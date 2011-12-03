package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public class PawnMoves {
	private ChessLogic cl;
	private Position whiteStart, blackStart, initWhitePawn;

	@Before
	public void initPawnTests() {
		cl = new ChessLogic();
		whiteStart = new Position(3, 3);
		blackStart = new Position(4, 4);
		initWhitePawn = new Position(0, 1);
		cl.white.add(new Pawn(whiteStart, WHITE));
		cl.white.add(new Pawn(initWhitePawn, WHITE));
		cl.black.add(new Pawn(blackStart, BLACK));
		cl.updatePositions();
	}

	@Test
	public void getMoves() {
		RelativeMoveList whitePawn = new Pawn(whiteStart, WHITE)
				.getNaiveMoves();
		RelativeMoveList blackPawn = new Pawn(blackStart, BLACK)
				.getNaiveMoves();
		RelativeMove up = new RelativeMove(Direction.UP, false);
		RelativeMove down = new RelativeMove(Direction.DOWN, false);
		assertTrue(whitePawn.contains(up));
		assertTrue(blackPawn.contains(down));
		assertFalse(whitePawn.contains(down));
		assertFalse(blackPawn.contains(up));
	}

	@Test
	public void whiteForward() {
		assertTrue(cl.isValidMove(new AbsoluteMove(whiteStart, new Position(3,
				4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteTwoStepsForward() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(3, 5)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteBackward() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(3, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteLeft() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(2, 3)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteRight() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(4, 3)));
	}

	@Test
	public void whiteUpRightHit() {
		assertTrue(cl.isValidMove(new AbsoluteMove(whiteStart, new Position(4,
				4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteDownRight() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(4, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteDownLeft() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(2, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteUpLeft() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(2, 4)));
	}

	@Test
	public void blackForward() {
		cl.currentTurnPlayer = cl.black;
		assertTrue(cl.isValidMove(new AbsoluteMove(blackStart, new Position(4,
				3))));
	}

	@Test(expected = InvalidMoveException.class)
	public void blackForwardButWrongTurn() {
		cl.isValidMove(new AbsoluteMove(blackStart, new Position(4, 3)));
	}

	@Test
	public void blackDownLeftHit() {
		cl.currentTurnPlayer = cl.black;
		cl.isValidMove(new AbsoluteMove(blackStart, new Position(3, 3)));
	}

	@Test(expected = InvalidMoveException.class)
	public void blackBackward() {
		cl.isValidMove(new AbsoluteMove(blackStart, new Position(3, 2)));
	}
	
	@Test
	public void initialPawn2StepsForward() {
		assertTrue(cl.isValidMove(new AbsoluteMove(initWhitePawn, new Position(0, 3))));
	}
}
