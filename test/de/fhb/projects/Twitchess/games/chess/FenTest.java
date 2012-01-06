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
		assertTrue(Fen.isValid(Fen.START_POSITION));
	}

	@Test
	public void FenRowHasLessThanEight1Test() {
		assertFalse(Fen.isValid("rnbqkbnr/pppppppp/7/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void FenRowHasLessThanEight2Test() {
		assertFalse(Fen.isValid("rnbqkbnr/ppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void FenRowHasMoreThanEight1Test() {
		assertFalse(Fen.isValid("rnbqkbnr/pppppppp/9/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void FenRowHasMoreThanEight2Test() {
		assertFalse(Fen.isValid("rnbqkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void FenHasUnknownCharacterTest() {
		assertFalse(Fen.isValid("rnblkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void FenHasUnknownCharacter2Test() {
		assertFalse(Fen.isValid("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPx/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void FenIsNullTest() {
		assertFalse(Fen.isValid(null));
	}

	@Test
	public void FenIsEmptyTest() {
		assertFalse(Fen.isValid(""));
	}

	@Test
	public void FenHasLessThanEightColumnsTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8 w KQkq - 0 1"));
	}

	@Test
	public void FenHasMoreThanEightColumnsTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8/8 w KQkq - 0 1"));
	}

	@Test
	public void FenHasMoreThanEight2ColumnsTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8/ w KQkq - 0 1"));
	}

	@Test
	public void FenIsShortTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w - - 0 1"));
	}

	@Test
	public void FenIsLongTest() {
		assertTrue(Fen.isValid("pppppppp/pppppppp/pppppppp/pppppppp/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP w KQkq e3 49 100"));
	}

	@Test
	public void FenPlayerIsWhiteTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w - - 0 1"));
	}

	@Test
	public void FenPlayerIsWhite2Test() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 W - - 0 1"));
	}

	@Test
	public void FenPlayerIsBlackTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 b - - 0 1"));
	}

	@Test
	public void FenPlayerIsBlack2Test() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 B - - 0 1"));
	}

	@Test
	public void FenPlayerIsMissingTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 - - 0 1"));
	}

	@Test
	public void FenOnlyPositionTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8"));
	}

	@Test
	public void FenOnlyPosition2Test() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 "));
	}

	@Test
	public void FenAllCastlingTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w KQkq - 0 1"));
	}

	@Test
	public void FenCastlingMinusKTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w Qkq - 0 1"));
	}

	@Test
	public void FenCastlingMinusQTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w Kkq - 0 1"));
	}

	@Test
	public void FenCastlingMinusKQTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w kq - 0 1"));
	}

	@Test
	public void FenCastlingMissingTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 w   - 0 1"));
	}

	@Test
	public void FenEnPassantWhiteTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 b - e3 0 1"));
	}

	@Test
	public void FenEnPassantWhiteWrongPosTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 b - e4 0 1"));
	}

	@Test
	public void FenEnPassantBlackTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w - H6 0 1"));
	}

	@Test
	public void FenEnPassantBlackWrongPosTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 w - H7 0 1"));
	}

	@Test
	public void FenNegativeHalfMoveTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 w - - -1 1"));
	}

	@Test
	public void FenLeadingZeroHalfMoveTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 w - - 033 1"));
	}

	@Test
	public void FenHalfMoveTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w - - 33 1"));
	}

	@Test
	public void FenHalfMoveIsNullTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w - - 0 1"));
	}

	@Test
	public void FenMoveIsNullTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 w - - 0 0"));
	}

	@Test
	public void FenMoveIsNegativeTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 w - - 0 -30"));
	}

	@Test
	public void FenMoveIsPositiveTest() {
		assertTrue(Fen.isValid("8/8/8/8/8/8/8/8 w - - 0 30"));
	}

	@Test
	public void FenNoSpaceBetweenPositionAndPlayerTest() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8w - - 0 30"));
	}
	
	@Test
	public void wrongPlayerColor() {
		assertFalse(Fen.isValid("8/8/8/8/8/8/8/8 q - - 0 30"));
	}

	@Test
	public void FenIsValidLetterValidTest() {
		assertTrue(Fen.isValidLetter('r'));
		assertTrue(Fen.isValidLetter('R'));
		assertTrue(Fen.isValidLetter('p'));
		assertTrue(Fen.isValidLetter('P'));
		assertTrue(Fen.isValidLetter('b'));
		assertTrue(Fen.isValidLetter('B'));
		assertTrue(Fen.isValidLetter('n'));
		assertTrue(Fen.isValidLetter('N'));
		assertTrue(Fen.isValidLetter('q'));
		assertTrue(Fen.isValidLetter('Q'));
		assertTrue(Fen.isValidLetter('k'));
		assertTrue(Fen.isValidLetter('K'));
	}

	@Test
	public void FenIsValidLetterInvalidTest() {
		assertFalse(Fen.isValidLetter('a'));
		assertFalse(Fen.isValidLetter('c'));
		assertFalse(Fen.isValidLetter('d'));
		assertFalse(Fen.isValidLetter('e'));
		assertFalse(Fen.isValidLetter('f'));
		assertFalse(Fen.isValidLetter('g'));
		assertFalse(Fen.isValidLetter('h'));
		assertFalse(Fen.isValidLetter('i'));
		assertFalse(Fen.isValidLetter('z'));
		assertFalse(Fen.isValidLetter('!'));
		assertFalse(Fen.isValidLetter('y'));
		assertFalse(Fen.isValidLetter('x'));
	}

	@Test(expected = RuntimeException.class)
	public void invalidGameState() {
		Fen f = new Fen("");
		f.getGameState();
	}

	@Test
	public void initialGameState() {
		GameState state = Fen.createGameState(Fen.START_POSITION);

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

	@Test
	public void parseGameStateToFen() {
		Fen fen = new Fen(
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 48 125");
		GameState state = fen.getGameState();
		assertEquals(fen, new Fen(state));
	}
}
