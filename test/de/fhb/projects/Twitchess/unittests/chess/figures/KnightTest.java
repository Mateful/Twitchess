package de.fhb.projects.Twitchess.unittests.chess.figures;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;

public class KnightTest {
	@Test
	public void getMoves() {
		List<Direction> directions = new Knight(new Position(0, 0))
				.getDirections();
		Direction knight = new OneStepDirection(DirectionType.KNIGHT);
		assertTrue(directions.contains(knight));
	}
}
