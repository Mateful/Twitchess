package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.chesstwitterbot.exception.WrongColorException;

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
		List<Direction> whiteDirections = new Pawn(whiteStart, WHITE)
				.getDirections();
		List<Direction> blackDirections = new Pawn(whiteStart, BLACK)
				.getDirections();
		Direction up = new OneStepDirection(DirectionType.UP);
		Direction down = new OneStepDirection(DirectionType.DOWN);
		assertTrue(whiteDirections.contains(up));
		assertTrue(blackDirections.contains(down));
		assertFalse(whiteDirections.contains(down));
		assertFalse(blackDirections.contains(up));
	}

	@Test
	public void whiteUp() {
		assertTrue(ChessLogic.isValidMove(state, Move.up(whiteStart, 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteTwoStepsUp() {
		ChessLogic.isValidMove(state, Move.up(whiteStart, 2));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteDown() {
		ChessLogic.isValidMove(state, Move.down(whiteStart, 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteLeft() {
		ChessLogic.isValidMove(state, Move.left(whiteStart, 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteRight() {
		ChessLogic.isValidMove(state, Move.right(whiteStart, 1));
	}

	@Test
	public void whiteUpRightHit() {
		assertTrue(ChessLogic.isValidMove(state, new Move(whiteStart,
				new Position(4, 4))));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteDownRight() {
		ChessLogic.isValidMove(state, new Move(whiteStart, new Position(4, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteDownLeft() {
		ChessLogic.isValidMove(state, new Move(whiteStart, new Position(2, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteUpLeft() {
		ChessLogic.isValidMove(state, new Move(whiteStart, new Position(2, 4)));
	}

	@Test
	public void blackForward() {
		state.currentTurnPlayer = state.black;
		assertTrue(ChessLogic.isValidMove(state, new Move(blackStart,
				new Position(4, 3))));
	}

	@Test(expected = WrongColorException.class)
	public void blackForwardButWrongTurn() {
		ChessLogic.isValidMove(state, new Move(blackStart, new Position(4, 3)));
	}

	@Test
	public void blackDownLeftHit() {
		state.currentTurnPlayer = state.black;
		ChessLogic.isValidMove(state, new Move(blackStart, new Position(3, 3)));
	}

	@Test(expected = WrongColorException.class)
	public void blackBackward() {
		ChessLogic.isValidMove(state, new Move(blackStart, new Position(3, 2)));
	}

	@Test
	public void initialPawn2StepsForward() {
		assertTrue(ChessLogic.isValidMove(state, new Move(initWhitePawn,
				new Position(0, 3))));
	}
}
