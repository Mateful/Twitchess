package de.fhb.projects.chesstwitterbot.chesslogic.move;

public class Direction {
	protected DirectionType type;

	public Direction(DirectionType type) {
		super();
		this.type = type;
	}

	public DirectionType getType() {
		return type;
	}

	public void setType(DirectionType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Direction other = (Direction) obj;
		if (type != other.type)
			return false;
		return true;
	}
}
