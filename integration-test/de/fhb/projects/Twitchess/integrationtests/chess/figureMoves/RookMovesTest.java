package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class RookMovesTest {
	private GameState state;
	private Rook rook;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		rook = new Rook(new Position(3, 3));
		white.add(rook);
		state = new GameState(white, black);
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic
				.isValidMove(state, Move.up(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.down(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.left(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.right(rook.getPosition(), 1)));
		assertTrue(ChessLogic
				.isValidMove(state, Move.up(rook.getPosition(), 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upRight() {
		ChessLogic.isValidMove(state, Move.upRight(rook.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downRight() {
		ChessLogic.isValidMove(state, Move.downRight(rook.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downLeft() {
		ChessLogic.isValidMove(state, Move.downLeft(rook.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upLeft() {
		ChessLogic.isValidMove(state, Move.upLeft(rook.getPosition(), 1));
	}
}
