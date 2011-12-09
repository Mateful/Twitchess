package de.fhb.projects.chesstwitterbot.games.chess.player;

public enum Color {
	WHITE, BLACK, NOCOLOR;

	public Color getInverse() {
		if (this == NOCOLOR) {
			return NOCOLOR;
		} else {
			return this == WHITE ? BLACK : WHITE;
		}
	}
}
