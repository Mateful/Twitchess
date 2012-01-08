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
	public void valueOfTestNull() {
		Move.valueOf(null);
	}
	
	@Test(expected = InvalidMoveException.class)
	public void valueOfTestEmpty() {
		Move.valueOf("");
	}
	
	@Test(expected = InvalidMoveException.class)
	public void valueOfTestInvalidCharacters() {
		Move.valueOf("dada");
	}
	
	@Test(expected = InvalidMoveException.class)
	public void valueOfTestInvalidCharacters2() {
		Move.valueOf("i1k2");
	}
	
	@Test(expected = RuntimeException.class)
	public void valueOfTestA1a1() {
		Move.valueOf("a1a1");
	}
	
	@Test
	public void valueOfTestA1b1() {
		Move m = Move.valueOf("a1b1");
		assertEquals(new Position(0,0), m.getStart());
		assertEquals(new Position(1,0), m.getDestination());
	}
	
	@Test
	public void valueOfTestA1B1() {
		Move m = Move.valueOf("A1B1");
		assertEquals(new Position(0,0), m.getStart());
		assertEquals(new Position(1,0), m.getDestination());
	}
	
	@Test
	public void valueOfTestA1h1() {
		Move m = Move.valueOf("A1H1");
		assertEquals(new Position(0,0), m.getStart());
		assertEquals(new Position(7,0), m.getDestination());
	}
	
	@Test
	public void valueOfTestA8h1() {
		Move m = Move.valueOf("a8h1");
		assertEquals(new Position(0,7), m.getStart());
		assertEquals(new Position(7,0), m.getDestination());
	}
	
	@Test
	public void valueOfTestA8h8() {
		Move m = Move.valueOf("a8h8");
		assertEquals(new Position(0,7), m.getStart());
		assertEquals(new Position(7,7), m.getDestination());
	}
	
	@Test
	public void valueOfTestb7c8PromoteToKnight() {
		Move m = Move.valueOf("b7c8N");
		assertEquals(new Position(1,6), m.getStart());
		assertEquals(new Position(2,7), m.getDestination());
		assertEquals(new Knight(new Position(2,7)), m.getPromoteTo());
	}
	
	@Test
	public void valueOfTestC2d1PromoteToBishop() {
		Move m = Move.valueOf("c2d1B");
		assertEquals(new Position(2,1), m.getStart());
		assertEquals(new Position(3,0), m.getDestination());
		assertEquals(new Bishop(new Position(3,0)), m.getPromoteTo());
	}
	
	@Test
	public void valueOfTestC2d1PromoteToRook() {
		Move m = Move.valueOf("c2d1R");
		assertEquals(new Position(2,1), m.getStart());
		assertEquals(new Position(3,0), m.getDestination());
		assertEquals(new Rook(new Position(3,0)), m.getPromoteTo());
	}
	
	@Test
	public void valueOfTestC2d1PromoteToQueen() {
		Move m = Move.valueOf("c2d1Q");
		assertEquals(new Position(2,1), m.getStart());
		assertEquals(new Position(3,0), m.getDestination());
		assertEquals(new Queen(new Position(3,0)), m.getPromoteTo());
	}
	

	@Test
	public void valueOfTestPromoteToIsLowerCase() {
		Move m = Move.valueOf("c2d1q");
		assertEquals(new Position(2,1), m.getStart());
		assertEquals(new Position(3,0), m.getDestination());
		assertEquals(new Queen(new Position(3,0)), m.getPromoteTo());
	}
	
	@Test(expected = InvalidMoveException.class)
	public void valueOfTestPromoteToIsPawn() {
		Move.valueOf("c2d1P");
	}
	
	@Test(expected = InvalidMoveException.class)
	public void valueOfTestPromoteToIsKing() {
		Move.valueOf("c2d1K");
	}
	
	@Test(expected = InvalidMoveException.class)
	public void valueOfTestMoveToLong() {
		Move.valueOf("a1b1c2");
	}
	
}
