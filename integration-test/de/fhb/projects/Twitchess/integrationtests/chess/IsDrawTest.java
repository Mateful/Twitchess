package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;

public class IsDrawTest {
	@Test
	public void test() {
		Fen f = new Fen("6k1/8/8/8/5n2/8/6r1/7K w - - 0 1");
		GameState s = f.createGameState();
		assertTrue(ChessLogic.isDraw(s));
	}
}
