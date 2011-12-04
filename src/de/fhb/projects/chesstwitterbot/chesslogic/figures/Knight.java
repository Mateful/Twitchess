package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;

public class Knight extends Figure {
	public Knight(Position position) {
		super(position);
		naiveMoves.add(new RelativeMove(Direction.KNIGHT, false));
	}
}
