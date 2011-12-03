package de.fhb.projects.chesstwitterbot.chesslogic.move;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessboardPositionToArrayPosition;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class AbsoluteMove {
	public static final AbsoluteMove NO_MOVE = new AbsoluteMove();
	private Direction direction;
	private Position start, destination;
	private int totalXDistance, totalYDistance;

	private AbsoluteMove() {
		direction = Direction.NODIRECTION;
		start = new Position(0, 0);
		destination = start;
		totalXDistance = totalYDistance = 0;
	}

	public AbsoluteMove(Position start, Position destination) {
		this.start = start;
		this.destination = destination;
		setDirection(start, destination);
		totalXDistance = Math.abs(totalXDistance);
		totalYDistance = Math.abs(totalYDistance);
	}

	private void setDirection(Position start, Position destination) {
		totalXDistance = destination.getX() - start.getX();
		totalYDistance = destination.getY() - start.getY();

		if(totalXDistance > 0 && totalYDistance == 0)
			direction = Direction.RIGHT;
		else if(totalXDistance < 0 && totalYDistance == 0)
			direction = Direction.LEFT;
		else if(totalXDistance == 0 && totalYDistance > 0)
			direction = Direction.UP;
		else if(totalXDistance == 0 && totalYDistance < 0)
			direction = Direction.DOWN;
		else if(totalXDistance > 0 && totalYDistance > 0
				&& Math.abs(totalXDistance) == Math.abs(totalYDistance))
			direction = Direction.UPRIGHT;
		else if(totalXDistance > 0 && totalYDistance < 0
				&& Math.abs(totalXDistance) == Math.abs(totalYDistance))
			direction = Direction.DOWNRIGHT;
		else if(totalXDistance < 0 && totalYDistance > 0
				&& Math.abs(totalXDistance) == Math.abs(totalYDistance))
			direction = Direction.UPLEFT;
		else if(totalXDistance < 0 && totalYDistance < 0
				&& Math.abs(totalXDistance) == Math.abs(totalYDistance))
			direction = Direction.DOWNLEFT;
		else if(Math.abs(totalXDistance) == 1 && Math.abs(totalYDistance) == 2
				|| Math.abs(totalXDistance) == 2
				&& Math.abs(totalYDistance) == 1)
			direction = Direction.KNIGHT;
		else
			throw new RuntimeException("No such move!");
	}

	public static AbsoluteMove up(Position position, int steps) {
		return new AbsoluteMove(position, new Position(position.getX(),
				position.getY() + steps));
	}

	public static AbsoluteMove down(Position position, int steps) {
		return new AbsoluteMove(position, new Position(position.getX(),
				position.getY() - steps));
	}

	public static AbsoluteMove left(Position position, int steps) {
		return new AbsoluteMove(position, new Position(position.getX() - steps,
				position.getY()));
	}

	public static AbsoluteMove right(Position position, int steps) {
		return new AbsoluteMove(position, new Position(position.getX() + steps,
				position.getY()));
	}

	public RelativeMove getRelativeMove() {
		int distance = Math.max((Math.abs(destination.getX() - start.getX())),
				Math.abs(destination.getY() - start.getY()));
		return new RelativeMove(direction,
				direction.equals(Direction.KNIGHT) ? false : distance > 1);
	}

	public Direction getDirection() {
		return direction;
	}

	public Position getStart() {
		return start;
	}

	public Position getDestination() {
		return destination;
	}

	public int getTotalXDistance() {
		return totalXDistance;
	}

	public int getTotalYDistance() {
		return totalYDistance;
	}

	@Override
	public String toString() {
		return ChessboardPositionToArrayPosition.parseArrayPostion(start)
				+ ChessboardPositionToArrayPosition
						.parseArrayPostion(destination);
	}
}
