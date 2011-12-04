package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public class Pawn extends Figure {
	protected RelativeMoveList hitMoves;

	public Pawn(Position position, Color color) {
		super(position, color);
		hitMoves = new RelativeMoveList();
		switch(color) {
		case WHITE:
			naiveMoves.add(new RelativeMove(Direction.UP, false));
			hitMoves.add(new RelativeMove(Direction.UPLEFT, false));
			hitMoves.add(new RelativeMove(Direction.UPRIGHT, false));
			break;
		case BLACK:
			naiveMoves.add(new RelativeMove(Direction.DOWN, false));
			hitMoves.add(new RelativeMove(Direction.DOWNLEFT, false));
			hitMoves.add(new RelativeMove(Direction.DOWNRIGHT, false));
			break;
		default:
			throw new RuntimeException(
					"Pawn can only be initialized with Color.BLACK or Color.WHITE.");
		}
	}

	public RelativeMoveList getHitMoves() {
		return hitMoves;
	}
}
