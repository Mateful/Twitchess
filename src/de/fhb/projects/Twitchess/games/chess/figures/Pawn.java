package de.fhb.projects.Twitchess.games.chess.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public final class Pawn extends Figure {
	public Pawn(final Position position) {
		super(position);
	}

	public Pawn(final Position position, final Color color) {
		super(position, color);
		setColor(color);
	}

	@Override
	protected void setMoveDirections() {
		moveDirections = new ArrayList<Direction>();
		switch (color) {
			case WHITE :
				moveDirections.add(new OneStepDirection(DirectionType.UP));
				break;
			case BLACK :
				moveDirections.add(new OneStepDirection(DirectionType.DOWN));
				break;
			default :
				break;
		}
	}

	@Override
	protected void setHitDirections() {
		hitDirections = new ArrayList<Direction>();
		switch (color) {
			case WHITE :
				hitDirections.add(new OneStepDirection(DirectionType.UPLEFT));
				hitDirections.add(new OneStepDirection(DirectionType.UPRIGHT));
				break;
			case BLACK :
				hitDirections.add(new OneStepDirection(DirectionType.DOWNLEFT));
				hitDirections
						.add(new OneStepDirection(DirectionType.DOWNRIGHT));
				break;
			default :
				break;
		}
	}

	public List<Direction> getHitMoves() {
		return hitDirections;
	}

	@Override
	public void setColor(final Color color) {
		super.setColor(color);
		setMoveDirections();
		setHitDirections();
	}

	@Override
	public String toString() {
		return "Pawn [hitMoves=" + hitDirections + ", directions="
				+ moveDirections + ", color=" + color + ", position="
				+ position + "]";
	}

	@Override
	public Object clone() {
		Pawn o = new Pawn((Position) position.clone(), color);
		
		return (Object) o;
	}
}
