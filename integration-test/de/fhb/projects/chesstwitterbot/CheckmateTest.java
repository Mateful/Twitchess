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
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;

public class CheckmateTest {
	private GameState state;
	private King king;
	private Rook rook1, rook2;

	@Before
	public void init() {
		state = new GameState();
	}
	
	@Test
	public void isInCheck() {
		state.white.add(new King(new Position(0, 0)));
		state.white.add(new Queen(new Position(1, 0)));
		state.black.add(new King(new Position(1, 3)));
		state.updatePositions();
		
		assertFalse(ChessLogic.isCheck(state, state.white));
		assertTrue(ChessLogic.isCheck(state, state.black));
	}

	@Test
	public void checkmate() {
		king = new King(new Position(0, 0));
		rook1 = new Rook(new Position(0, 7));
		rook2 = new Rook(new Position(1, 7));
		state.white.add(king);
		state.black.add(rook1, rook2);
		state.currentTurnPlayer = state.white;
		state.updatePositions();

		assertTrue(ChessLogic.isCheckmate(state, state.white));
	}

	@Test
	public void noCheckmate() {
		king = new King(new Position(0, 0));
		rook1 = new Rook(new Position(0, 7));
		rook2 = new Rook(new Position(2, 7));
		state.white.add(king);
		state.black.add(rook1, rook2);
		state.updatePositions();

		assertFalse(ChessLogic.isCheckmate(state, state.white));
	}
}
