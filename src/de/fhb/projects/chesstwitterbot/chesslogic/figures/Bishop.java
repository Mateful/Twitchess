package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;

public final class Bishop extends Figure {
	public Bishop(final Position position) {
		super(position);
		directions.add(new InfiniteDirection(DirectionType.UPRIGHT));
		directions.add(new InfiniteDirection(DirectionType.UPLEFT));
		directions.add(new InfiniteDirection(DirectionType.DOWNRIGHT));
		directions.add(new InfiniteDirection(DirectionType.DOWNLEFT));
	}
}
