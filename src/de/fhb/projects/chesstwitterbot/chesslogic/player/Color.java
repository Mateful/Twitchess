package de.fhb.projects.chesstwitterbot.chesslogic.player;

public enum Color {
	WHITE, BLACK, NOCOLOR;
	
	public Color getInverse() {
		return this == WHITE ? BLACK : WHITE;
	}	
}
