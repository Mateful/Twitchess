package de.fhb.projects.chesstwitterbot.games.chess.figures;

import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.OneStepDirection;

public final class Knight extends Figure {
	public Knight(final Position position) {
		super(position);
		directions.add(new OneStepDirection(DirectionType.KNIGHT));
	}

	@Override
	public String toString() {
		return "Knight [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
