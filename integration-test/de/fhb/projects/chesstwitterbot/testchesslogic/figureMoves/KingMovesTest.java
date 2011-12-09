package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.chesstwitterbot.games.chess.ChessLogic;
import de.fhb.projects.chesstwitterbot.games.chess.GameState;
import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.figures.King;
import de.fhb.projects.chesstwitterbot.games.chess.move.Direction;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.Move;
import de.fhb.projects.chesstwitterbot.games.chess.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.games.chess.player.Player;

public class KingMovesTest {
	private GameState state;
	private Player white, black;
	private King king;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		king = new King(new Position(3, 3));
		white.add(king);
		state = new GameState(white, black);
	}

	@Test
	public void getMoves() {
		List<Direction> directions = king.getDirections();
		Direction up = new OneStepDirection(DirectionType.UP);
		Direction down = new OneStepDirection(DirectionType.DOWN);
		Direction left = new OneStepDirection(DirectionType.LEFT);
		Direction right = new OneStepDirection(DirectionType.RIGHT);
		Direction upright = new OneStepDirection(DirectionType.UPRIGHT);
		Direction downright = new OneStepDirection(DirectionType.DOWNRIGHT);
		Direction upleft = new OneStepDirection(DirectionType.UPLEFT);
		Direction downleft = new OneStepDirection(DirectionType.DOWNLEFT);
		assertTrue(directions.contains(left));
		assertTrue(directions.contains(right));
		assertTrue(directions.contains(up));
		assertTrue(directions.contains(down));
		assertTrue(directions.contains(upright));
		assertTrue(directions.contains(downright));
		assertTrue(directions.contains(upleft));
		assertTrue(directions.contains(downleft));
	}

	@Test
	public void validMoves() {
		assertTrue(ChessLogic
				.isValidMove(state, Move.up(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.down(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.left(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.right(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upRight(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upLeft(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(king.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downLeft(king.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void twoStepsForward() {
		ChessLogic.isValidMove(state, Move.up(king.getPosition(), 2));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void twoStepsUpRight() {
		ChessLogic.isValidMove(state, Move.upRight(king.getPosition(), 2));
	}
}
