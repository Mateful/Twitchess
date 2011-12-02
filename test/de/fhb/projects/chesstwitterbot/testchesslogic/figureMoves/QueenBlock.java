package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;

public class QueenBlock {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initGame() {
		cl = new ChessLogic();
		Figure[][] board = new Figure[8][8];
		start = new Position(3, 3);
		board[3][3] = new Queen(Color.WHITE);

		board[3][4] = new Pawn(Color.WHITE);
		board[4][4] = new Pawn(Color.WHITE);
		board[4][3] = new Pawn(Color.WHITE);
		board[4][2] = new Pawn(Color.WHITE);
		board[3][2] = new Pawn(Color.WHITE);
		board[2][2] = new Pawn(Color.WHITE);
		board[2][3] = new Pawn(Color.WHITE);
		board[2][4] = new Pawn(Color.WHITE);

		cl.board = board;
	}

	@Test
	public void isQueenUpMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(3, 4))));
	}
	
	@Test
	public void isQueenRightMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 3))));
	}

	@Test
	public void isQueenLeftMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 3))));
	}
	
	@Test
	public void isQueenDownMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(3, 2))));
	}

	@Test
	public void isQueenUpRightMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 4))));
	}
	
	@Test
	public void isQueenDownRightMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 2))));
	}

	@Test
	public void isQueenDownLeftMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 2))));
	}

	@Test
	public void isQueenUpLeftMoveBlocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 4))));
	}
}
