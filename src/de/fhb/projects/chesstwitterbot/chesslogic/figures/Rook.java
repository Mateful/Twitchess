package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;

public class Rook extends Figure {
	public Rook(Position position) {
		super(position);
		directions.add(new InfiniteDirection(DirectionType.UP));
		directions.add(new InfiniteDirection(DirectionType.DOWN));
		directions.add(new InfiniteDirection(DirectionType.LEFT));
		directions.add(new InfiniteDirection(DirectionType.RIGHT));
	}
}
