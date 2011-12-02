package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessBoard;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public abstract class Figure {
	protected Color color;
	protected Position position;
	
	public Figure(Position position, Color color) {
		setPosition(position);
		setColor(color);
	}

	public Color getColor() {
		return color;
	}
	
	private void setColor(Color color) {
		this.color = color;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	

	public void moveTo(Position destination) {
		setPosition(destination);
	}
	
	public String toString() {
		return color.toString().toLowerCase() + " " + this.getClass().getSimpleName() + " @ " + position;
	}
	
	
	
	public abstract List<Move> generateMoves(ChessBoard board);
	
	@Override
	public abstract Object clone();
	 
}
