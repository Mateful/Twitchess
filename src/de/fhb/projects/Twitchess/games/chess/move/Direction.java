package de.fhb.projects.Twitchess.games.chess.move;

public abstract class Direction {
	protected DirectionType type;

	public Direction(final DirectionType type) {
		this.type = type;
	}

	public final DirectionType getType() {
		return type;
	}

	public final void setType(final DirectionType type) {
		this.type = type;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public final boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Direction other = (Direction) obj;
		return type == other.type;
	}
}
