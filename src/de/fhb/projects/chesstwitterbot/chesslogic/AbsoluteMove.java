package de.fhb.projects.chesstwitterbot.chesslogic;

public class AbsoluteMove {
	private Direction direction;
	private Position start, destination;

	public AbsoluteMove(Position start, Position destination) {
		this.start = start;
		this.destination = destination;
		setDirection(start, destination);
	}

	private void setDirection(Position start, Position destination) {
		int xDirection = destination.getX() - start.getX();
		int yDirection = destination.getY() - start.getY();

		if(xDirection > 0 && yDirection == 0)
			direction = Direction.RIGHT;
		else if(xDirection < 0 && yDirection == 0)
			direction = Direction.LEFT;
		else if(xDirection == 0 && yDirection > 0)
			direction = Direction.UP;
		else if(xDirection == 0 && yDirection < 0)
			direction = Direction.DOWN;
		else if(xDirection > 0 && yDirection > 0
				&& Math.abs(xDirection) == Math.abs(yDirection))
			direction = Direction.UPRIGHT;
		else if(xDirection > 0 && yDirection < 0
				&& Math.abs(xDirection) == Math.abs(yDirection))
			direction = Direction.DOWNRIGHT;
		else if(xDirection < 0 && yDirection > 0
				&& Math.abs(xDirection) == Math.abs(yDirection))
			direction = Direction.UPLEFT;
		else if(xDirection < 0 && yDirection < 0
				&& Math.abs(xDirection) == Math.abs(yDirection))
			direction = Direction.DOWNLEFT;
		else if(Math.abs(xDirection) == 1 && Math.abs(yDirection) == 2
				|| Math.abs(xDirection) == 2 && Math.abs(yDirection) == 1)
			direction = Direction.KNIGHT;
		else
			throw new RuntimeException("No such move!");
	}

	public RelativeMove getRelativeMove() {
		int distance = Math.max((Math.abs(destination.getX() - start.getX())),
				Math.abs(destination.getY() - start.getY()));		
		return new RelativeMove(direction, direction.equals(Direction.KNIGHT) ? false : distance > 1);
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

	@Override
	public String toString() {
		return ChessboardPositionToArrayPosition.parseArrayPostion(start)
				+ ChessboardPositionToArrayPosition
						.parseArrayPostion(destination);
	}
}
