package de.fhb.projects.chesstwitterbot.chesslogic;

public class RelativeMove {
	private Direction direction;
	private boolean infinite;

	public RelativeMove(Direction direction, boolean infinite) {
		this.direction = direction;
		this.infinite = infinite;
	}

	@Override
	public boolean equals(Object o) {
		RelativeMove nm = (RelativeMove)o;
		return this.direction.equals(nm.direction) && this.infinite == nm.infinite;
	}

	@Override
	public String toString() {
		return "Relative Move=" + direction + "," + infinite;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public boolean isInfinite() {
		return infinite;
	}
}
