package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;

public class KnightMovesTest {
	private GameState state;
	private Position start;

	@Before
	public void initPawnTests() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new Knight(start));
		state.updatePositions();
	}

	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new Knight(start).getNaiveMoves();
		RelativeMove knight = new RelativeMove(Direction.KNIGHT, false);
		assertTrue(naiveMoves.contains(knight));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 5))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(5, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 1))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(1, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 5))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(5, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 1))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(1, 4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void upRight() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 4)));
	}

	@Test(expected = InvalidMoveException.class)
	public void downRight() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void downLeft() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void upLeft() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 4)));
	}

	@Test(expected = InvalidMoveException.class)
	public void up() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 4)));
	}

	@Test(expected = InvalidMoveException.class)
	public void down() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void left() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 3)));
	}

	@Test(expected = InvalidMoveException.class)
	public void right() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 3)));
	}
}
