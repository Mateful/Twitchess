package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessBoard;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class Pawn extends Figure {
	public Pawn(Position position, Color color) {
		super(position, color);
	}

	@Override
	public List<Move> generateMoves(ChessBoard board) {
		List<Move> moves = new ArrayList<Move>();
		int factor = color == Color.WHITE ? 1 : -1;

		if (addMoveForward(board, moves, factor)) {
			if (isDoubleStepPossible()) {
				addDoubleStep(board, moves, factor);
			}
		}

		addCapture(board, moves, factor);

		if (board.getEnPassant() != null)
			addEnPassant(board, moves, factor);

		return moves;
	}

	private boolean addMoveForward(ChessBoard board, List<Move> moves,
			int factor) {
		Position p;
		Figure f;

		p = Position.getPosition(position.getX(), position.getY() + factor);
		f = board
				.getFigureAtPosition(position.getX(), position.getY() + factor);

		if (canMoveTo(p, f)) {
			moves.add(new Move(position, p));

			return true;
		}

		return false;
	}

	private void addCapture(ChessBoard board, List<Move> moves, int factor) {
		Position p;
		Figure f;

		p = Position.getPosition(position.getX() + 1, position.getY() + factor);
		f = board.getFigureAtPosition(position.getX() + 1, position.getY()
				+ factor);

		if (canCapture(p, f)) {
			moves.add(new Move(position, p));
		}

		p = Position.getPosition(position.getX() - 1, position.getY() + factor);
		f = board.getFigureAtPosition(position.getX() - 1, position.getY()
				+ factor);

		if (canCapture(p, f)) {
			moves.add(new Move(position, p));
		}
	}

	private void addEnPassant(ChessBoard board, List<Move> moves, int factor) {
		Position p;
		Figure f;

		p = Position.getPosition(position.getX() + 1, position.getY() + factor);
		f = board.getFigureAtPosition(position.getX() + 1, position.getY());
		if (canCaptureEnPassant(board, p, f)) {
			moves.add(new Move(position, p, true));
		} else {
			p = Position.getPosition(position.getX() - 1, position.getY()
					+ factor);
			f = board.getFigureAtPosition(position.getX() - 1, position.getY());

			if (canCaptureEnPassant(board, p, f)) {
				moves.add(new Move(position, p, true));
			}
		}
	}

	private boolean canCaptureEnPassant(ChessBoard b, Position p, Figure f) {
		return f != null && f == b.getEnPassant() && f.getColor() != color
				&& p != null;
	}

	private boolean canCapture(Position destination, Figure figureOnTarget) {
		return destination != null && figureOnTarget != null
				&& figureOnTarget.getColor() != color;
	}

	private void addDoubleStep(ChessBoard board, List<Move> moves, int factor) {
		Position p;
		Figure f;
		p = Position.getPosition(position.getX(), position.getY() + 2 * factor);
		f = board.getFigureAtPosition(position.getX(), position.getY() + 2
				* factor);

		if (canMoveTo(p, f))
			moves.add(new Move(position, p));
	}

	private boolean isDoubleStepPossible() {

		return position.getY() == 1 && color == Color.WHITE
				|| position.getY() == 6 && color == Color.BLACK;
	}

	private boolean canMoveTo(Position destination, Figure figureOnTarget) {
		return destination != null && (figureOnTarget == null);
	}
	
	@Override
	public Object clone() {
		return (Object) new Pawn(position, color);
	}

}
