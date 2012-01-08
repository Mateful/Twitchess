package de.fhb.projects.Twitchess.games.chess.move;

import de.fhb.projects.Twitchess.exception.InvalidMoveException;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;

/**
 * This class contains information about one move in a standard game of chess.
 * The Move-class manages the flow of the game, since it is used to take the
 * game from one {@link GameState} to another.
 */
public final class Move {
	public static final Move NO_MOVE = new Move();
	private Direction direction;
	private Position start, destination;
	private Figure movingFigure, hitTarget, promoteTo;

	private Move() {
		direction = new OneStepDirection(DirectionType.NODIRECTION);
		start = new Position(0, 0);
		destination = start;
		hitTarget = NoFigure.NO_FIGURE;
		promoteTo = NoFigure.NO_FIGURE;
	}

	public Move(final Position start, final Position destination) {
		this.start = start;
		this.destination = destination;
		setDirection();
		hitTarget = NoFigure.NO_FIGURE;
		promoteTo = NoFigure.NO_FIGURE;
	}

	private void setDirection() {
		DirectionType directionType = DirectionType.getDirectionType(start,
				destination);

		if (isOneStepMove() || directionType.equals(DirectionType.KNIGHT)) {
			direction = new OneStepDirection(directionType);
		} else {
			direction = new InfiniteDirection(directionType);
		}
	}

	private boolean isOneStepMove() {
		return start.calculateXDistance(destination) <= 1
				&& start.calculateYDistance(destination) <= 1;
	}

	public static Move up(final Position position, final int steps) {
		return new Move(position, new Position(position.x, position.y + steps));
	}

	public static Move down(final Position position, final int steps) {
		return new Move(position, new Position(position.x, position.y - steps));
	}

	public static Move left(final Position position, final int steps) {
		return new Move(position, new Position(position.x - steps, position.y));
	}

	public static Move right(final Position position, final int steps) {
		return new Move(position, new Position(position.x + steps, position.y));
	}

	public static Move upRight(final Position position, final int steps) {
		return new Move(position, new Position(position.x + steps, position.y
				+ steps));
	}

	public static Move upLeft(final Position position, final int steps) {
		return new Move(position, new Position(position.x - steps, position.y
				+ steps));
	}

	public static Move downRight(final Position position, final int steps) {
		return new Move(position, new Position(position.x + steps, position.y
				- steps));
	}

	public static Move downLeft(final Position position, final int steps) {
		return new Move(position, new Position(position.x - steps, position.y
				- steps));
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (destination.hashCode());
		result = prime * result + (direction.hashCode());
		result = prime * result + (start.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		Move other = (Move) obj;
		return start.equals(other.start)
				&& destination.equals(other.destination)
				&& direction.equals(other.direction);
	}

	@Override
	public String toString() {
		return "Move [direction=" + direction + ", start=" + start
				+ ", destination=" + destination + "]";
	}

	public String getLongNotation() {
		return start.toString() + destination.toString();
	}

	public Figure getHitTarget() {
		return hitTarget;
	}

	public void setHitTarget(Figure hitTarget) {
		this.hitTarget = hitTarget;
	}

	public Figure getPromoteTo() {
		return promoteTo;
	}

	/**
	 * Sets the figure the moving pawn should promote to. NOTE: The position of
	 * promoteTo doesn't concern us, since it will be set by GameState.
	 * 
	 * @param promoteTo
	 */
	public void setPromoteTo(Figure promoteTo) {
		this.promoteTo = promoteTo;
	}

	public Figure getMovingFigure() {
		return movingFigure;
	}

	public void setMovingFigure(Figure movingFigure) {
		this.movingFigure = movingFigure;
	}
	
	public static Move valueOf(final String s) throws InvalidMoveException {
		Position start = null, destination = null;
		if (s == null || !s.matches("([a-hA-H][1-8]){2}[QqRrBbNn]?"))
			throw new InvalidMoveException("Invalid move. Could not parse it.");

		for (int i = 0; i < 2; ++i) {
			int x = Character.toLowerCase(s.charAt(i * 2)) - 'a';
			int y = s.charAt(i * 2 + 1) - '1';

			if (i == 0)
				start = new Position(x, y);
			else
				destination = new Position(x, y);
		}

		Move m = new Move(start, destination);

		if (s.length() == 5) {
			switch (Character.toLowerCase(s.charAt(4))) {
				case 'q' :
					m.setPromoteTo(new Queen(destination));
					break;
				case 'r' :
					m.setPromoteTo(new Rook(destination));
					break;
				case 'b' :
					m.setPromoteTo(new Bishop(destination));
					break;
				case 'n' :
					m.setPromoteTo(new Knight(destination));
					break;
			}
		}

		return m;
	}
}
