package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;

public final class King extends Figure {
	public King(final Position position) {
		super(position);
		directions.add(new OneStepDirection(DirectionType.UP));
		directions.add(new OneStepDirection(DirectionType.DOWN));
		directions.add(new OneStepDirection(DirectionType.LEFT));
		directions.add(new OneStepDirection(DirectionType.RIGHT));
		directions.add(new OneStepDirection(DirectionType.UPRIGHT));
		directions.add(new OneStepDirection(DirectionType.UPLEFT));
		directions.add(new OneStepDirection(DirectionType.DOWNRIGHT));
		directions.add(new OneStepDirection(DirectionType.DOWNLEFT));
	}
}
