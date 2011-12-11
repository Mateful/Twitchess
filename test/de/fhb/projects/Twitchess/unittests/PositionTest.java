package de.fhb.projects.Twitchess.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;

public class PositionTest {
	@Test
	public void equals() {
		Position p = new Position(0, 0);
		assertEquals(p, p);
		assertEquals(new Position(0, 0), p);
		assertFalse(p.equals(null));
		assertFalse(p.equals(new Object()));
		assertFalse(p.equals(new Position(0, 1)));
	}
}
