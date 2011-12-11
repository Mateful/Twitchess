package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class EnPassantTest {
	private GameState state;
	private Player white, black;
	private Pawn blackPawn, whitePawn;

	@Before
	public void init() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		state = new GameState(white, black);
	}

	@Test
	public void enPassantBlackHitsWhite() {
		blackPawn = new Pawn(new Position(1, 3), BLACK);
		whitePawn = new Pawn(new Position(0, 1), WHITE);
		white.add(whitePawn);
		black.add(blackPawn);
		state.updatePositions();

		GameState nextState = new GameState(state, Move.up(
				whitePawn.getPosition(), 2));
		assertTrue(ChessLogic.isValidMove(nextState,
				Move.downLeft(blackPawn.getPosition(), 1)));
	}

	@Test
	public void enPassantWhiteHitsBlack() {
		blackPawn = new Pawn(new Position(3, 6), BLACK);
		whitePawn = new Pawn(new Position(2, 3), WHITE);
		white.add(whitePawn);
		black.add(blackPawn);
		state.updatePositions();

		GameState whitePawnMoves1Up = new GameState(state, Move.up(
				whitePawn.getPosition(), 1));
		GameState blackPawnMoves2Down = new GameState(whitePawnMoves1Up,
				Move.down(blackPawn.getPosition(), 2));
		assertTrue(ChessLogic.isValidMove(blackPawnMoves2Down,
				Move.upRight(whitePawn.getPosition(), 1)));
	}
}
