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

	public Figure(Position position) {
		this(position, Color.NOCOLOR);
	}

	public Figure(Position position, Color color) {
		this.position = position;
		directions = new ArrayList<Direction>();
		this.color = color;
	}

	public List<Direction> getDirections() {
		return directions;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public boolean canDoMove(Move move) {
		for (Direction d : directions) {
			if (d instanceof InfiniteDirection
					&& d.getType().equals(move.getDirectionType()))
				return true;
			if (d instanceof OneStepDirection
					&& move.getDirection() instanceof OneStepDirection
					&& d.getType().equals(move.getDirectionType()))
				return true;
		}
		return false;
	}
}
