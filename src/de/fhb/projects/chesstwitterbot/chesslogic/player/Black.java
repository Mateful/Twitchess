package de.fhb.projects.chesstwitterbot.chesslogic.player;

public final class Black extends Player {
	public Black() {
		super();
		color = Color.BLACK;
	}

	@Override
	public String toString() {
		return "Black [color=" + color + ", opponent=" + opponent
				+ ", inCheck=" + inCheck + ", figuresInGame=" + figuresInGame
				+ "]";
	}
}
