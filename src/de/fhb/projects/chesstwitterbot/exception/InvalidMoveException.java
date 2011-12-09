package de.fhb.projects.chesstwitterbot.exception;

public final class InvalidMoveException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidMoveException(final String message) {
		super(message);
	}
}
