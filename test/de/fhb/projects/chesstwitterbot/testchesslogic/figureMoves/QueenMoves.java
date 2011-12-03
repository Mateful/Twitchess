package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;

public class QueenMoves {
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
		RelativeMoveList naiveMoves = new Queen(start).getNaiveMoves();
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
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(3, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(3, 5))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(3, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(2, 3))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(4, 3))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(4, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(5, 5))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(2, 2))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(2, 4))));
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start, new Position(4, 2))));
	}
}
