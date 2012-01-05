package de.fhb.projects.Twitchess.games.chess.figures;

import static de.fhb.projects.Twitchess.games.chess.player.Color.NOCOLOR;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public final class Rook extends Figure {
	public Rook(final Position position) {
		this(position, NOCOLOR);
	}

	public Rook(final Position position, final Color color) {
		super(position, color);
		setMoveDirections();
		setHitDirections();
	}

	@Override
	protected void setMoveDirections() {
		moveDirections.add(new InfiniteDirection(DirectionType.UP));
		moveDirections.add(new InfiniteDirection(DirectionType.DOWN));
		moveDirections.add(new InfiniteDirection(DirectionType.LEFT));
		moveDirections.add(new InfiniteDirection(DirectionType.RIGHT));
	}

	@Override
	public String toString() {
		return "Rook [directions=" + moveDirections + ", color=" + color
				+ ", position=" + position + "]";
	}

	@Override
	public Object clone() {
		Rook o = new Rook((Position) position.clone(), color);

		return (Object) o;
	}
}
