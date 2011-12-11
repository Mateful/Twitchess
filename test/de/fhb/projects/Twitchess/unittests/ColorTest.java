package de.fhb.projects.Twitchess.unittests;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.NOCOLOR;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColorTest {
	@Test
	public void test() {
		assertEquals(WHITE, BLACK.getInverse());
		assertEquals(BLACK, WHITE.getInverse());
		assertEquals(NOCOLOR, NOCOLOR.getInverse());
	}
}
