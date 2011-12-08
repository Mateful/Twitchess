package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;

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
