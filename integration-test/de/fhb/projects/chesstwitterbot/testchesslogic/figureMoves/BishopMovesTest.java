package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Bishop;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public class BishopMovesTest {
	private GameState state;
	private Position start;

	@Before
	public void initPawnTests() {
		start = new Position(3, 3);

		state = new GameState();
		state.white.add(new Bishop(start));
		state.updatePositions();
	}

	@Test
	public void getMoves() {
		List<Direction> directions = new Bishop(start).getDirections();
		Direction upright = new InfiniteDirection(DirectionType.UPRIGHT);
		Direction downright = new InfiniteDirection(DirectionType.DOWNRIGHT);
		Direction upleft = new InfiniteDirection(DirectionType.UPLEFT);
		Direction downleft = new InfiniteDirection(DirectionType.DOWNLEFT);
		assertTrue(directions.contains(upright));
		assertTrue(directions.contains(downright));
		assertTrue(directions.contains(upleft));
		assertTrue(directions.contains(downleft));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, Move.upRight(start, 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.upLeft(start, 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.downRight(start, 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.downLeft(start, 1)));
	}

	@Test(expected = InvalidMoveException.class)
	public void up() {
		ChessLogic.isValidMove(state, Move.up(start, 1));
	}

	@Test(expected = InvalidMoveException.class)
	public void down() {
		ChessLogic.isValidMove(state, Move.down(start, 1));
	}

	@Test(expected = InvalidMoveException.class)
	public void left() {
		ChessLogic.isValidMove(state, Move.left(start, 1));
	}

	@Test(expected = InvalidMoveException.class)
	public void right() {
		ChessLogic.isValidMove(state, Move.right(start, 1));
	}
}
