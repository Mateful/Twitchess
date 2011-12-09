package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.FigureCannotMoveIntoDirectionException;

public class KnightMovesTest {
	private GameState state;
	private Knight knight;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		knight = new Knight(new Position(3, 3));
		white.add(knight);
		state = new GameState(white, black);
	}

	@Test
	public void getMoves() {
		List<Direction> directions = knight.getDirections();
		Direction knight = new OneStepDirection(DirectionType.KNIGHT);
		assertTrue(directions.contains(knight));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 5))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(5, 4))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 1))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(1, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 5))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(5, 2))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 1))));
		assertTrue(ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(1, 4))));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upRight() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downRight() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downLeft() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upLeft() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void up() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(3, 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void down() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(3, 2)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void left() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(2, 3)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void right() {
		ChessLogic.isValidMove(state, new Move(knight.getPosition(),
				new Position(4, 3)));
	}
}
