package de.fhb.projects.chesstwitterbot.games.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.Direction;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.Move;
import de.fhb.projects.chesstwitterbot.games.chess.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.games.chess.player.Color;

public final class Pawn extends Figure {
	protected List<Direction> hitMoves;

	public Pawn(final Position position, final Color color) {
		super(position, color);
		hitMoves = new ArrayList<Direction>();
		switch (color) {
		case WHITE:
			directions.add(new OneStepDirection(DirectionType.UP));
			hitMoves.add(new OneStepDirection(DirectionType.UPLEFT));
			hitMoves.add(new OneStepDirection(DirectionType.UPRIGHT));
			break;
		case BLACK:
			directions.add(new OneStepDirection(DirectionType.DOWN));
			hitMoves.add(new OneStepDirection(DirectionType.DOWNLEFT));
			hitMoves.add(new OneStepDirection(DirectionType.DOWNRIGHT));
			break;
		default:
			throw new RuntimeException("Pawn can only be initialized with Color.BLACK or Color.WHITE.");
		}
	}

	public List<Direction> getHitMoves() {
		return hitMoves;
	}

	public boolean canDoHit(final Move move) {
		for (Direction d : hitMoves) {
			if (d instanceof OneStepDirection && move.getDirection() instanceof OneStepDirection
					&& d.getType().equals(move.getDirectionType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Pawn [hitMoves=" + hitMoves + ", directions=" + directions + ", color=" + color + ", position=" + position + "]";
	}
}
