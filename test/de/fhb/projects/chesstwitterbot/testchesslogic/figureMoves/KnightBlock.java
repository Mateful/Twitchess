package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;

public class KnightBlock {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initGame() {
		cl = new ChessLogic();
		Figure[][] board = new Figure[8][8];
		start = new Position(3, 3);
		board[3][3] = new Knight(Color.WHITE);

		board[4][5] = new Pawn(Color.WHITE);
		board[5][4] = new Pawn(Color.WHITE);
		board[2][1] = new Pawn(Color.WHITE);
		board[1][2] = new Pawn(Color.WHITE);
		board[2][5] = new Pawn(Color.WHITE);
		board[5][2] = new Pawn(Color.WHITE);
		board[4][1] = new Pawn(Color.WHITE);
		board[1][4] = new Pawn(Color.WHITE);

		cl.board = board;
	}

	@Test
	public void isKnightMove1Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 5))));
	}
	
	@Test
	public void isKnightMove2Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(5, 4))));
	}

	@Test
	public void isKnightMove3Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 1))));
	}
	
	@Test
	public void isKnightMove4Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(1, 2))));
	}

	@Test
	public void isKnightMove5Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(2, 5))));
	}
	
	@Test
	public void isKnightMove6Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(5, 2))));
	}

	@Test
	public void isKnightMove7Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(4, 1))));
	}

	@Test
	public void isKnightMove8Blocked() {
		assertTrue(cl
				.isMoveBlocked(new AbsoluteMove(start, new Position(1, 4))));
	}
}
