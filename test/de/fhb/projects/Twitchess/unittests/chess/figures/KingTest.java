package de.fhb.projects.Twitchess.unittests.chess.figures;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;

public class KingTest {
	@Test
	public void getMoves() {
		List<Direction> directions = new King(new Position(0, 0))
				.getDirections();
		Direction up = new OneStepDirection(DirectionType.UP);
		Direction down = new OneStepDirection(DirectionType.DOWN);
		Direction left = new OneStepDirection(DirectionType.LEFT);
		Direction right = new OneStepDirection(DirectionType.RIGHT);
		Direction upright = new OneStepDirection(DirectionType.UPRIGHT);
		Direction downright = new OneStepDirection(DirectionType.DOWNRIGHT);
		Direction upleft = new OneStepDirection(DirectionType.UPLEFT);
		Direction downleft = new OneStepDirection(DirectionType.DOWNLEFT);
		assertTrue(directions.contains(left));
		assertTrue(directions.contains(right));
		assertTrue(directions.contains(up));
		assertTrue(directions.contains(down));
		assertTrue(directions.contains(upright));
		assertTrue(directions.contains(downright));
		assertTrue(directions.contains(upleft));
		assertTrue(directions.contains(downleft));
	}
}
