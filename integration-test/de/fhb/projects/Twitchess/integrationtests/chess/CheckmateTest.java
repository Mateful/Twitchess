package de.fhb.projects.Twitchess.integrationtests.chess;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class CheckmateTest {
	private GameState state;
	private Player white, black;
	private King king;
	private Rook rook1, rook2;

	@Before
	public void init() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		state = new GameState(white, black);
	}

	@Test
	public void isInCheck() {
		white.add(new King(new Position(0, 0)));
		white.add(new Queen(new Position(1, 0)));
		black.add(new King(new Position(1, 3)));
		state = new GameState(white, black);
		assertFalse(ChessLogic.isCheck(state, white));
		assertTrue(ChessLogic.isCheck(state, black));
	}

	@Test
	public void checkmate() {
		king = new King(new Position(0, 0));
		rook1 = new Rook(new Position(0, 7));
		rook2 = new Rook(new Position(1, 7));
		white.add(king);
		black.add(rook1, rook2);
		state = new GameState(white, black);
		assertTrue(ChessLogic.isCheckmate(state, white));
	}

	@Test
	public void noCheckmate() {
		king = new King(new Position(0, 0));
		rook1 = new Rook(new Position(0, 7));
		rook2 = new Rook(new Position(2, 7));
		white.add(king);
		black.add(rook1, rook2);
		state.updatePositions();
		assertFalse(ChessLogic.isCheckmate(state, white));
	}
}
