package de.fhb.projects.chesstwitterbot.exception;

public final class FigureCannotMoveIntoDirectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FigureCannotMoveIntoDirectionException(final String message) {
		super(message);
	}
}
