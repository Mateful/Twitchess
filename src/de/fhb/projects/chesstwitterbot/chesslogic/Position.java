package de.fhb.projects.chesstwitterbot.chesslogic;

public class Position {
	private int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return "" + (char) (97 + x) + (y + 1);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o == null || o.getClass() != this.getClass()) {
			return false;
		}

		Position p = (Position) o;
		return x == p.x && y == p.y;
	}

	@Override
	public int hashCode() {
		long bits = x;
		bits ^= y * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	/* STATIC ANTICS */
	private static Position[][] positions;

	public static boolean xAndYAreInInterval(int x, int y, int min, int max) {
		return x >= min && x <= max && y >= min && y <= max;
	}

	static {
		positions = new Position[8][8];

		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				positions[x][y] = new Position(x, y);
	}

	public static Position getPosition(int x, int y) {
		if (xAndYAreInInterval(x, y, 0, 7))
			return positions[x][y];

		return null;
	}
}
