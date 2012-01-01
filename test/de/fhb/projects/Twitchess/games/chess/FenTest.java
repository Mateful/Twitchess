package de.fhb.projects.Twitchess.games.chess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

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
		Fen f = new Fen(
				"8/8/8/8/8/8/8 w KQkq - 0 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenHasMoreThanEightColumnsTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8/8 w KQkq - 0 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenHasMoreThanEight2ColumnsTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8/ w KQkq - 0 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenIsShortTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 0 1");
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
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenPlayerIsWhite2Test() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 W - - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenPlayerIsBlackTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 b - - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenPlayerIsBlack2Test() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 B - - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenPlayerIsMissingTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 - - 0 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenOnlyPositionTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenOnlyPosition2Test() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 ");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenAllCastlingTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w KQkq - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenCastlingMinusKTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w Qkq - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenCastlingMinusQTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w Kkq - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenCastlingMinusKQTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w kq - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenCastlingMissingTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w   - 0 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenEnPassantWhiteTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 b - e3 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenEnPassantWhiteWrongPosTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 b - e4 0 1");
		assertFalse(f.isValid());
	}
	
	
	@Test
	public void FenEnPassantBlackTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - H6 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenEnPassantBlackWrongPosTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - H7 0 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenNegativeHalfMoveTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - -1 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenLeadingZeroHalfMoveTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 033 1");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenHalfMoveTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 33 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenHalfMoveIsNullTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 0 1");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenMoveIsNullTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 0 0");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenMoveIsNegativeTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 0 -30");
		assertFalse(f.isValid());
	}
	
	@Test
	public void FenMoveIsPositiveTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8 w - - 0 30");
		assertTrue(f.isValid());
	}
	
	@Test
	public void FenNoSpaceBetweenPositionAndPlayerTest() {
		Fen f = new Fen(
				"8/8/8/8/8/8/8/8w - - 0 30");
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
}
