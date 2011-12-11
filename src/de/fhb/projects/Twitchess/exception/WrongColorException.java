package de.fhb.projects.Twitchess.exception;

public final class WrongColorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WrongColorException(final String message) {
		super(message);
	}
}
