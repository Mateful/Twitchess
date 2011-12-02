package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;

public class Bishop extends Figure {
	public Bishop(Color color) {
		super(color);
		naiveMoves.add(new RelativeMove(Direction.UPRIGHT, true));
		naiveMoves.add(new RelativeMove(Direction.DOWNRIGHT, true));
		naiveMoves.add(new RelativeMove(Direction.UPLEFT, true));
		naiveMoves.add(new RelativeMove(Direction.DOWNLEFT, true));
	}
}
