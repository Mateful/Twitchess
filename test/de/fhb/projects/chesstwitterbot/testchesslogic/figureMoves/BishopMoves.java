package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Bishop;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;

public class BishopMoves {
	private ChessLogic cl;
	private Position start;

	@Before
	public void initPawnTests() {
		cl = new ChessLogic();
		start = new Position(3, 3);
		cl.white.add(new Bishop(start));
		cl.updatePositions();
	}	

	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new Bishop(start).getNaiveMoves();
		RelativeMove upright = new RelativeMove(Direction.UPRIGHT, true);
		RelativeMove downright = new RelativeMove(Direction.DOWNRIGHT, true);
		RelativeMove upleft = new RelativeMove(Direction.UPLEFT, true);
		RelativeMove downleft = new RelativeMove(Direction.DOWNLEFT, true);
		assertTrue(naiveMoves.contains(upright));
		assertTrue(naiveMoves.contains(downright));
		assertTrue(naiveMoves.contains(upleft));
		assertTrue(naiveMoves.contains(downleft));
	}
	
	@Test
	public void validMoves() {
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(4, 4))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(2, 2))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(2, 4))));
		assertTrue(cl.isValidMove(new AbsoluteMove(start, new Position(4, 2))));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void up() {
		cl.isValidMove(new AbsoluteMove(start, new Position(3, 4)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void down() {
		cl.isValidMove(new AbsoluteMove(start, new Position(3, 2)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void left() {
		cl.isValidMove(new AbsoluteMove(start, new Position(2, 3)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void right() {
		cl.isValidMove(new AbsoluteMove(start, new Position(4, 3)));
	}
}
