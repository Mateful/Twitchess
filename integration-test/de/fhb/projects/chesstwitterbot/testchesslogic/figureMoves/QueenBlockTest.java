package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.MoveBlockedException;

public class QueenBlockTest {
	private GameState state;
	private Queen queen;
	private Player white, black;

	@Before
	public void initGame() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		queen = new Queen(new Position(3, 3));
		white.add(queen);
		white.add(new Pawn(new Position(3, 4), WHITE));
		white.add(new Pawn(new Position(4, 4), WHITE));
		white.add(new Pawn(new Position(4, 3), WHITE));
		white.add(new Pawn(new Position(4, 2), WHITE));
		white.add(new Pawn(new Position(3, 2), WHITE));
		white.add(new Pawn(new Position(2, 2), WHITE));
		white.add(new Pawn(new Position(2, 3), WHITE));
		white.add(new Pawn(new Position(2, 4), WHITE));
		state = new GameState(white, black);
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenUpMove1StepBlocked() {
		ChessLogic.isValidMove(state, Move.up(queen.getPosition(), 1));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenUpMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.up(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenLeftMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.left(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenRightMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.right(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenDownMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.up(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenRightMoveBlocked() {
		ChessLogic.isValidMove(state, Move.right(queen.getPosition(), 1));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenLeftMoveBlocked() {
		ChessLogic.isValidMove(state, Move.left(queen.getPosition(), 1));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenDownMoveBlocked() {
		ChessLogic.isValidMove(state, Move.down(queen.getPosition(), 1));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenUpRightMove1StepBlocked() {
		assertTrue(ChessLogic.isValidMove(state, Move.upRight(queen.getPosition(), 1)));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenUpRightMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.upRight(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenUpLeftMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.upLeft(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenDownLeftMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.downLeft(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenDownRightMove2StepsBlocked() {
		ChessLogic.isValidMove(state, Move.downRight(queen.getPosition(), 2));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenDownRightMoveBlocked() {
		ChessLogic.isValidMove(state, Move.downRight(queen.getPosition(), 1));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenDownLeftMoveBlocked() {
		ChessLogic.isValidMove(state, Move.downLeft(queen.getPosition(), 1));
	}

	@Test(expected = MoveBlockedException.class)
	public void isQueenUpLeftMoveBlocked() {
		ChessLogic.isValidMove(state, Move.upLeft(queen.getPosition(), 1));
	}
}
