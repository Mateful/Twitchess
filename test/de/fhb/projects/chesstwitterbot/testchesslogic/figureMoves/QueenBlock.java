package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;

public class QueenBlock {
	private GameState state;
	private Position start;

	@Before
	public void initGame() {
		state = new GameState();
		start = new Position(3, 3);
		state.white.add(new Queen(start));

		state.white.add(new Pawn(new Position(3, 4), WHITE));
		state.white.add(new Pawn(new Position(4, 4), WHITE));
		state.white.add(new Pawn(new Position(4, 3), WHITE));
		state.white.add(new Pawn(new Position(4, 2), WHITE));
		state.white.add(new Pawn(new Position(3, 2), WHITE));
		state.white.add(new Pawn(new Position(2, 2), WHITE));
		state.white.add(new Pawn(new Position(2, 3), WHITE));
		state.white.add(new Pawn(new Position(2, 4), WHITE));
		state.updatePositions();
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenUpMove1StepBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 4)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenUpMove2StepsBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 5)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenRightMoveBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 3)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenLeftMoveBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 3)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenDownMoveBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(3, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenUpRightMove1StepBlocked() {
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 4))));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenUpRightMove2StepsBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(5, 5)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenDownRightMoveBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(4, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenDownLeftMoveBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 2)));
	}

	@Test(expected = InvalidMoveException.class)
	public void isQueenUpLeftMoveBlocked() {
		ChessLogic.isValidMove(state, new AbsoluteMove(start,
				new Position(2, 4)));
	}
}
