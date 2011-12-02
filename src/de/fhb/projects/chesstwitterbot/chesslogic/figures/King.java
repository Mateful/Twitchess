package de.fhb.projects.chesstwitterbot.chesslogic.figures;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessBoard;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public class King extends Figure {
	public King(Position position, Color color) {
		super(position, color);
	}

	@Override
	public List<Move> generateMoves(ChessBoard board) {
		List<Move> moves = new ArrayList<Move>();

		addSimpleMoves(board, moves);
		addCastling(board, moves);

		return moves;
	}

	private void addCastling(ChessBoard board, List<Move> moves) {
		if (isKingCastlingPossible(board)) {
			moves.add(new Move(position, Position.getPosition(
					position.getX() + 2, position.getY())));
		}

		if (isQueenCastlingPossible(board)) {
			moves.add(new Move(position, Position.getPosition(
					position.getX() - 2, position.getY())));
		}
	}

	private boolean isKingCastlingPossible(ChessBoard board) {
		boolean isCastlingAllowed = board.getKingCastling()[color == Color.WHITE ? 0
				: 1];
		boolean isKingOnCorrectSpot = board.getFigureAtPosition(4,
				(color == Color.WHITE) ? 0 : 7) == this;
		boolean isRookOnCorrectSpot = board.getFigureAtPosition(7,
				(color == Color.WHITE) ? 0 : 7) instanceof Rook;
		boolean isSpaceInBetweenEmpty = board.getFigureAtPosition(5,
				(color == Color.WHITE) ? 0 : 7) == null
				&& board.getFigureAtPosition(6, (color == Color.WHITE) ? 0 : 7) == null;

		return isCastlingAllowed && isKingOnCorrectSpot && isRookOnCorrectSpot
				&& isSpaceInBetweenEmpty;
	}

	private boolean isQueenCastlingPossible(ChessBoard board) {
		boolean isCastlingAllowed = board.getQueenCastling()[color == Color.WHITE ? 0
				: 1];
		boolean isKingOnCorrectSpot = board.getFigureAtPosition(4,
				(color == Color.WHITE) ? 0 : 7) == this;
		boolean isRookOnCorrectSpot = board.getFigureAtPosition(0,
				(color == Color.WHITE) ? 0 : 7) instanceof Rook;
		boolean isSpaceInBetweenEmpty = board.getFigureAtPosition(3,
				(color == Color.WHITE) ? 0 : 7) == null
				&& board.getFigureAtPosition(2, (color == Color.WHITE) ? 0 : 7) == null
				&& board.getFigureAtPosition(1, (color == Color.WHITE) ? 0 : 7) == null;

		return isCastlingAllowed && isKingOnCorrectSpot && isRookOnCorrectSpot
				&& isSpaceInBetweenEmpty;
	}

	private void addSimpleMoves(ChessBoard board, List<Move> moves) {
		Position p;
		Figure f;

		for (int x = -1; x <= 1; ++x) {
			for (int y = -1; y <= 1; ++y) {
				if (distanceIsGreaterNull(x, y)) {
					p = Position.getPosition(position.getX() + x,
							position.getY() + y);
					f = board.getFigureAtPosition(position.getX() + x,
							position.getY() + y);
					if (canMoveTo(p, f))
						moves.add(new Move(position, p));
				}
			}
		}
	}

	private boolean distanceIsGreaterNull(int x, int y) {
		return !(x == 0 && y == 0);
	}

	private boolean canMoveTo(Position destination, Figure figureOnTarget) {
		return destination != null
				&& (figureOnTarget == null || figureOnTarget.getColor() != color);
	}

	@Override
	public Object clone() {
		return (Object) new King(position, color);
	}
}
