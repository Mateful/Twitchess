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
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class KingMovesTest {
	private GameState state;
	private Player white, black;
	private King king;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		king = new King(new Position(3, 3));
		white.add(king);
		state = new GameState(white, black);
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic
				.isValidMove(state, Move.up(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.down(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.left(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.right(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upRight(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upLeft(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downLeft(king.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void twoStepsForward() {
		ChessLogic.isValidMove(state, Move.up(king.getPosition(), 2));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void twoStepsUpRight() {
		ChessLogic.isValidMove(state, Move.upRight(king.getPosition(), 2));
	}
}
