package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.player.Color;

/**
 * This class holds every information about the chessboard for a standard game of chess. The
 * get*Figure*-methods return the figure(s) with their initial position and
 * color set.
 */
public final class ChessProperties {
	public static final int CHESSBOARD_WIDTH = 8, CHESSBOARD_HEIGHT = 8;
	public static final int CHESSBOARD_BOTTOM_RANK = 0;
	public static final int CHESSBOARD_TOP_RANK = 7;
	public static final int WHITE_PAWN_RANK = 1;
	public static final int BLACK_PAWN_RANK = 6;
	public static final Position WHITE_KING_POSITION = new Position(4, 0);
	public static final Position BLACK_KING_POSITION = new Position(4, 7);
	public static final Position WHITE_QUEEN_POSITION = new Position(3, 0);
	public static final Position BLACK_QUEEN_POSITION = new Position(3, 7);
	public static final Position[] WHITE_ROOK_POSITIONS = new Position[]{
			new Position(0, 0), new Position(7, 0)};
	public static final Position[] BLACK_ROOK_POSITIONS = new Position[]{
			new Position(0, 7), new Position(7, 7)};
	public static final Position[] WHITE_BISHOP_POSITIONS = new Position[]{
			new Position(2, 0), new Position(5, 0)};
	public static final Position[] BLACK_BISHOP_POSITIONS = new Position[]{
			new Position(2, 7), new Position(5, 7)};
	public static final Position[] WHITE_KNIGHT_POSITIONS = new Position[]{
			new Position(1, 0), new Position(6, 0)};
	public static final Position[] BLACK_KNIGHT_POSITIONS = new Position[]{
			new Position(1, 7), new Position(6, 7)};

	private ChessProperties() {
	}

	public static King getKing(final Color color) {
		return new King(color == WHITE
				? WHITE_KING_POSITION
				: BLACK_KING_POSITION, color);
	}

	public static Queen getQueen(final Color color) {
		return new Queen(color == WHITE
				? WHITE_QUEEN_POSITION
				: BLACK_QUEEN_POSITION, color);
	}

	public static Bishop[] getBishops(final Color color) {
		Bishop[] bishops = new Bishop[WHITE_ROOK_POSITIONS.length];
		for (int i = 0; i < bishops.length; i++) {
			bishops[i] = new Bishop(color == WHITE
					? WHITE_BISHOP_POSITIONS[i]
					: BLACK_BISHOP_POSITIONS[i], color);
		}
		return bishops;
	}

	public static Knight[] getKnights(final Color color) {
		Knight[] knights = new Knight[WHITE_ROOK_POSITIONS.length];
		for (int i = 0; i < knights.length; i++) {
			knights[i] = new Knight(color == WHITE
					? WHITE_KNIGHT_POSITIONS[i]
					: BLACK_KNIGHT_POSITIONS[i], color);
		}
		return knights;
	}

	public static Rook[] getRooks(final Color color) {
		Rook[] rooks = new Rook[WHITE_ROOK_POSITIONS.length];
		for (int i = 0; i < rooks.length; i++) {
			rooks[i] = new Rook(color == WHITE
					? WHITE_ROOK_POSITIONS[i]
					: BLACK_ROOK_POSITIONS[i], color);
		}
		return rooks;
	}

	public static Pawn[] getPawns(final Color color) {
		Pawn[] pawns = new Pawn[CHESSBOARD_WIDTH];
		for (int i = 0; i < CHESSBOARD_WIDTH; i++) {
			pawns[i] = new Pawn(new Position(i, color == WHITE
					? WHITE_PAWN_RANK
					: BLACK_PAWN_RANK), color);
		}
		return pawns;
	}

	public static Position getLeftRookPosition(Color color) {
		switch (color) {
			case WHITE :
				return WHITE_ROOK_POSITIONS[0];
			case BLACK :
				return BLACK_ROOK_POSITIONS[0];
			default :
				throw new RuntimeException("No Rook with this color.");
		}
	}

	public static Position getRightRookPosition(Color color) {
		switch (color) {
			case WHITE :
				return WHITE_ROOK_POSITIONS[1];
			case BLACK :
				return BLACK_ROOK_POSITIONS[1];
			default :
				throw new RuntimeException("No Rook with this color.");
		}
	}

	public static Position getKingPosition(Color color) {
		switch (color) {
			case WHITE :
				return WHITE_KING_POSITION;
			case BLACK :
				return BLACK_KING_POSITION;
			default :
				throw new RuntimeException("No King with this color.");
		}
	}
}
