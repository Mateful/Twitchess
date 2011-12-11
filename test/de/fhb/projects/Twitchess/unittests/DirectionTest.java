package de.fhb.projects.Twitchess.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;

public class DirectionTest {
	@Test
	public void equals() {
		Direction oneUp = new OneStepDirection(DirectionType.UP);
		assertEquals(oneUp, oneUp);
		assertEquals(new OneStepDirection(DirectionType.UP), oneUp);
		assertFalse(oneUp.equals(null));
		assertFalse(oneUp.equals(new Object()));
		assertFalse(oneUp.equals(new OneStepDirection(DirectionType.DOWN)));
		
		Direction infiniteUp = new InfiniteDirection(DirectionType.UP);
		assertEquals(infiniteUp, infiniteUp);
		assertEquals(new InfiniteDirection(DirectionType.UP), infiniteUp);
		assertFalse(infiniteUp.equals(null));
		assertFalse(infiniteUp.equals(new Object()));
		assertFalse(oneUp.equals(new InfiniteDirection(DirectionType.DOWN)));
		
		assertFalse(oneUp.equals(infiniteUp));
	}
}
