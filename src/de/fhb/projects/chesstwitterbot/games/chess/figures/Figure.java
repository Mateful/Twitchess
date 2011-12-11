package de.fhb.projects.chesstwitterbot.games.chess.figures;

import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.games.chess.player.Color.WHITE;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.Direction;
import de.fhb.projects.chesstwitterbot.games.chess.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.games.chess.move.Move;
import de.fhb.projects.chesstwitterbot.games.chess.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.games.chess.player.Color;

public abstract class Figure {
	protected List<Direction> directions;
	protected Color color;
	protected Position position;

	public Figure(final Position position) {
		this(position, Color.NOCOLOR);
	}

	public Figure(final Position position, final Color color) {
		this.position = position;
		directions = new ArrayList<Direction>();
		this.color = color;
	}

	public final List<Direction> getDirections() {
		return directions;
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

	protected abstract void setDirections();

	public final boolean canDoMove(final Move move) {
		for (Direction d : directions) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (color.hashCode());
		result = prime * result + (directions.hashCode());
		result = prime * result + (position.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		Figure other = (Figure) obj;
		return color == other.color && directions.equals(other.directions)
				&& position.equals(other.position);
	}
}
