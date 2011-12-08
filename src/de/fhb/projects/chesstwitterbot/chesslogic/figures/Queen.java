package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;

public final class Queen extends Figure {
	public Queen(final Position position) {
		super(position);
		directions.add(new InfiniteDirection(DirectionType.UP));
		directions.add(new InfiniteDirection(DirectionType.DOWN));
		directions.add(new InfiniteDirection(DirectionType.LEFT));
		directions.add(new InfiniteDirection(DirectionType.RIGHT));
		directions.add(new InfiniteDirection(DirectionType.UPRIGHT));
		directions.add(new InfiniteDirection(DirectionType.UPLEFT));
		directions.add(new InfiniteDirection(DirectionType.DOWNRIGHT));
		directions.add(new InfiniteDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "Queen [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
