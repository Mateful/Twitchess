package de.fhb.projects.chesstwitterbot.chesslogic.player;

public final class White extends Player {
	public White() {
		super();
		color = Color.WHITE;
	}

	@Override
	public String toString() {
		return "White [color=" + color + ", opponent=" + opponent
				+ ", inCheck=" + inCheck + ", figuresInGame=" + figuresInGame
				+ "]";
	}
}
