package de.fhb.projects.chesstwitterbot.games.chess.figures;

import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.InfiniteDirection;

public final class Bishop extends Figure {
	public Bishop(final Position position) {
		super(position);
		directions.add(new InfiniteDirection(DirectionType.UPRIGHT));
		directions.add(new InfiniteDirection(DirectionType.UPLEFT));
		directions.add(new InfiniteDirection(DirectionType.DOWNRIGHT));
		directions.add(new InfiniteDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "Bishop [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
