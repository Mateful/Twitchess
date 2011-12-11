package de.fhb.projects.Twitchess.integrationtests.chess;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.exception.NoFigureException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

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
