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
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.FigureCannotMoveIntoDirectionException;

public class RookMovesTest {
	private GameState state;
	private Rook rook;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		rook = new Rook(new Position(3, 3));
		white.add(rook);
		state = new GameState(white, black);
	}

	@Test
	public void getMoves() {
		List<Direction> directions = rook.getDirections();
		Direction up = new InfiniteDirection(DirectionType.UP);
		Direction down = new InfiniteDirection(DirectionType.DOWN);
		Direction left = new InfiniteDirection(DirectionType.LEFT);
		Direction right = new InfiniteDirection(DirectionType.RIGHT);
		assertTrue(directions.contains(left));
		assertTrue(directions.contains(right));
		assertTrue(directions.contains(up));
		assertTrue(directions.contains(down));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic.isValidMove(state, Move.up(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.down(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.left(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.right(rook.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state, Move.up(rook.getPosition(), 4)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upRight() {
		ChessLogic.isValidMove(state, Move.upRight(rook.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downRight() {
		ChessLogic.isValidMove(state, Move.downRight(rook.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void downLeft() {
		ChessLogic.isValidMove(state, Move.downLeft(rook.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void upLeft() {
		ChessLogic.isValidMove(state, Move.upLeft(rook.getPosition(), 1));
	}
}
