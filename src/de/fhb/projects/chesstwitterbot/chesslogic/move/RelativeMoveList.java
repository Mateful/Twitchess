package de.fhb.projects.chesstwitterbot.chesslogic.move;

import java.util.ArrayList;

public class RelativeMoveList extends ArrayList<RelativeMove> {
	private static final long serialVersionUID = 1L;

	public boolean contains(AbsoluteMove move) {
		return contains(move.getRelativeMove());
	}
	
	public boolean contains(RelativeMove move) {
		RelativeMove moveInList;
		for(int i = 0; i < size(); i++) {
			moveInList = get(i);
			if(haveSameDirection(move, moveInList) && haveSameRange(move, moveInList))
				return true;
		}
		return false;
	}

	private boolean haveSameDirection(RelativeMove move, RelativeMove moveInList) {
		return move.getDirection().equals(moveInList.getDirection());
	}
	
	private boolean haveSameRange(RelativeMove move, RelativeMove moveInList) {
		return moveInList.isInfinite() || move.isInfinite() == moveInList.isInfinite();
	}
}
