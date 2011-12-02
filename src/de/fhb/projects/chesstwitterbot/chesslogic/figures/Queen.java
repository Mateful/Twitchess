package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;

public class Queen extends Figure {
	public Queen(Color color) {
		super(color);
		naiveMoves.add(new RelativeMove(Direction.UPRIGHT, true));
		naiveMoves.add(new RelativeMove(Direction.DOWNRIGHT, true));
		naiveMoves.add(new RelativeMove(Direction.UPLEFT, true));
		naiveMoves.add(new RelativeMove(Direction.DOWNLEFT, true));
		naiveMoves.add(new RelativeMove(Direction.UP, true));
		naiveMoves.add(new RelativeMove(Direction.DOWN, true));
		naiveMoves.add(new RelativeMove(Direction.LEFT, true));
		naiveMoves.add(new RelativeMove(Direction.RIGHT, true));
	}
}
