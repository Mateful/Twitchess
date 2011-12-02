package de.fhb.projects.chesstwitterbot.chesslogic;

public class Move {
	private Position start, destination;
	private boolean enPassant;

	public Position getStart() {
		return start;
	}

	public void setStart(Position start) {
		this.start = start;
	}

	public Position getDestination() {
		return destination;
	}

	public void setDestination(Position destination) {
		this.destination = destination;
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public void setEnPassant(boolean isEnPassant) {
		this.enPassant = isEnPassant;
	}

	public Move(Position start, Position destination) {
		setStart(start);
		setDestination(destination);
		setEnPassant(false);
	}
	
	public Move(Position start, Position destination, boolean enPassant) {
		setStart(start);
		setDestination(destination);
		setEnPassant(enPassant);
	}

	public String toString() {
		return start.toString() + destination.toString();
	}

}
