package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMoveList;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;

public class QueenMoves {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initPawnTests() {
		cl = new ChessLogic();
		Figure[][] board = new Figure[8][8];
		start = new Position(3, 3);
		board[3][3] = new Queen(Color.WHITE);
		cl.board = board;
		cl.currentTurnPlayer = Color.WHITE;
	}	

	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new Queen(Color.WHITE).getNaiveMoves();
		RelativeMove up = new RelativeMove(Direction.UP, true);
		RelativeMove down = new RelativeMove(Direction.DOWN, true);
		RelativeMove left = new RelativeMove(Direction.LEFT, true);
		RelativeMove right = new RelativeMove(Direction.RIGHT, true);
		RelativeMove upright = new RelativeMove(Direction.UPRIGHT, true);
		RelativeMove downright = new RelativeMove(Direction.DOWNRIGHT, true);
		RelativeMove upleft = new RelativeMove(Direction.UPLEFT, true);
		RelativeMove downleft = new RelativeMove(Direction.DOWNLEFT, true);
		assertTrue(naiveMoves.contains(left));
		assertTrue(naiveMoves.contains(right));
		assertTrue(naiveMoves.contains(up));
		assertTrue(naiveMoves.contains(down));
		assertTrue(naiveMoves.contains(upright));
		assertTrue(naiveMoves.contains(downright));
		assertTrue(naiveMoves.contains(upleft));
		assertTrue(naiveMoves.contains(downleft));
	}
	
	@Test
	public void validMoves() {
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(3, 4))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(3, 5))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(3, 2))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(2, 3))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(4, 3))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(4, 4))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(5, 5))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(2, 2))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(2, 4))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(4, 2))));
	}
}
