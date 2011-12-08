package de.fhb.projects.chesstwitterbot.chesslogic.move;

public final class OneStepDirection extends Direction {

	public OneStepDirection(DirectionType type) {
		super(type);
	}

	@Override
	public String toString() {
		return "OneStepDirection [type=" + type + "]";
	}

}
