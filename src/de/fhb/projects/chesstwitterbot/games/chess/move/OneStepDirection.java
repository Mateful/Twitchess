package de.fhb.projects.chesstwitterbot.games.chess.move;

public final class OneStepDirection extends Direction {

	public OneStepDirection(final DirectionType type) {
		super(type);
	}

	@Override
	public String toString() {
		return "OneStepDirection [type=" + type + "]";
	}

}
