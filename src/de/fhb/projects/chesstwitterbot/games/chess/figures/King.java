package de.fhb.projects.chesstwitterbot.games.chess.figures;

import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.NOCOLOR;
import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.games.chess.player.Color;

public final class King extends Figure {
	public King(final Position position) {
		this(position, NOCOLOR);
	}

	public King(final Position position, final Color color) {
		super(position, color);
		setDirections();
	}

	@Override
	protected void setDirections() {
		directions.add(new OneStepDirection(DirectionType.UP));
		directions.add(new OneStepDirection(DirectionType.DOWN));
		directions.add(new OneStepDirection(DirectionType.LEFT));
		directions.add(new OneStepDirection(DirectionType.RIGHT));
		directions.add(new OneStepDirection(DirectionType.UPRIGHT));
		directions.add(new OneStepDirection(DirectionType.UPLEFT));
		directions.add(new OneStepDirection(DirectionType.DOWNRIGHT));
		directions.add(new OneStepDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "King [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
