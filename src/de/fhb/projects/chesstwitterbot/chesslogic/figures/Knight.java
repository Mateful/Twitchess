package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;

public class Knight extends Figure {
	public Knight(Color color) {
		super(color);
		naiveMoves.add(new RelativeMove(Direction.KNIGHT, false));
	}
}
