package de.fhb.projects.Twitchess.games.chess.figures;

import static de.fhb.projects.Twitchess.games.chess.player.Color.NOCOLOR;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public final class King extends Figure {
	public King(final Position position) {
		this(position, NOCOLOR);
	}

	public King(final Position position, final Color color) {
		super(position, color);
		setMoveDirections();
		setHitDirections();
	}

	@Override
	protected void setMoveDirections() {
		moveDirections.add(new OneStepDirection(DirectionType.UP));
		moveDirections.add(new OneStepDirection(DirectionType.DOWN));
		moveDirections.add(new OneStepDirection(DirectionType.LEFT));
		moveDirections.add(new OneStepDirection(DirectionType.RIGHT));
		moveDirections.add(new OneStepDirection(DirectionType.UPRIGHT));
		moveDirections.add(new OneStepDirection(DirectionType.UPLEFT));
		moveDirections.add(new OneStepDirection(DirectionType.DOWNRIGHT));
		moveDirections.add(new OneStepDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "King [directions=" + moveDirections + ", color=" + color
				+ ", position=" + position + "]";
	}

	@Override
	public Object clone() {
		King o = new King((Position) position.clone(), color);

		return (Object) o;
	}
}
