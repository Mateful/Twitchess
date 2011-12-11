package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_HEIGHT;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_WIDTH;
import de.fhb.projects.Twitchess.exception.InvalidMoveException;

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
		return pos.getX() >= CHESSBOARD_WIDTH
				|| pos.getY() >= CHESSBOARD_HEIGHT || pos.getX() < 0
				|| pos.getY() < 0;
	}
}
