package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public class NoFigure extends Figure {
	public static final NoFigure NO_FIGURE = new NoFigure();
	
	private NoFigure() {
		super(null);
	}
	
	private NoFigure(Color color, Position position) {
		super(null);
	}
}
