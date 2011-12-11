package de.fhb.projects.chesstwitterbot.games.chess.figures;

public final class NoFigure extends Figure {
	public static final NoFigure NO_FIGURE = new NoFigure();

	private NoFigure() {
		super(null);
	}

	@Override
	protected void setDirections() {
	}

	@Override
	public String toString() {
		return "NoFigure [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}
