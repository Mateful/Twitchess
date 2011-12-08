package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public final class NoFigure extends Figure {
	public static final NoFigure NO_FIGURE = new NoFigure();

	private NoFigure() {
		super(null);
	}

	private NoFigure(final Color color, final Position position) {
		super(null);
	}

	@Override
	public String toString() {
		return "NoFigure [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
