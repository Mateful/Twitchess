package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessBoard;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class Queen extends Figure {

	public Queen(Position position, Color color) {
		super(position, color);
	}

	@Override
	public List<Move> generateMoves(ChessBoard board) {
		final int[] FACTOR_X = new int[] { 1, 0, -1 };
		final int[] FACTOR_Y = new int[] { 1, 0, -1 };

		List<Move> moves = new ArrayList<Move>();

		Position p;
		Figure f;
		boolean stop;

		for (int fx = 0; fx < 3; fx++) {
			for (int fy = 0; fy < 3; fy++) {
				stop = false;
				if (FACTOR_X[fx] != 0 || FACTOR_Y[fy] != 0) {
					for (int x = position.getX() + FACTOR_X[fx], y = position
							.getY() + FACTOR_Y[fy]; x >= 0 && x < 8 && y >= 0
							&& y < 8 && !stop; x += FACTOR_X[fx], y += FACTOR_Y[fy]) {
						p = Position.getPosition(x, y);
						f = board.getFigureAtPosition(x, y);
						if (canMoveTo(p, f)) {
							moves.add(new Move(position, p));
							if (f != null)
								stop = true;
						}
						else
							stop = true;

					}
				}
			}
		}

		return moves;
	}

	private boolean canMoveTo(Position destination, Figure figureOnTarget) {
		return destination != null
				&& (figureOnTarget == null || figureOnTarget.getColor() != color);
	}
	
	@Override
	public Object clone() {
		return (Object) new Queen(position, color);
	}
}
