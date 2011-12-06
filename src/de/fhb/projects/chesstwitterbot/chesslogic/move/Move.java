package de.fhb.projects.chesstwitterbot.chesslogic.move;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessboardPositionToArrayPosition;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class Move {
	public static final Move NO_MOVE = new Move();
	private Direction direction;
	private Position start, destination;

	private Move() {
		direction = new Direction(DirectionType.NODIRECTION);
		start = new Position(0, 0);
		destination = start;
	}

	public Move(Position start, Position destination) {
		this.start = start;
		this.destination = destination;
		setDirection(start, destination);
	}

	private void setDirection(Position start, Position destination) {
		DirectionType directionType = DirectionType.getDirectionType(start,
				destination);

		if (start.calculateXDistance(destination) <= 1
				&& start.calculateYDistance(destination) <= 1
				|| directionType.equals(DirectionType.KNIGHT))
			this.direction = new OneStepDirection(directionType);
		else
			this.direction = new InfiniteDirection(directionType);
	}

	public static Move up(Position position, int steps) {
		return new Move(position, new Position(position.getX(), position.getY()
				+ steps));
	}

	public static Move down(Position position, int steps) {
		return new Move(position, new Position(position.getX(), position.getY()
				- steps));
	}

	public static Move left(Position position, int steps) {
		return new Move(position, new Position(position.getX() - steps,
				position.getY()));
	}

	public static Move right(Position position, int steps) {
		return new Move(position, new Position(position.getX() + steps,
				position.getY()));
	}

	public static Move upRight(Position position, int steps) {
		return new Move(position, new Position(position.getX() + 1,
				position.getY() + steps));
	}

	public static Move upLeft(Position position, int steps) {
		return new Move(position, new Position(position.getX() - 1,
				position.getY() + steps));
	}

	public static Move downRight(Position position, int steps) {
		return new Move(position, new Position(position.getX() + 1,
				position.getY() - steps));
	}

	public static Move downLeft(Position position, int steps) {
		return new Move(position, new Position(position.getX() - 1,
				position.getY() - steps));
	}

	public Direction getDirection() {
		return direction;
	}

	public DirectionType getDirectionType() {
		return direction.getType();
	}

	public Position getStart() {
		return start;
	}

	public Position getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return ChessboardPositionToArrayPosition.parseArrayPostion(start)
				+ ChessboardPositionToArrayPosition
						.parseArrayPostion(destination);
	}
}
