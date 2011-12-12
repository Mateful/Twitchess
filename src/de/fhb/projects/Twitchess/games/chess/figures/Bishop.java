package de.fhb.projects.Twitchess.games.chess.figures;

import static de.fhb.projects.Twitchess.games.chess.player.Color.NOCOLOR;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public final class Bishop extends Figure {
	public Bishop(final Position position) {
		this(position, NOCOLOR);
	}

	public Bishop(final Position position, final Color color) {
		super(position, color);
		setMoveDirections();
		setHitDirections();
	}

	@Override
	protected void setMoveDirections() {
		moveDirections.add(new InfiniteDirection(DirectionType.UPRIGHT));
		moveDirections.add(new InfiniteDirection(DirectionType.UPLEFT));
		moveDirections.add(new InfiniteDirection(DirectionType.DOWNRIGHT));
		moveDirections.add(new InfiniteDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "Bishop [directions=" + moveDirections + ", color=" + color
				+ ", position=" + position + "]";
	}
}
