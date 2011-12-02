package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessBoard;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class Knight extends Figure {

	public Knight(Position position, Color color) {
		super(position, color);
	}

	@Override
	public List<Move> generateMoves(ChessBoard board) {
		List<Move> moves = new ArrayList<Move>();
		final int[] OFFSET_X = new int[] { 1, 1, 2, 2, -1, -1, -2, -2 };
		final int[] OFFSET_Y = new int[] { 2, -2, 1, -1, 2, -2, 1, -1 };
		int x, y;
		Position p;
		Figure f;

		for (int i = 0; i < 8; i++) {
			x = position.getX() + OFFSET_X[i];
			y = position.getY() + OFFSET_Y[i];

			p = Position.getPosition(x, y);
			f = board.getFigureAtPosition(x, y);
			if (canMoveTo(p, f))
				moves.add(new Move(position, p));
	
		}
		return moves;
	}

	private boolean canMoveTo(Position destination, Figure figureOnTarget) {
		return destination != null
				&& (figureOnTarget == null || figureOnTarget.getColor() != color);
	}
	
	@Override
	public Object clone() {
		return (Object) new Knight(position, color);
	}
}
