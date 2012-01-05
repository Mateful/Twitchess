package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class HitFigureTest {
	@Test
	public void enPassantWhiteHitRemovesFigure() {
		Fen fen = new Fen("k7/8/8/pP6/8/8/8/7K w - a6 0 1");
		GameState state = fen.getGameState();
		Move m = Move.upLeft(new Position(1, 4), 1);

		assertTrue(ChessLogic.isValidMove(state, m));

		GameState nextState = new GameState(state, m);
		assertEquals(nextState.getFigure(0, 4), NoFigure.NO_FIGURE);
		assertTrue(nextState.getFigure(0, 5) instanceof Pawn);
		assertFalse(nextState.getCurrentPlayer().getFiguresInGame()
				.contains(m.getHitTarget()));
	}

	@Test
	public void enPassantBlackHitRemovesFigure() {
		Fen fen = new Fen("k7/8/8/8/5Pp1/8/8/7K b - f3 0 1");
		GameState state = fen.getGameState();
		Move m = Move.downLeft(new Position(6, 3), 1);

		assertTrue(ChessLogic.isValidMove(state, m));

		GameState nextState = new GameState(state, m);
		assertEquals(nextState.getFigure(5, 3), NoFigure.NO_FIGURE);
		assertTrue(nextState.getFigure(5, 2) instanceof Pawn);
		assertFalse(nextState.getCurrentPlayer().getFiguresInGame()
				.contains(m.getHitTarget()));
	}

	@Test
	public void enPassantBlack2HitRemovesFigure() {
		Fen fen = new Fen("k7/8/8/8/4pP2/8/8/7K b - f3 0 1");
		GameState state = fen.getGameState();
		Move m = Move.downRight(new Position(4, 3), 1);

		assertTrue(ChessLogic.isValidMove(state, m));

		GameState nextState = new GameState(state, m);
		assertEquals(nextState.getFigure(5, 3), NoFigure.NO_FIGURE);
		assertTrue(nextState.getFigure(5, 2) instanceof Pawn);
		assertFalse(nextState.getCurrentPlayer().getFiguresInGame()
				.contains(m.getHitTarget()));
	}

	@Test
	public void queenHitExchangesFigure() {
		Fen fen = new Fen("k7/8/8/p7/Q7/8/8/7K w - a6 0 1");
		GameState state = fen.getGameState();
		Move m = Move.up(new Position(0, 3), 1);

		assertTrue(ChessLogic.isValidMove(state, m));

		GameState nextState = new GameState(state, m);
		assertTrue(nextState.getFigure(0, 4) instanceof Queen);
		assertFalse(nextState.getCurrentPlayer().getFiguresInGame()
				.contains(m.getHitTarget()));
	}
}
