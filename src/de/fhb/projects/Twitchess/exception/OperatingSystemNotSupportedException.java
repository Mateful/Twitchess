package de.fhb.projects.Twitchess.exception;

public final class OperatingSystemNotSupportedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public OperatingSystemNotSupportedException(final String message) {
		super(message);
	}

}
