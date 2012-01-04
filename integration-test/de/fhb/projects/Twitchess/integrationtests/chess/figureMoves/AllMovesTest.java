package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class AllMovesTest {
	private GameState state;

	@Test
	public void getAllMovesOfFreeKing() {
		state = new Fen("8/8/8/8/3k4/8/8/8 w - - 0 1").getGameState();
		King king = new King(new Position(3, 3));

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
		assertEquals(8, moves.size());
	}

	@Test
	public void getAllMovesOfKingInBottomLeftCorner() {
		state = new Fen("8/8/8/8/8/8/8/K7 w - - 0 1").getGameState();
		King king = new King(new Position(0, 0));

		List<Move> moves = ChessLogic.getAllMoves(state, king);
		Move up = Move.up(king.getPosition(), 1);
		Move right = Move.right(king.getPosition(), 1);
		Move upright = Move.upRight(king.getPosition(), 1);
		assertTrue(moves.contains(up));
		assertTrue(moves.contains(right));
		assertTrue(moves.contains(upright));
		assertEquals(3, moves.size());
	}

	@Test
	public void getAllMovesOfQueenInBottomRightCorner() {
		state = new Fen("8/8/8/8/8/8/8/7Q w - - 0 1").getGameState();
		Queen queen = new Queen(new Position(7, 0));

		List<Move> moves = ChessLogic.getAllMoves(state, queen);
		Move up1 = Move.up(queen.getPosition(), 1);
		Move left1 = Move.left(queen.getPosition(), 1);
		Move upleft1 = Move.upLeft(queen.getPosition(), 1);
		Move upleft2 = Move.upLeft(queen.getPosition(), 2);
		assertTrue(moves.contains(up1));
		assertTrue(moves.contains(left1));
		assertTrue(moves.contains(upleft1));
		assertTrue(moves.contains(upleft2));
	}
}
