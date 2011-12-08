package de.fhb.projects.chesstwitterbot.chesslogic.move;

public final class InfiniteDirection extends Direction {

	public InfiniteDirection(final DirectionType type) {
		super(type);
	}

	@Override
	public String toString() {
		return "InfiniteDirection [type=" + type + "]";
	}

}
