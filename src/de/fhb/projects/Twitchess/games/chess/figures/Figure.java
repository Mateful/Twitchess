package de.fhb.projects.Twitchess.games.chess.figures;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.move.Direction;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.move.OneStepDirection;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public abstract class Figure implements Cloneable {
	protected List<Direction> moveDirections;
	protected List<Direction> hitDirections;
	protected Color color;
	protected Position position;

	public Figure(final Position position) {
		this(position, Color.NOCOLOR);
	}

	public Figure(final Position position, final Color color) {
		this.position = position;
		moveDirections = new ArrayList<Direction>();
		hitDirections = new ArrayList<Direction>();
		this.color = color;
	}

	public final List<Direction> getDirections() {
		return moveDirections;
	}

	public final Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		if (color == WHITE || color == BLACK) {
			this.color = color;
		} else {
			throw new RuntimeException(
					"Figures colors can only be set to Color.BLACK or Color.WHITE.");
		}
	}

	public final Position getPosition() {
		return position;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	protected abstract void setMoveDirections();

	protected void setHitDirections() {
		hitDirections = moveDirections;
	}

	public final boolean canDoMove(final Move move) {
		return contains(moveDirections, move);
	}

	public boolean canDoHit(final Move move) {
		return contains(hitDirections, move);
	}

	private boolean contains(List<Direction> list, Move move) {
		for (Direction d : list) {
			if (d instanceof InfiniteDirection
					&& d.getType().equals(move.getDirectionType())) {
				return true;
			}
			if (d instanceof OneStepDirection
					&& move.getDirection() instanceof OneStepDirection
					&& d.getType().equals(move.getDirectionType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (color.hashCode());
		result = prime * result + (moveDirections.hashCode());
		result = prime * result + (position.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		Figure other = (Figure) obj;
		return color == other.color
				&& moveDirections.equals(other.moveDirections)
				&& position.equals(other.position);
	}

	@Override
	public abstract Object clone();
}
