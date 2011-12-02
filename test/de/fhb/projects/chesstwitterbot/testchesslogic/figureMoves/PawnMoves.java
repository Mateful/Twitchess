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
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;

public class PawnMoves {
	private ChessLogic cl;
	private Position whiteStart, blackStart;

	@Before
	public void initPawnTests() {
		cl = new ChessLogic();
		Figure[][] board = new Figure[8][8];
		whiteStart = new Position(3, 3);
		blackStart = new Position(4, 4);
		board[3][3] = new Pawn(Color.WHITE);
		board[4][4] = new Pawn(Color.BLACK);
		cl.board = board;
		cl.currentTurnPlayer = Color.WHITE;
	}
	
	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new Pawn(Color.WHITE).getNaiveMoves();
		RelativeMove up = new RelativeMove(Direction.UP, false);
		assertTrue(naiveMoves.contains(up));
	}
	
	@Test
	public void validWhiteMoves() {
		assertTrue(cl.isValidMove(new AbsoluteMove(whiteStart, new Position(3, 4))));
	}
	
	@Test
	public void validBlackMoves() {
		assertTrue(cl.isValidMove(new AbsoluteMove(blackStart, new Position(4, 3))));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void twoStepsForward() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(3, 5)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void backward() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(3, 2)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void left() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(2, 3)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void right() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(4, 3)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void upRight() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(4, 4)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void downRight() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(4, 2)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void downLeft() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(2, 2)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void upLeft() {
		cl.isValidMove(new AbsoluteMove(whiteStart, new Position(2, 4)));
	}
}
