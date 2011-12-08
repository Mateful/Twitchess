package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.move.OneStepDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

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

	public final void setColor(final Color color) {
		this.color = color;
	}

	public final Position getPosition() {
		return position;
	}

	public final void setPosition(final Position position) {
		this.position = position;
	}

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
	public String toString() {
		return "Figure [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
