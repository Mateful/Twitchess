package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;

public class Knight extends Figure {
	public Knight(Position position) {
		super(position);
		directions.add(new OneStepDirection(DirectionType.KNIGHT));
	}
}
