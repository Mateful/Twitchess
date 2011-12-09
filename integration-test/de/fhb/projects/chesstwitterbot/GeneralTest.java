package de.fhb.projects.chesstwitterbot;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.NoFigureException;

public class GeneralTest {
	private GameState state;
	private Player white, black;

	@Before
	public void initGame() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		state = new GameState(white, black);
	}

	@Test(expected = NoFigureException.class)
	public void isNoFigureToMove() {
		ChessLogic.isValidMove(state, new Move(new Position(0, 4),
				new Position(0, 5)));
	}
}
