package de.fhb.projects.chesstwitterbot.exception;

public class NoFigureException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NoFigureException(String message) {
		super(message);
	}
}
