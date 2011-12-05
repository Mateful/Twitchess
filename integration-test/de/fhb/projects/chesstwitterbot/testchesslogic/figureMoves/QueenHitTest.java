package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public class QueenHitTest {
	private GameState state;
	private Position start;

	@Before
	public void initGame() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new Queen(start));

		state.black.add(new Pawn(new Position(3, 4), BLACK));
		state.black.add(new Pawn(new Position(4, 4), BLACK));
		state.black.add(new Pawn(new Position(4, 3), BLACK));
		state.black.add(new Pawn(new Position(4, 2), BLACK));
		state.black.add(new Pawn(new Position(3, 2), BLACK));
		state.black.add(new Pawn(new Position(2, 2), BLACK));
		state.black.add(new Pawn(new Position(2, 3), BLACK));
		state.black.add(new Pawn(new Position(2, 4), BLACK));
		state.updatePositions();
	}

	@Test
	public void isQueenUpMove1StepBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(3, 4))));
	}

	@Test
	public void isQueenRightMoveBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(4, 3))));
	}

	@Test
	public void isQueenLeftMoveBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(2, 3))));
	}

	@Test
	public void isQueenDownMoveBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(3, 2))));
	}

	@Test
	public void isQueenUpRightMove1StepBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(4, 4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenUpRightMove2StepsBlocked() {
		ChessLogic.isValidMove(state, new Move(start,
				new Position(5, 5)));
	}

	@Test
	public void isQueenDownRightMoveBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(4, 2))));
	}

	@Test
	public void isQueenDownLeftMoveBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(2, 2))));
	}

	@Test
	public void isQueenUpLeftMoveBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start,
				new Position(2, 4))));
	}
}
