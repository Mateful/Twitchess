package de.fhb.projects.chesstwitterbot;

import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.games.chess.ChessLogic;
import de.fhb.projects.chesstwitterbot.games.chess.GameState;
import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.figures.King;
import de.fhb.projects.chesstwitterbot.games.chess.figures.Queen;
import de.fhb.projects.chesstwitterbot.games.chess.move.Move;
import de.fhb.projects.chesstwitterbot.games.chess.player.Player;

public class AllMovesTest {
	private GameState state;
	private Player white, black;

	@Before
	public void init() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		state = new GameState(white, black);
	}

	@Test
	public void getAllMovesOfFreeKing() {
		King king = new King(new Position(3, 3));
		white.add(king);
		state.updatePositions();

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
		King king = new King(new Position(0, 0));
		white.add(king);
		state.updatePositions();

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
		Queen queen = new Queen(new Position(7, 0));
		white.add(queen);
		state.updatePositions();

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
