package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;

public class KnightBlock {
	private GameState state;
	private Position start;

	@Before
	public void initGame() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new Knight(start));

		state.white.add(new Pawn(new Position(4, 5), WHITE));
		state.white.add(new Pawn(new Position(5, 4), WHITE));
		state.white.add(new Pawn(new Position(2, 1), WHITE));
		state.white.add(new Pawn(new Position(1, 2), WHITE));
		state.white.add(new Pawn(new Position(2, 5), WHITE));
		state.white.add(new Pawn(new Position(5, 2), WHITE));
		state.white.add(new Pawn(new Position(4, 1), WHITE));
		state.white.add(new Pawn(new Position(1, 4), WHITE));
		state.updatePositions();
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove1Blocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 5)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove2Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(5, 4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove3Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 1))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove4Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(1, 2))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove5Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 5))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove6Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(5, 2))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove7Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 1))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isKnightMove8Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(1, 4))));
	}
}
