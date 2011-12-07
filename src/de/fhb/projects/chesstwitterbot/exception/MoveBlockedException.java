package de.fhb.projects.chesstwitterbot.exception;

public class MoveBlockedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MoveBlockedException(String message) {
		super(message);
	}
}
