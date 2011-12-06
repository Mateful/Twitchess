package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.DirectionType;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public class Pawn extends Figure {
	protected List<Direction> hitMoves;

	public Pawn(Position position, Color color) {
		super(position, color);
		hitMoves = new ArrayList<Direction>();
		switch (color) {
			case WHITE :
				directions.add(new OneStepDirection(DirectionType.UP));
				hitMoves.add(new OneStepDirection(DirectionType.UPLEFT));
				hitMoves.add(new OneStepDirection(DirectionType.UPRIGHT));
				break;
			case BLACK :
				directions.add(new OneStepDirection(DirectionType.DOWN));
				hitMoves.add(new OneStepDirection(DirectionType.DOWNLEFT));
				hitMoves.add(new OneStepDirection(DirectionType.DOWNRIGHT));
				break;
			default :
				throw new RuntimeException(
						"Pawn can only be initialized with Color.BLACK or Color.WHITE.");
		}
	}

	public List<Direction> getHitMoves() {
		return hitMoves;
	}

	public boolean canDoHit(Move move) {
		for (Direction d : hitMoves) {
			if (d instanceof OneStepDirection
					&& move.getDirection() instanceof OneStepDirection
					&& d.getType().equals(move.getDirectionType()))
				return true;
		}
		return false;
	}
}
