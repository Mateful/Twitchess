package de.fhb.projects.Twitchess.games.chess.figures;

public final class NoFigure extends Figure {
	public static final NoFigure NO_FIGURE = new NoFigure();

	private NoFigure() {
		super(null);
	}

	@Override
	protected void setMoveDirections() {
	}

	@Override
	protected void setHitDirections() {
	}

	@Override
	public String toString() {
		return "NoFigure [directions=" + moveDirections + ", color=" + color
				+ ", position=" + position + "]";
	}

	@Override
	public Object clone() {
		return NO_FIGURE;
	}
}
