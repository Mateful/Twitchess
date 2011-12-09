package de.fhb.projects.chesstwitterbot.exception;

public final class NoFigureException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoFigureException(final String message) {
		super(message);
	}
}
