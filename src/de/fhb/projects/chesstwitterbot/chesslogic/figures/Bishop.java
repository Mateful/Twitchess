package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;

public class Bishop extends Figure {
	public Bishop(Position position) {
		super(position);
		naiveMoves.add(new RelativeMove(Direction.UPRIGHT, true));
		naiveMoves.add(new RelativeMove(Direction.DOWNRIGHT, true));
		naiveMoves.add(new RelativeMove(Direction.UPLEFT, true));
		naiveMoves.add(new RelativeMove(Direction.DOWNLEFT, true));
	}
}
