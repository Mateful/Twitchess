package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.RelativeMoveList;

public abstract class Figure {
	protected RelativeMoveList naiveMoves;
	public Color color;

	public Figure(Color color) {
		this.color = color;
		naiveMoves = new RelativeMoveList();
	}

	public final RelativeMoveList getNaiveMoves() {
		return naiveMoves;
	}
}
