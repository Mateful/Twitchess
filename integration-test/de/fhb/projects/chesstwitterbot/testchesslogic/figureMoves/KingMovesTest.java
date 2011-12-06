package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public class KingMovesTest {
	private GameState state;
	private Position start;

	@Before
	public void initPawnTests() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new King(start));
		state.updatePositions();
	}

	@Test
	public void getMoves() {
		List<Direction> directions = new King(start).getDirections();
		Direction up = new OneStepDirection(DirectionType.UP);
		Direction down = new OneStepDirection(DirectionType.DOWN);
		Direction left = new OneStepDirection(DirectionType.LEFT);
		Direction right = new OneStepDirection(DirectionType.RIGHT);
		Direction upright = new OneStepDirection(DirectionType.UPRIGHT);
		Direction downright = new OneStepDirection(DirectionType.DOWNRIGHT);
		Direction upleft = new OneStepDirection(DirectionType.UPLEFT);
		Direction downleft = new OneStepDirection(DirectionType.DOWNLEFT);
		assertTrue(directions.contains(left));
		assertTrue(directions.contains(right));
		assertTrue(directions.contains(up));
		assertTrue(directions.contains(down));
		assertTrue(directions.contains(upright));
		assertTrue(directions.contains(downright));
		assertTrue(directions.contains(upleft));
		assertTrue(directions.contains(downleft));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				3, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				3, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				2, 3))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				4, 3))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				4, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				2, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				2, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(
				4, 2))));
	}

	@Test(expected = InvalidMoveException.class)
	public void twoStepsForward() {
		ChessLogic.isValidMove(state, new Move(start, new Position(3, 5)));
	}

	@Test(expected = InvalidMoveException.class)
	public void twoStepsUpRight() {
		ChessLogic.isValidMove(state, new Move(start, new Position(5, 5)));
	}
}
