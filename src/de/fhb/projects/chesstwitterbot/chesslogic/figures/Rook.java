package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessBoard;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class Rook extends Figure {

	public Rook(Position position, Color color) {
		super(position, color);
	}

	@Override
	public List<Move> generateMoves(ChessBoard board) {

		List<Move> moves = new ArrayList<Move>();

		generateAndAddHorizontalMoves(board, moves);
		generateAndAddVerticalMoves(board, moves);

		return moves;
	}

	private void generateAndAddHorizontalMoves(ChessBoard board,
			List<Move> moves) {
		final int[] FACTOR = new int[] { 1, -1 };
		Position p;
		Figure f;
		boolean stop;

		for (int i = 0; i < 2; i++) {
			stop = false;
			for (int x = position.getX() + FACTOR[i], y = position.getY(); x >= 0
					&& x < 8 && !stop; x += FACTOR[i]) {
				p = Position.getPosition(x, y);
				f = board.getFigureAtPosition(x, y);
				if (canMoveTo(p, f))
					moves.add(new Move(position, p));
				else
					stop = true;

			}
		}
	}

	private void generateAndAddVerticalMoves(ChessBoard board, List<Move> moves) {
		final int[] FACTOR = new int[] { 1, -1 };
		Position p;
		Figure f;
		boolean stop;

		for (int i = 0; i < 2; i++) {
			stop = false;
			for (int x = position.getX(), y = position.getY() + FACTOR[i]; y >= 0
					&& y < 8 && !stop; y += FACTOR[i]) {
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

	private boolean canMoveTo(Position destination, Figure figureOnTarget) {
		return destination != null
				&& (figureOnTarget == null || figureOnTarget.getColor() != color);
	}
	
	@Override
	public Object clone() {
		return (Object) new Rook(position, color);
	}
}
