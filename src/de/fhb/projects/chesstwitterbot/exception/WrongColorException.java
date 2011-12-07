package de.fhb.projects.chesstwitterbot.exception;

public class WrongColorException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public WrongColorException(String message) {
		super(message);
	}
}
