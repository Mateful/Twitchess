package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;

public class RookMoves {
	private GameState state;
	private Position start;

	@Before
	public void initPawnTests() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new Rook(start));
		state.updatePositions();
	}

	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new Rook(start).getNaiveMoves();
		RelativeMove up = new RelativeMove(Direction.UP, true);
		RelativeMove down = new RelativeMove(Direction.DOWN, true);
		RelativeMove left = new RelativeMove(Direction.LEFT, true);
		RelativeMove right = new RelativeMove(Direction.RIGHT, true);
		assertTrue(naiveMoves.contains(left));
		assertTrue(naiveMoves.contains(right));
		assertTrue(naiveMoves.contains(up));
		assertTrue(naiveMoves.contains(down));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 5))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 3))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 3))));
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
}
