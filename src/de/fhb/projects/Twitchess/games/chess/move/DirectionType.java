package de.fhb.projects.Twitchess.games.chess.move;

import de.fhb.projects.Twitchess.games.chess.Position;

public enum DirectionType {
	UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT, KNIGHT, NODIRECTION;

	public static DirectionType getDirectionType(final Position start,
			final Position destination) {
		DirectionType direction;
		if (DirectionType.isKnight(start, destination)) {
			direction = DirectionType.KNIGHT;
		} else if (DirectionType.isUpRight(start, destination)) {
			direction = DirectionType.UPRIGHT;
		} else if (DirectionType.isDownRight(start, destination)) {
			direction = DirectionType.DOWNRIGHT;
		} else if (DirectionType.isUpLeft(start, destination)) {
			direction = DirectionType.UPLEFT;
		} else if (DirectionType.isDownLeft(start, destination)) {
			direction = DirectionType.DOWNLEFT;
		} else if (DirectionType.isRight(start, destination)) {
			direction = DirectionType.RIGHT;
		} else if (DirectionType.isLeft(start, destination)) {
			direction = DirectionType.LEFT;
		} else if (DirectionType.isUp(start, destination)) {
			direction = DirectionType.UP;
		} else if (DirectionType.isDown(start, destination)) {
			direction = DirectionType.DOWN;
		} else {
			throw new RuntimeException("No such move!");
		}
		return direction;
	}

	public static boolean isHorizontal(final Position start,
			final Position destination) {
		return !isUpSide(start, destination) && !isDownSide(start, destination);
	}

	public static boolean isVertical(final Position start,
			final Position destination) {
		return !isLeftSide(start, destination)
				&& !isRightSide(start, destination);
	}

	public static boolean isUp(final Position start, final Position destination) {
		return isUpSide(start, destination) && isVertical(start, destination);
	}

	public static boolean isDown(final Position start,
			final Position destination) {
		return isDownSide(start, destination) && isVertical(start, destination);
	}

	public static boolean isLeft(final Position start,
			final Position destination) {
		return isLeftSide(start, destination)
				&& isHorizontal(start, destination);
	}

	public static boolean isRight(final Position start,
			final Position destination) {
		return isRightSide(start, destination)
				&& isHorizontal(start, destination);
	}

	public static boolean isUpSide(final Position start,
			final Position destination) {
		return destination.getY() > start.getY();
	}

	public static boolean isDownSide(final Position start,
			final Position destination) {
		return destination.getY() < start.getY();
	}

	public static boolean isLeftSide(final Position start,
			final Position destination) {
		return destination.getX() < start.getX();
	}

	public static boolean isRightSide(final Position start,
			final Position destination) {
		return destination.getX() > start.getX();
	}

	public static boolean isUpLeft(final Position start,
			final Position destination) {
		return isUpSide(start, destination) && isLeftSide(start, destination)
				&& isDiagonal(start, destination);
	}

	public static boolean isUpRight(final Position start,
			final Position destination) {
		return isUpSide(start, destination) && isRightSide(start, destination)
				&& isDiagonal(start, destination);
	}

	public static boolean isDownLeft(final Position start,
			final Position destination) {
		return isDownSide(start, destination) && isLeftSide(start, destination)
				&& isDiagonal(start, destination);
	}

	public static boolean isDownRight(final Position start,
			final Position destination) {
		return isDownSide(start, destination)
				&& isRightSide(start, destination)
				&& isDiagonal(start, destination);
	}

	public static boolean isDiagonal(final Position start,
			final Position destination) {
		return start.calculateXDistance(destination) == start
				.calculateYDistance(destination);
	}

	public static boolean isKnight(final Position start,
			final Position destination) {
		int totalXDistance = start.calculateXDistance(destination);
		int totalYDistance = start.calculateYDistance(destination);
		return Math.abs(totalXDistance) == 1 && Math.abs(totalYDistance) == 2
				|| Math.abs(totalXDistance) == 2
				&& Math.abs(totalYDistance) == 1;
	}

}
