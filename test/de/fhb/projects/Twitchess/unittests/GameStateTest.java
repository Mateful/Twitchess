package de.fhb.projects.Twitchess.unittests;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

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
		//assertEquals(thirdState.getCurrentPlayer(), white);
		assertEquals(WHITE, thirdState.getCurrentColor());
		assertEquals(secondState, thirdState.getLastState());
		assertEquals(blackPawnMove, thirdState.getLastMove());
		
		assertEquals(new Pawn(new Position(0, 1), WHITE), firstState.getFigureAtStart(whitePawnMove));
		assertEquals(NoFigure.NO_FIGURE, firstState.getFigureAtDestination(whitePawnMove));
		
		assertEquals(new Pawn(new Position(0, 3), WHITE), secondState.getFigureAtDestination(whitePawnMove));
		assertEquals(NoFigure.NO_FIGURE, secondState.getFigureAtStart(whitePawnMove));
	}

	@Test
	public void getOpponent() {
		GameState state = new GameState(white, black);
		assertEquals(black, state.getOpponent(white));
		assertEquals(white, state.getOpponent(black));
	}
}
