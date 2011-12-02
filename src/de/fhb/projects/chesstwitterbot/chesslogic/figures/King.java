package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;

public class King extends Figure {
	public King(Color color) {
		super(color);
		naiveMoves.add(new RelativeMove(Direction.UPRIGHT, false));
		naiveMoves.add(new RelativeMove(Direction.DOWNRIGHT, false));
		naiveMoves.add(new RelativeMove(Direction.UPLEFT, false));
		naiveMoves.add(new RelativeMove(Direction.DOWNLEFT, false));
		naiveMoves.add(new RelativeMove(Direction.UP, false));
		naiveMoves.add(new RelativeMove(Direction.DOWN, false));
		naiveMoves.add(new RelativeMove(Direction.LEFT, false));
		naiveMoves.add(new RelativeMove(Direction.RIGHT, false));
	}
}
