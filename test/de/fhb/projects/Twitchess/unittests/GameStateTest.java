package de.fhb.projects.Twitchess.unittests;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class GameStateTest {
	private Player white, black;

	@Before
	public void init() {
		white = Player.getFullyInitializedPlayer(WHITE);
		black = Player.getFullyInitializedPlayer(BLACK);
	}

	@Test
	public void game() {
		GameState firstState = new GameState(white, black);
		assertEquals(firstState.getCurrentPlayer(), white);
		assertEquals(WHITE, firstState.getCurrentColor());
		assertNull(firstState.getLastState());
		assertEquals(Move.NO_MOVE, firstState.getLastMove());

		Move whitePawnMove = new Move(new Position(0, 1), new Position(0, 3));
		assertEquals(new Pawn(new Position(0, 1), WHITE),
				firstState.getFigureAtStart(whitePawnMove));

		GameState secondState = new GameState(firstState, whitePawnMove);
		assertEquals(secondState.getCurrentPlayer(), black);
		assertEquals(BLACK, secondState.getCurrentColor());
		assertEquals(firstState, secondState.getLastState());
		assertEquals(whitePawnMove, secondState.getLastMove());

		Move blackPawnMove = new Move(new Position(0, 6), new Position(0, 4));
		assertEquals(new Pawn(new Position(0, 6), BLACK),
				firstState.getFigureAtStart(blackPawnMove));

		GameState thirdState = new GameState(secondState, blackPawnMove);
		// assertEquals(thirdState.getCurrentPlayer(), white);
		assertEquals(WHITE, thirdState.getCurrentColor());
		assertEquals(secondState, thirdState.getLastState());
		assertEquals(blackPawnMove, thirdState.getLastMove());

		assertEquals(new Pawn(new Position(0, 1), WHITE),
				firstState.getFigureAtStart(whitePawnMove));
		assertEquals(NoFigure.NO_FIGURE,
				firstState.getFigureAtDestination(whitePawnMove));

		assertEquals(new Pawn(new Position(0, 3), WHITE),
				secondState.getFigureAtDestination(whitePawnMove));
		assertEquals(NoFigure.NO_FIGURE,
				secondState.getFigureAtStart(whitePawnMove));
	}

	@Test
	public void getOpponent() {
		GameState state = new GameState(white, black);
		assertEquals(black, state.getOpponent(white));
		assertEquals(white, state.getOpponent(black));
	}

	@Test
	public void sameStateEverythingEqualsEverything1() {
		GameState state = Fen.createGameState(Fen.START_POSITION);
		assertTrue(state.hasSamePositioningAndColor(state));
	}

	@Test
	public void sameStateEverythingEqualsEverything2() {
		GameState state1 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		GameState state2 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertTrue(state1.hasSamePositioningAndColor(state2));
	}

	@Test
	public void sameStateEverythingEqualsEverything3() {
		GameState state1 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 1");
		GameState state2 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertTrue(state1.hasSamePositioningAndColor(state2));
	}

	@Test
	public void sameStateEverythingEqualsEverything4() {
		GameState state1 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 56 1");
		GameState state2 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 34 1");
		assertTrue(state1.hasSamePositioningAndColor(state2));
	}

	@Test
	public void sameStateEverythingEqualsEverything5() {
		GameState state1 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 54");
		GameState state2 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 34");
		assertTrue(state1.hasSamePositioningAndColor(state2));
	}

	@Test
	public void sameStateDifferentPositions() {
		GameState state1 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		GameState state2 = Fen
				.createGameState("rnbqkbnr/8/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertFalse(state1.hasSamePositioningAndColor(state2));
	}

	@Test
	public void sameStateDifferentColor() {
		GameState state1 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		GameState state2 = Fen
				.createGameState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR b KQkq - 0 1");
		assertFalse(state1.hasSamePositioningAndColor(state2));
	}
}
