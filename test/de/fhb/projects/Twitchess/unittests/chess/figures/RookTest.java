package de.fhb.projects.Twitchess.unittests.chess.figures;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;

public class RookTest {
	@Test
	public void getMoves() {
		List<Direction> directions = new Rook(new Position(0, 0))
				.getDirections();
		Direction up = new InfiniteDirection(DirectionType.UP);
		Direction down = new InfiniteDirection(DirectionType.DOWN);
		Direction left = new InfiniteDirection(DirectionType.LEFT);
		Direction right = new InfiniteDirection(DirectionType.RIGHT);
		assertTrue(directions.contains(left));
		assertTrue(directions.contains(right));
		assertTrue(directions.contains(up));
		assertTrue(directions.contains(down));
	}
}
