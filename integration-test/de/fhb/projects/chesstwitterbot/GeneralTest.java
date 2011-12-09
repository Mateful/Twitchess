package de.fhb.projects.chesstwitterbot;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.exception.NoFigureException;

public class GeneralTest {
	private GameState state;

	@Before
	public void initGame() {
		state = new GameState();
	}

	@Test(expected = NoFigureException.class)
	public void isNoFigureToMove() {
		ChessLogic.isValidMove(state, new Move(new Position(0, 4),
				new Position(0, 5)));
	}
}
