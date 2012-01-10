package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fhb.projects.Twitchess.exception.InvalidMoveException;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class MoveTest {
	@Test(expected = InvalidMoveException.class)
	public void valueOfNullTest() {
		Move.valueOf(null);
	}

	@Test(expected = InvalidMoveException.class)
	public void valueOfEmptyTest() {
		Move.valueOf("");
	}

	@Test(expected = InvalidMoveException.class)
	public void valueOfInvalidCharactersTest() {
		Move.valueOf("dada");
	}

	@Test(expected = InvalidMoveException.class)
	public void valueOfInvalidCharacters2Test() {
		Move.valueOf("i1k2");
	}

	@Test(expected = RuntimeException.class)
	public void valueOfA1a1Test() {
		Move.valueOf("a1a1");
	}

	@Test
	public void valueOfA1b1Test() {
		Move m = Move.valueOf("a1b1");
		assertEquals(new Position(0, 0), m.getStart());
		assertEquals(new Position(1, 0), m.getDestination());
	}

	@Test
	public void valueOfA1B1Test() {
		Move m = Move.valueOf("A1B1");
		assertEquals(new Position(0, 0), m.getStart());
		assertEquals(new Position(1, 0), m.getDestination());
	}

	@Test
	public void valueOfA1h1Test() {
		Move m = Move.valueOf("A1H1");
		assertEquals(new Position(0, 0), m.getStart());
		assertEquals(new Position(7, 0), m.getDestination());
	}

	@Test
	public void valueOfA8h1Test() {
		Move m = Move.valueOf("a8h1");
		assertEquals(new Position(0, 7), m.getStart());
		assertEquals(new Position(7, 0), m.getDestination());
	}

	@Test
	public void valueOfA8h8Test() {
		Move m = Move.valueOf("a8h8");
		assertEquals(new Position(0, 7), m.getStart());
		assertEquals(new Position(7, 7), m.getDestination());
	}

	@Test
	public void valueOfb7c8PromoteToKnightTest() {
		Move m = Move.valueOf("b7c8N");
		assertEquals(new Position(1, 6), m.getStart());
		assertEquals(new Position(2, 7), m.getDestination());
		assertEquals(new Knight(new Position(2, 7)), m.getPromoteTo());
	}

	@Test
	public void valueOfC2d1PromoteToBishopTest() {
		Move m = Move.valueOf("c2d1B");
		assertEquals(new Position(2, 1), m.getStart());
		assertEquals(new Position(3, 0), m.getDestination());
		assertEquals(new Bishop(new Position(3, 0)), m.getPromoteTo());
	}

	@Test
	public void valueOfC2d1PromoteToRookTest() {
		Move m = Move.valueOf("c2d1R");
		assertEquals(new Position(2, 1), m.getStart());
		assertEquals(new Position(3, 0), m.getDestination());
		assertEquals(new Rook(new Position(3, 0)), m.getPromoteTo());
	}

	@Test
	public void valueOfC2d1PromoteToQueenTest() {
		Move m = Move.valueOf("c2d1Q");
		assertEquals(new Position(2, 1), m.getStart());
		assertEquals(new Position(3, 0), m.getDestination());
		assertEquals(new Queen(new Position(3, 0)), m.getPromoteTo());
	}

	@Test
	public void valueOfPromoteToIsLowerCaseTest() {
		Move m = Move.valueOf("c2d1q");
		assertEquals(new Position(2, 1), m.getStart());
		assertEquals(new Position(3, 0), m.getDestination());
		assertEquals(new Queen(new Position(3, 0)), m.getPromoteTo());
	}

	@Test(expected = InvalidMoveException.class)
	public void valueOfPromoteToIsPawnTest() {
		Move.valueOf("c2d1P");
	}

	@Test(expected = InvalidMoveException.class)
	public void valueOfPromoteToIsKingTest() {
		Move.valueOf("c2d1K");
	}

	@Test(expected = InvalidMoveException.class)
	public void valueOfMoveToLongTest() {
		Move.valueOf("a1b1c2");
	}

	@Test
	public void getLongNotationNoPromotionTest() {
		Move m = new Move(new Position(1, 1), new Position(3, 3));
		assertEquals("b2d4", m.getLongNotation());
	}

	@Test
	public void getLongNotationNoPromotion2Test() {
		Move m = new Move(new Position(6, 6), new Position(6, 7));
		assertEquals("g7g8", m.getLongNotation());
	}

	@Test
	public void getLongNotationPromotionToQueenTest() {
		Move m = new Move(new Position(6, 6), new Position(6, 7));
		m.setPromoteTo(new Queen(new Position(6, 7)));
		assertEquals("g7g8q", m.getLongNotation());
	}

	@Test
	public void getLongNotationPromotionToRookTest() {
		Move m = new Move(new Position(6, 6), new Position(6, 7));
		m.setPromoteTo(new Rook(new Position(6, 7)));
		assertEquals("g7g8r", m.getLongNotation());
	}

	@Test
	public void getLongNotationPromotionToBishopTest() {
		Move m = new Move(new Position(6, 6), new Position(6, 7));
		m.setPromoteTo(new Bishop(new Position(6, 7)));
		assertEquals("g7g8b", m.getLongNotation());
	}

	@Test
	public void getLongNotationPromotionToKnightTest() {
		Move m = new Move(new Position(6, 6), new Position(6, 7));
		m.setPromoteTo(new Knight(new Position(6, 7)));
		assertEquals("g7g8n", m.getLongNotation());
	}
}
