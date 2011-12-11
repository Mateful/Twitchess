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
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class BishopMovesTest {
	private GameState state;
	private Bishop bishop;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		bishop = new Bishop(new Position(3, 3));
		white.add(bishop);
		state = new GameState(white, black);
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.upRight(bishop.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upLeft(bishop.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(bishop.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downLeft(bishop.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void up() {
		ChessLogic.isValidMove(state, Move.up(bishop.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void down() {
		ChessLogic.isValidMove(state, Move.down(bishop.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void left() {
		ChessLogic.isValidMove(state, Move.left(bishop.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void right() {
		ChessLogic.isValidMove(state, Move.right(bishop.getPosition(), 1));
	}
}
