package de.fhb.projects.Twitchess.exception;

public class ChessManagerGameAlreadyOverException extends ChessManagerException {
	private static final long serialVersionUID = 1L;

	public ChessManagerGameAlreadyOverException(final String message) {
		super(message);
	}

}
