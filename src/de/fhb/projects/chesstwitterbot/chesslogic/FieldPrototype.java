package de.fhb.projects.chesstwitterbot.chesslogic;

import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public enum FieldPrototype {
	EMPTY(null, null), 
	KING_WHITE(Type.KING, Color.WHITE), KING_BLACK(Type.KING, Color.BLACK),
	QUEEN_WHITE(Type.QUEEN, Color.WHITE), QUEEN_BLACK(Type.QUEEN, Color.BLACK),
	ROOK_WHITE(Type.ROOK, Color.WHITE), ROOK_BLACK(Type.ROOK, Color.BLACK),
	BISHOP_WHITE(Type.BISHOP, Color.WHITE), BISHOP_BLACK(Type.BISHOP, Color.BLACK),
	KNIGHT_WHITE(Type.KNIGHT, Color.WHITE), KNIGHT_BLACK(Type.KNIGHT, Color.BLACK),
	PAWN_WHITE(Type.PAWN, Color.WHITE), PAWN_BLACK(Type.PAWN, Color.BLACK);
	
	private FieldPrototype(Type t, Color c) {
		type = t;
		color = c;
	}
	
	public enum Type {
		KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN;
	}

	private Type type;
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
