package de.fhb.projects.chesstwitterbot;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;

public class AllMovesTest {
	private GameState state;
	private King king;

	@Before
	public void init() {
		state = new GameState();
	}
	
	@Test
	public void getAllMovesOfFreeKing() {
		king = new King(new Position(3, 3));
		state.white.add(king);
		state.updatePositions();
		state.currentTurnPlayer = state.white;
		
		List<Move> moves = ChessLogic.getAllMoves(state, king);
		Move up = Move.up(king.getPosition(), 1);
		Move down = Move.down(king.getPosition(), 1);
		Move left = Move.left(king.getPosition(), 1);
		Move right = Move.right(king.getPosition(), 1);
		Move upright = Move.upRight(king.getPosition(), 1);
		Move downright = Move.upLeft(king.getPosition(), 1);
		Move upleft = Move.downRight(king.getPosition(), 1);
		Move downleft = Move.downLeft(king.getPosition(), 1);		
		assertTrue(moves.contains(up));
		assertTrue(moves.contains(down));
		assertTrue(moves.contains(left));
		assertTrue(moves.contains(right));
		assertTrue(moves.contains(upright));
		assertTrue(moves.contains(downright));
		assertTrue(moves.contains(upleft));
		assertTrue(moves.contains(downleft));
	}
	
	@Test
	public void getAllMovesOfKingInCorner() {
		king = new King(new Position(0, 0));
		state.white.add(king);
		state.updatePositions();
		state.currentTurnPlayer = state.white;
		
		List<Move> moves = ChessLogic.getAllMoves(state, king);
		Move up = Move.up(king.getPosition(), 1);
		Move right = Move.right(king.getPosition(), 1);
		Move upright = Move.upRight(king.getPosition(), 1);		
		assertTrue(moves.contains(up));
		assertTrue(moves.contains(right));
		assertTrue(moves.contains(upright));
	}
}
