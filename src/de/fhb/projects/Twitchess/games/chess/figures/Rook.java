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
		setDirections();
	}

	@Override
	protected void setDirections() {
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
