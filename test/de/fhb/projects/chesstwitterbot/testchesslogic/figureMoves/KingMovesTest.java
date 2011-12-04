package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;

public class KingMovesTest {
	private GameState state;
	private Position start;

	@Before
	public void initPawnTests() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new King(start));
		state.updatePositions();
	}	

	@Test
	public void getMoves() {
		RelativeMoveList naiveMoves = new King(start).getNaiveMoves();
		RelativeMove up = new RelativeMove(Direction.UP, false);
		RelativeMove down = new RelativeMove(Direction.DOWN, false);
		RelativeMove left = new RelativeMove(Direction.LEFT, false);
		RelativeMove right = new RelativeMove(Direction.RIGHT, false);
		RelativeMove upright = new RelativeMove(Direction.UPRIGHT, false);
		RelativeMove downright = new RelativeMove(Direction.DOWNRIGHT, false);
		RelativeMove upleft = new RelativeMove(Direction.UPLEFT, false);
		RelativeMove downleft = new RelativeMove(Direction.DOWNLEFT, false);
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
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(3, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(3, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(2, 3))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(4, 3))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(4, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(2, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(2, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(4, 2))));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void twoStepsForward() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(3, 5)));
	}
	
	@Test(expected = InvalidMoveException.class)
	public void twoStepsUpRight() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(5, 5)));
	}
}