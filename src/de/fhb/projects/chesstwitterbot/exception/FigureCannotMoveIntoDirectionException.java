package de.fhb.projects.chesstwitterbot.exception;

public class FigureCannotMoveIntoDirectionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FigureCannotMoveIntoDirectionException(String message) {
		super(message);
	}
}
