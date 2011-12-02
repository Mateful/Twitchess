package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMove;

public class Pawn extends Figure {
	public Pawn(Color color) {
		super(color);
		switch(color) {
		case WHITE:
			naiveMoves.add(new RelativeMove(Direction.UP, false));
			break;
		case BLACK:
			naiveMoves.add(new RelativeMove(Direction.DOWN, false));
			break;
		}
	}
}
