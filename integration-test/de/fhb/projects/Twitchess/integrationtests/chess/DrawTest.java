package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class DrawTest {
	@Test
	public void drawByHalfMoves() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 50 1");
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isDraw(state));
	}

	@Test
	public void drawByNoMovesLeft() {
		Fen fen = new Fen("1r6/8/8/8/8/8/7r/K7 w - - 0 1");
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isDraw(state));
	}

	@Test
	public void drawBy3TimesSamePositioning() {
		GameState lastState = Fen.createGameState(Fen.START_POSITION);
		// Players do the same thing for 3 turns
		for (int i = 0; i < 3; i++) {
			Move whiteJump = new Move(new Position(1, 0), new Position(0, 2));
			assertTrue(ChessLogic.isValidMove(lastState, whiteJump));
			lastState = new GameState(lastState, whiteJump);

			Move blackJump = new Move(new Position(1, 7), new Position(0, 5));
			assertTrue(ChessLogic.isValidMove(lastState, blackJump));
			lastState = new GameState(lastState, blackJump);

			Move whiteJumpBack = new Move(new Position(0, 2),
					new Position(1, 0));
			assertTrue(ChessLogic.isValidMove(lastState, whiteJumpBack));
			lastState = new GameState(lastState, whiteJumpBack);

			Move blackJumpBack = new Move(new Position(0, 5),
					new Position(1, 7));
			assertTrue(ChessLogic.isValidMove(lastState, blackJumpBack));
			lastState = new GameState(lastState, blackJumpBack);
			// draw at turn 3
			if (i == 2) {
				assertTrue(ChessLogic.isDraw(lastState));
			}
		}
	}

	@Test
	public void resetHalfMoveClockByPawnMove() {
		Fen fen = new Fen(
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 24 1");
		GameState state = fen.getGameState();
		Move pawnMove = Move.up(new Position(0, 1), 1);
		assertTrue(ChessLogic.isValidMove(state, pawnMove));
		GameState nextState = new GameState(state, pawnMove);
		assertEquals(0, nextState.getHalfMoveClock());
	}

	@Test
	public void resetHalfMoveClockByCaptureMove() {
		Fen fen = new Fen("8/8/8/8/8/8/1p6/B7 w KQkq - 24 1");
		GameState state = fen.getGameState();
		Move pawnMove = Move.upRight(new Position(0, 0), 1);
		assertTrue(ChessLogic.isValidMove(state, pawnMove));
		GameState nextState = new GameState(state, pawnMove);
		assertEquals(0, nextState.getHalfMoveClock());
	}
}
