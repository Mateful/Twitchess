package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.MoveBlockedException;

public class KnightBlockTest {
	private GameState state;
	private Knight knight;
	private Player white, black;

	@Before
	public void initGame() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		knight = new Knight(new Position(3, 3));
		white.add(knight);
		white.add(new Pawn(new Position(4, 5), WHITE));
		white.add(new Pawn(new Position(5, 4), WHITE));
		white.add(new Pawn(new Position(2, 1), WHITE));
		white.add(new Pawn(new Position(1, 2), WHITE));
		white.add(new Pawn(new Position(2, 5), WHITE));
		white.add(new Pawn(new Position(5, 2), WHITE));
		white.add(new Pawn(new Position(4, 1), WHITE));
		white.add(new Pawn(new Position(1, 4), WHITE));		
		state = new GameState(white, black);
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove1Blocked() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(4, 5)));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove2Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				5, 4))));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove3Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				2, 1))));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove4Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				1, 2))));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove5Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				2, 5))));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove6Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				5, 2))));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove7Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				4, 1))));
	}

	@Test(expected = MoveBlockedException.class)
	public void isKnightMove8Blocked() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(), new Position(
				1, 4))));
	}
}
