package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class QueenMovesTest {
	private GameState state;
	private Queen queen;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		queen = new Queen(new Position(3, 3));
		white.add(queen);
		state = new GameState(white, black);
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.down(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.left(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.right(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upRight(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upLeft(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downLeft(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(queen.getPosition(), 2)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(queen.getPosition(), 3)));
	}
}
