package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.games.chess.ChessLogic;
import de.fhb.projects.chesstwitterbot.games.chess.GameState;
import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.figures.Queen;
import de.fhb.projects.chesstwitterbot.games.chess.move.Direction;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.games.chess.move.Move;
import de.fhb.projects.chesstwitterbot.games.chess.player.Player;

public class QueenMovesTest {
	private GameState state;
	private Queen queen;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		queen = new Queen(new Position(3, 3));
		white.add(queen);
		state = new GameState(white, black);
	}

	@Test
	public void getMoves() {
		List<Direction> directions = queen.getDirections();
		Direction up = new InfiniteDirection(DirectionType.UP);
		Direction down = new InfiniteDirection(DirectionType.DOWN);
		Direction left = new InfiniteDirection(DirectionType.LEFT);
		Direction right = new InfiniteDirection(DirectionType.RIGHT);
		Direction upright = new InfiniteDirection(DirectionType.UPRIGHT);
		Direction downright = new InfiniteDirection(DirectionType.DOWNRIGHT);
		Direction upleft = new InfiniteDirection(DirectionType.UPLEFT);
		Direction downleft = new InfiniteDirection(DirectionType.DOWNLEFT);
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
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.down(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.left(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.right(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upRight(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.upLeft(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downLeft(queen.getPosition(), 1)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(queen.getPosition(), 2)));
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(queen.getPosition(), 3)));
	}
}
