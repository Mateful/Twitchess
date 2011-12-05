package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;

public class QueenMovesTest {
	private GameState state;
	private Position start;

	@Before
	public void initPawnTests() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new Queen(start));
		state.updatePositions();
	}	

	@Test
	public void getMoves() {
		List<Direction> directions = new Queen(start).getDirections();
		Direction up = new InfiniteDirection(DirectionType.UP);
		Direction down = new InfiniteDirection(DirectionType.DOWN);
		Direction left = new InfiniteDirection(DirectionType.LEFT);
		Direction right = new InfiniteDirection(DirectionType.RIGHT);
		Direction upright = new InfiniteDirection(DirectionType.UPRIGHT);
		Direction downright = new InfiniteDirection(DirectionType.DOWNRIGHT);
		Direction upleft = new InfiniteDirection(DirectionType.UPLEFT);
		Direction downleft = new InfiniteDirection(DirectionType.DOWNLEFT);
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
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(3, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(3, 5))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(3, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(2, 3))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(4, 3))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(4, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(5, 5))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(2, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(2, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(start, new Position(4, 2))));
	}
}
