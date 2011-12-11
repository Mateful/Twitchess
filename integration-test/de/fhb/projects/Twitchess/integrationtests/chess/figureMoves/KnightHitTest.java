package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class KnightHitTest {
	private GameState state;
	private Knight knight;
	private Player white, black;

	@Before
	public void initGame() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		knight = new Knight(new Position(3, 3));
		white.add(knight);
		black.add(new Pawn(new Position(4, 5), BLACK));
		black.add(new Pawn(new Position(5, 4), BLACK));
		black.add(new Pawn(new Position(2, 1), BLACK));
		black.add(new Pawn(new Position(1, 2), BLACK));
		black.add(new Pawn(new Position(2, 5), BLACK));
		black.add(new Pawn(new Position(5, 2), BLACK));
		black.add(new Pawn(new Position(4, 1), BLACK));
		black.add(new Pawn(new Position(1, 4), BLACK));
		state = new GameState(white, black);
	}

	@Test
	public void isKnightMove1Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 5))));
	}

	@Test
	public void isKnightMove2Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(5, 4))));
	}

	@Test
	public void isKnightMove3Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 1))));
	}

	@Test
	public void isKnightMove4Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(1, 2))));
	}

	@Test
	public void isKnightMove5Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 5))));
	}

	@Test
	public void isKnightMove6Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(5, 2))));
	}

	@Test
	public void isKnightMove7Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 1))));
	}

	@Test
	public void isKnightMove8Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(1, 4))));
	}
}
