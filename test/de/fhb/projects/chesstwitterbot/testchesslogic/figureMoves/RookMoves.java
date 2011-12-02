package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMoveList;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;

public class RookMoves {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initPawnTests() {
		cl = new ChessLogic();
		Figure[][] board = new Figure[8][8];
		start = new Position(3, 3);
		board[3][3] = new Rook(Color.WHITE);
		cl.board = board;
		cl.currentTurnPlayer = Color.WHITE;
	}	

	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new Rook(Color.WHITE).getNaiveMoves();
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
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(3, 4))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(3, 5))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(3, 2))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(2, 3))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(4, 3))));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void upRight() {
		cl.isValidMove(new AbsoluteMove(start, new Position(4, 4)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void downRight() {
		cl.isValidMove(new AbsoluteMove(start, new Position(4, 2)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void downLeft() {
		cl.isValidMove(new AbsoluteMove(start, new Position(2, 2)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void upLeft() {
		cl.isValidMove(new AbsoluteMove(start, new Position(2, 4)));
	}
}
