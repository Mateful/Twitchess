package de.fhb.projects.Twitchess.games.chess.figures;

import static de.fhb.projects.Twitchess.games.chess.player.Color.NOCOLOR;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public final class Knight extends Figure {
	public Knight(final Position position) {
		this(position, NOCOLOR);
	}

	public Knight(final Position position, final Color color) {
		super(position, color);
		setMoveDirections();
		setHitDirections();
	}

	@Override
	protected void setMoveDirections() {
		moveDirections.add(new OneStepDirection(DirectionType.KNIGHT));
	}

	@Override
	public String toString() {
		return "Knight [directions=" + moveDirections + ", color=" + color
				+ ", position=" + position + "]";
	}

	@Override
	public Object clone() {
		Knight o = new Knight((Position) position.clone(), color);

		return (Object) o;
	}
}
