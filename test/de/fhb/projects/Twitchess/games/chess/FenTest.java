package de.fhb.projects.Twitchess.games.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public class FenTest {
	@Test
	public void FenStartPositionTest() {
		Fen f = new Fen(Fen.START_POSITION);
		assertTrue(f.isValid());
	}

	@Test
	public void FenRowHasLessThanEight1Test() {
		Fen f = new Fen(
				"rnbqkbnr/pppppppp/7/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenRowHasLessThanEight2Test() {
		Fen f = new Fen(
				"rnbqkbnr/ppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenRowHasMoreThanEight1Test() {
		Fen f = new Fen(
				"rnbqkbnr/pppppppp/9/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenRowHasMoreThanEight2Test() {
		Fen f = new Fen(
				"rnbqkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenHasUnknownCharacterTest() {
		Fen f = new Fen(
				"rnblkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenHasUnknownCharacter2Test() {
		Fen f = new Fen(
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPx/RNBQKBNR w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenIsNullTest() {
		Fen f = new Fen((String) null);
		assertFalse(f.isValid());
	}

	@Test
	public void FenIsEmptyTest() {
		Fen f = new Fen("");
		assertFalse(f.isValid());
	}

	@Test
	public void FenHasLessThanEightColumnsTest() {
		Fen f = new Fen("8/8/8/8/8/8/8 w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenHasMoreThanEightColumnsTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8/8 w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenHasMoreThanEight2ColumnsTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8/ w KQkq - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenIsShortTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenIsLongTest() {
		Fen f = new Fen(
				"pppppppp/pppppppp/pppppppp/pppppppp/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP w KQkq e3 49 100");
		assertTrue(f.isValid());
	}

	@Test
	public void FenPlayerIsWhiteTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenPlayerIsWhite2Test() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 W - - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenPlayerIsBlackTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 b - - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenPlayerIsBlack2Test() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 B - - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenPlayerIsMissingTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 - - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenOnlyPositionTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8");
		assertFalse(f.isValid());
	}

	@Test
	public void FenOnlyPosition2Test() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 ");
		assertFalse(f.isValid());
	}

	@Test
	public void FenAllCastlingTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w KQkq - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenCastlingMinusKTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w Qkq - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenCastlingMinusQTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w Kkq - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenCastlingMinusKQTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w kq - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenCastlingMissingTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w   - 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenEnPassantWhiteTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 b - e3 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenEnPassantWhiteWrongPosTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 b - e4 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenEnPassantBlackTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - H6 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenEnPassantBlackWrongPosTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - H7 0 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenNegativeHalfMoveTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - -1 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenLeadingZeroHalfMoveTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 033 1");
		assertFalse(f.isValid());
	}

	@Test
	public void FenHalfMoveTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 33 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenHalfMoveIsNullTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 0 1");
		assertTrue(f.isValid());
	}

	@Test
	public void FenMoveIsNullTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 0 0");
		assertFalse(f.isValid());
	}

	@Test
	public void FenMoveIsNegativeTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 0 -30");
		assertFalse(f.isValid());
	}

	@Test
	public void FenMoveIsPositiveTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8 w - - 0 30");
		assertTrue(f.isValid());
	}

	@Test
	public void FenNoSpaceBetweenPositionAndPlayerTest() {
		Fen f = new Fen("8/8/8/8/8/8/8/8w - - 0 30");
		assertFalse(f.isValid());
	}

	@Test
	public void FenIsValidLetterValidTest() {
		Fen f = new Fen("");

		assertTrue(f.isValidLetter('r'));
		assertTrue(f.isValidLetter('R'));
		assertTrue(f.isValidLetter('p'));
		assertTrue(f.isValidLetter('P'));
		assertTrue(f.isValidLetter('b'));
		assertTrue(f.isValidLetter('B'));
		assertTrue(f.isValidLetter('n'));
		assertTrue(f.isValidLetter('N'));
		assertTrue(f.isValidLetter('q'));
		assertTrue(f.isValidLetter('Q'));
		assertTrue(f.isValidLetter('k'));
		assertTrue(f.isValidLetter('K'));
	}

	@Test
	public void FenIsValidLetterInvalidTest() {
		Fen f = new Fen("");

		assertFalse(f.isValidLetter('a'));
		assertFalse(f.isValidLetter('c'));
		assertFalse(f.isValidLetter('d'));
		assertFalse(f.isValidLetter('e'));
		assertFalse(f.isValidLetter('f'));
		assertFalse(f.isValidLetter('g'));
		assertFalse(f.isValidLetter('h'));
		assertFalse(f.isValidLetter('i'));
		assertFalse(f.isValidLetter('z'));
		assertFalse(f.isValidLetter('!'));
		assertFalse(f.isValidLetter('y'));
		assertFalse(f.isValidLetter('x'));
	}

	@Test(expected = RuntimeException.class)
	public void invalidGameState() {
		Fen f = new Fen("");
		f.getGameState();
	}

	@Test
	public void initialGameState() {
		Fen fen = Fen.getStartingPosition();
		GameState state = fen.getGameState();

		assertEquals(
				new King(ChessProperties.BLACK_KING_POSITION, Color.BLACK),
				state.getFigure(ChessProperties.BLACK_KING_POSITION));
		assertEquals(new Queen(ChessProperties.BLACK_QUEEN_POSITION,
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_QUEEN_POSITION));
		assertEquals(new Rook(ChessProperties.BLACK_ROOK_POSITIONS[0],
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_ROOK_POSITIONS[0]));
		assertEquals(new Rook(ChessProperties.BLACK_ROOK_POSITIONS[1],
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_ROOK_POSITIONS[1]));
		assertEquals(new Knight(ChessProperties.BLACK_KNIGHT_POSITIONS[0],
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_KNIGHT_POSITIONS[0]));
		assertEquals(new Knight(ChessProperties.BLACK_KNIGHT_POSITIONS[1],
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_KNIGHT_POSITIONS[1]));
		assertEquals(new Bishop(ChessProperties.BLACK_BISHOP_POSITIONS[0],
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_BISHOP_POSITIONS[0]));
		assertEquals(new Bishop(ChessProperties.BLACK_BISHOP_POSITIONS[1],
				Color.BLACK),
				state.getFigure(ChessProperties.BLACK_BISHOP_POSITIONS[1]));
		for (int i = 0; i < ChessProperties.CHESSBOARD_WIDTH; i++) {
			Position p = new Position(i, ChessProperties.BLACK_PAWN_RANK);
			assertEquals(new Pawn(p, Color.BLACK), state.getFigure(p));
		}

		assertEquals(
				new King(ChessProperties.WHITE_KING_POSITION, Color.WHITE),
				state.getFigure(ChessProperties.WHITE_KING_POSITION));
		assertEquals(new Queen(ChessProperties.WHITE_QUEEN_POSITION,
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_QUEEN_POSITION));
		assertEquals(new Rook(ChessProperties.WHITE_ROOK_POSITIONS[0],
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_ROOK_POSITIONS[0]));
		assertEquals(new Rook(ChessProperties.WHITE_ROOK_POSITIONS[1],
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_ROOK_POSITIONS[1]));
		assertEquals(new Knight(ChessProperties.WHITE_KNIGHT_POSITIONS[0],
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_KNIGHT_POSITIONS[0]));
		assertEquals(new Knight(ChessProperties.WHITE_KNIGHT_POSITIONS[1],
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_KNIGHT_POSITIONS[1]));
		assertEquals(new Bishop(ChessProperties.WHITE_BISHOP_POSITIONS[0],
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_BISHOP_POSITIONS[0]));
		assertEquals(new Bishop(ChessProperties.WHITE_BISHOP_POSITIONS[1],
				Color.WHITE),
				state.getFigure(ChessProperties.WHITE_BISHOP_POSITIONS[1]));
		for (int i = 0; i < ChessProperties.CHESSBOARD_WIDTH; i++) {
			Position p = new Position(i, ChessProperties.WHITE_PAWN_RANK);
			assertEquals(new Pawn(p, Color.WHITE), state.getFigure(p));
		}

		assertEquals(0, state.getHalfMoveClock());
		assertEquals(1, state.getFullMoveNumber());
		assertEquals(Color.WHITE, state.getCurrentColor());
		assertTrue(state.canBlackCastleKingSide());
		assertTrue(state.canBlackCastleQueenSide());
		assertTrue(state.canWhiteCastleKingSide());
		assertTrue(state.canWhiteCastleQueenSide());
	}

	@Test
	public void gameStateCounters() {
		Fen fen = new Fen("8/8/8/8/8/8/8/8 w KQkq - 34 89");
		GameState state = fen.getGameState();
		assertEquals(34, state.getHalfMoveClock());
		assertEquals(89, state.getFullMoveNumber());
	}
}
