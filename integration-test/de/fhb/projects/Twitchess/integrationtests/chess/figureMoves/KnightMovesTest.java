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
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class KnightMovesTest {
	private GameState state;
	private Knight knight;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		knight = new Knight(new Position(3, 3));
		white.add(knight);
		state = new GameState(white, black);
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 5))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(5, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 1))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(1, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 5))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(5, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 1))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(1, 4))));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upRight() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downRight() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downLeft() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upLeft() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void up() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(3, 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void down() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(3, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void left() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 3)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void right() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 3)));
	}
}
