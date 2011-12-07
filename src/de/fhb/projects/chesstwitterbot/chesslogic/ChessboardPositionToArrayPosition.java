package de.fhb.projects.chesstwitterbot.chesslogic;

import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public final class ChessboardPositionToArrayPosition {
	private ChessboardPositionToArrayPosition() {
	}

	public static String parseArrayPostion(final Position pos) {
		if (isOutOfChessboard(pos)) {
			throw new InvalidMoveException(
					"Your move was invalid because it was out of the chessboard!");
		}
		return (char) (pos.getX() + 'a') + Integer.toString(pos.y + 1);
	}

	public static Position parseChessboardPosition(final String pos) {
		Position position = new Position(pos.charAt(0) - 'a',
				Integer.parseInt(pos.charAt(1) + "") - 1);
		if (isOutOfChessboard(position)) {
			throw new InvalidMoveException(
					"Your move was invalid because it was out of the chessboard!");
		}
		return position;
	}

	private static boolean isOutOfChessboard(final Position pos) {
		return pos.getX() >= 8 || pos.getY() >= 8 || pos.getX() < 0
				|| pos.getY() < 0;
	}
}
