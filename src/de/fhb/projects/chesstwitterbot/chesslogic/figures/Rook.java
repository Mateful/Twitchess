package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;

public class Rook extends Figure {
	public Rook(Position position) {
		super(position);
		naiveMoves.add(new RelativeMove(Direction.UP, true));
		naiveMoves.add(new RelativeMove(Direction.DOWN, true));
		naiveMoves.add(new RelativeMove(Direction.LEFT, true));
		naiveMoves.add(new RelativeMove(Direction.RIGHT, true));
	}
}
