package de.fhb.projects.Twitchess.unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class MoveTest {
	@Test
	public void equals() {
		Move move = new Move(new Position(0, 0), new Position(0, 1));
		assertEquals(move, move);
		assertEquals(new Move(new Position(0, 0), new Position(0, 1)), move);
		assertFalse(move.equals(null));
		assertFalse(move.equals(new Object()));
		assertFalse(move
				.equals(new Move(new Position(0, 0), new Position(1, 0))));
	}
}
