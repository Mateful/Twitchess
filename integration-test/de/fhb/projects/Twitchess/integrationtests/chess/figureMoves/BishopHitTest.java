package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class BishopHitTest {
	private GameState state, nextState;
	private Bishop bishop;
	private Player white, black;

	@Before
	public void initGame() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		bishop = new Bishop(new Position(3, 3));
		white.add(bishop);
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

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void bishopUpMove1StepHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(bishop.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void bishopRightMoveHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.right(bishop.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void bishopLeftMoveHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.left(bishop.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void bishopDownMoveHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.down(bishop.getPosition(), 1)));
	}

	@Test
	public void bishopUpRightMove1StepHit() {
		Move move = Move.upRight(bishop.getPosition(), 1);
		assertTrue(ChessLogic.isValidMove(state, move));
		nextState = new GameState(state, move);
		assertEquals(NoFigure.NO_FIGURE, nextState.getFigure(move.getStart()));
		assertEquals(new Bishop(move.getDestination(), WHITE),
				nextState.getFigure(move.getDestination()));
	}

	@Test
	public void bishopDownRightMoveHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.downRight(bishop.getPosition(), 1)));
	}

	@Test
	public void bishopDownLeftMoveHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.downLeft(bishop.getPosition(), 1)));
	}

	@Test
	public void bishopUpLeftMoveHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.upLeft(bishop.getPosition(), 1)));
	}
}
