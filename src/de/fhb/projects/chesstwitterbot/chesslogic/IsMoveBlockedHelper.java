package de.fhb.projects.chesstwitterbot.chesslogic;

import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;

public class IsMoveBlockedHelper {
	int yStart, xStart, yDest, xDest, yToAdd, xToAdd;

	public void setUp(AbsoluteMove absoluteMove) {
		yStart = absoluteMove.getStart().getY() + 1;
		yDest = absoluteMove.getDestination().getY();
		yToAdd = 1;
	}
	
	public void setDown(AbsoluteMove absoluteMove) {
		yStart = absoluteMove.getStart().getY() - 1;
		yDest = absoluteMove.getDestination().getY();
		yToAdd = -1;
	}
	
	public void setRight(AbsoluteMove absoluteMove) {
		xStart = absoluteMove.getStart().getX() + 1;
		xDest = absoluteMove.getDestination().getX();
		xToAdd = 1;
	}
	
	public void setLeft(AbsoluteMove absoluteMove) {
		xStart = absoluteMove.getStart().getX() - 1;
		xDest = absoluteMove.getDestination().getX();
		xToAdd = -1;
	}
}
