package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMoveList;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public abstract class Figure {
	protected RelativeMoveList naiveMoves;
	public Color color;
	public Position position;

	public Figure(Position position) {
		this(position, Color.NOCOLOR);
	}
	
	public Figure(Position position, Color color) {
		this.position = position;
		naiveMoves = new RelativeMoveList();
		this.color = color;
	}

	public final RelativeMoveList getNaiveMoves() {
		return naiveMoves;
	}
}
