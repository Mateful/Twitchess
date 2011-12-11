package de.fhb.projects.chesstwitterbot.games.chess.figures;

import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.NOCOLOR;
import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.games.chess.player.Color;

public final class Bishop extends Figure {
	public Bishop(final Position position) {
		this(position, NOCOLOR);
	}

	public Bishop(final Position position, final Color color) {
		super(position, color);
		setDirections();
	}

	@Override
	protected void setDirections() {
		directions.add(new InfiniteDirection(DirectionType.UPRIGHT));
		directions.add(new InfiniteDirection(DirectionType.UPLEFT));
		directions.add(new InfiniteDirection(DirectionType.DOWNRIGHT));
		directions.add(new InfiniteDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "Bishop [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
