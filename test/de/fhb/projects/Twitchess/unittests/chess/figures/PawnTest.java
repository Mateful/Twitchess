package de.fhb.projects.Twitchess.unittests.chess.figures;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public class PawnTest {
	@Test
	public void getMoves() {
		List<Direction> whiteDirections = new Pawn(new Position(0, 0),
				Color.WHITE).getDirections();
		List<Direction> blackDirections = new Pawn(new Position(0, 0),
				Color.BLACK).getDirections();
		Direction up = new OneStepDirection(DirectionType.UP);
		Direction down = new OneStepDirection(DirectionType.DOWN);
		assertTrue(whiteDirections.contains(up));
		assertTrue(blackDirections.contains(down));
		assertFalse(whiteDirections.contains(down));
		assertFalse(blackDirections.contains(up));
	}
}
