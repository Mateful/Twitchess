package de.fhb.projects.Twitchess.exception;

public final class MoveBlockedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MoveBlockedException(final String message) {
		super(message);
	}
}
