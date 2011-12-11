package de.fhb.projects.Twitchess.games.chess.player;

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
