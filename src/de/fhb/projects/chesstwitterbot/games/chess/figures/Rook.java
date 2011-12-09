package de.fhb.projects.chesstwitterbot.games.chess.figures;

import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.InfiniteDirection;

public final class Rook extends Figure {
	public Rook(final Position position) {
		super(position);
		directions.add(new InfiniteDirection(DirectionType.UP));
		directions.add(new InfiniteDirection(DirectionType.DOWN));
		directions.add(new InfiniteDirection(DirectionType.LEFT));
		directions.add(new InfiniteDirection(DirectionType.RIGHT));
	}

	@Override
	public String toString() {
		return "Rook [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
