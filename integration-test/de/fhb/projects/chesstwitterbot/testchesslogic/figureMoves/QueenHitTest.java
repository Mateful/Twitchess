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

public class QueenHitTest {
	private GameState state;
	private Queen queen;
	private Player white, black;

	@Before
	public void initGame() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		queen = new Queen(new Position(3, 3));
		white.add(queen);
		black.add(new Pawn(new Position(3, 4), BLACK));
		black.add(new Pawn(new Position(4, 4), BLACK));
		black.add(new Pawn(new Position(4, 3), BLACK));
		black.add(new Pawn(new Position(4, 2), BLACK));
		black.add(new Pawn(new Position(3, 2), BLACK));
		black.add(new Pawn(new Position(2, 2), BLACK));
		black.add(new Pawn(new Position(2, 3), BLACK));
		black.add(new Pawn(new Position(2, 4), BLACK));
		state = new GameState(white, black);
	}

	@Test
	public void queenUpMove1StepHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.up(queen.getPosition(), 1)));
	}

	@Test
	public void queenRightMoveHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.right(queen.getPosition(), 1)));
	}

	@Test
	public void queenLeftMoveHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.left(queen.getPosition(), 1)));
	}

	@Test
	public void queenDownMoveHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.down(queen.getPosition(), 1)));
	}

	@Test
	public void queenUpRightMove1StepHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.upRight(queen.getPosition(), 1)));
	}

	@Test
	public void queenDownRightMoveHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.downRight(queen.getPosition(), 1)));
	}

	@Test
	public void queenDownLeftMoveHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.downLeft(queen.getPosition(), 1)));
	}

	@Test
	public void queenUpLeftMoveHit() {
		assertTrue(ChessLogic.isValidMove(state, Move.upLeft(queen.getPosition(), 1)));
	}
}
