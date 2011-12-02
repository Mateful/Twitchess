package de.fhb.projects.chesstwitterbot.chesslogic;

public enum Color {
	WHITE, BLACK;
	
	public Color getInverse() {
		return this == WHITE ? BLACK : WHITE;
	}
	
	
}
