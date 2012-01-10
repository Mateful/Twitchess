package de.fhb.projects.Twitchess.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ChessStateVOTest {

	private ChessStateVO csvo;
	
	@Before
	public void init(){
		csvo = new ChessStateVO();
	}
	
	
	@Test
	public void hashCodeChangeAllTest(){
		assertEquals(887503681,csvo.hashCode());
		csvo.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertEquals(1921833681,csvo.hashCode());
		csvo.setId(10);
		assertEquals(1922131591,csvo.hashCode());
		csvo.setPlayerName("Ich");
		assertEquals(1924404697,csvo.hashCode());
		csvo.setResult(2);
		assertEquals(1924404699,csvo.hashCode());
	}
	
	@Test
	public void hashCodeChangeOneParameterTest(){
		csvo.setId(10);		
		assertEquals(887801591,csvo.hashCode());
		
		csvo = new ChessStateVO();
		csvo.setPlayerName("player1");
		assertEquals(-1528191023,csvo.hashCode());	
		
		csvo = new ChessStateVO();
		csvo.setResult(100);
		assertEquals(887503781,csvo.hashCode());
	}

	
	@Test
	public void equalsTest(){
		ChessStateVO csvo2 = new ChessStateVO();		
		assertTrue(csvo.equals(csvo2));	
		
		assertTrue(csvo.equals(csvo));
		assertTrue(!csvo.equals(null));
		assertTrue(!csvo.equals(new Integer(1)));
		
		csvo2= new ChessStateVO("player1","rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",null,null,null);
		assertTrue(csvo.equals(csvo2));
		
		csvo.setId(1);
		assertTrue(!csvo.equals(csvo2));
	}
	
	@Test
	public void toStringTest(){
		assertEquals("ChessState [id=0, playerName=null, fen=null, moves=null, date=null, result=null]",csvo.toString());
		csvo.setMoves("a2a3");
		
		assertEquals("ChessState [id=0, playerName=null, fen=null, moves=a2a3, date=null, result=null]",csvo.toString());
		csvo.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		csvo.setPlayerName("player1");
		csvo.setId(222);
		csvo.setResult(1234);
		assertEquals("ChessState [id=222, playerName=player1, fen=rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1, moves=a2a3, date=null, result=1234]",csvo.toString());
	}
	
}
