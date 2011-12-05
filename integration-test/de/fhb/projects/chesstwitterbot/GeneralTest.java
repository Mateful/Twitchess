package de.fhb.projects.chesstwitterbot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public class GeneralTest {
	private GameState state;

	@Before
	public void initGame() {
		state = new GameState();
		state.white.add(new King(new Position(0, 0)));
		state.white.add(new Queen(new Position(1, 0)));
		state.black.add(new King(new Position(1, 3)));
		state.updatePositions();
	}

	@Test(expected = InvalidMoveException.class)
	public void isNoFigureToMove() {
		ChessLogic.isValidMove(state, new Move(new Position(0, 4),
				new Position(0, 5)));
	}

	@Test
	public void isInCheck() {
		assertFalse(ChessLogic.isCheck(state, state.white));
		assertTrue(ChessLogic.isCheck(state, state.black));
	}
}
