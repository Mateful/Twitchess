package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;

public class PawnMovesTest {
	private GameState state;
	private Position whiteStart, blackStart, initWhitePawn;

	@Before
	public void initPawnTests() {
		state = new GameState();
		whiteStart = new Position(3, 3);
		blackStart = new Position(4, 4);
		initWhitePawn = new Position(0, 1);
		state.white.add(new Pawn(whiteStart, WHITE));
		state.white.add(new Pawn(initWhitePawn, WHITE));
		state.black.add(new Pawn(blackStart, BLACK));
		state.updatePositions();
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
	public void whiteUp() {
		assertTrue(ChessLogic
				.isValidMove(state, AbsoluteMove.up(whiteStart, 1)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteTwoStepsUp() {
		ChessLogic.isValidMove(state, AbsoluteMove.up(whiteStart, 2));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteDown() {
		ChessLogic.isValidMove(state, AbsoluteMove.down(whiteStart, 1));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteLeft() {
		ChessLogic.isValidMove(state, AbsoluteMove.left(whiteStart, 1));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteRight() {
		ChessLogic.isValidMove(state, AbsoluteMove.right(whiteStart, 1));
	}

	@Test
	public void whiteUpRightHit() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(whiteStart,
				new Position(4, 4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteDownRight() {
		ChessLogic.isValidMove(state, new AbsoluteMove(whiteStart,
				new Position(4, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteDownLeft() {
		ChessLogic.isValidMove(state, new AbsoluteMove(whiteStart,
				new Position(2, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteUpLeft() {
		ChessLogic.isValidMove(state, new AbsoluteMove(whiteStart,
				new Position(2, 4)));
	}

	@Test
	public void blackForward() {
		state.currentTurnPlayer = state.black;
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(blackStart,
				new Position(4, 3))));
	}

	@Test(expected = InvalidMoveException.class)
	public void blackForwardButWrongTurn() {
		ChessLogic.isValidMove(state, new AbsoluteMove(blackStart,
				new Position(4, 3)));
	}

	@Test
	public void blackDownLeftHit() {
		state.currentTurnPlayer = state.black;
		ChessLogic.isValidMove(state, new AbsoluteMove(blackStart,
				new Position(3, 3)));
	}

	@Test(expected = InvalidMoveException.class)
	public void blackBackward() {
		ChessLogic.isValidMove(state, new AbsoluteMove(blackStart,
				new Position(3, 2)));
	}

	@Test
	public void initialPawn2StepsForward() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(
				initWhitePawn, new Position(0, 3))));
	}
}
