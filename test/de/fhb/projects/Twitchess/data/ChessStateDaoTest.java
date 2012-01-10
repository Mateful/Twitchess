package de.fhb.projects.Twitchess.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class ChessStateDaoTest {
	
	private ChessStateDAO dao;	
	
	@Before
	public void init() throws SQLException, ClassNotFoundException{
		dao =new ChessStateDAO("test-files/chess.db");
	}
	
	@Test
	public void testFindNotFinishedGameByPlayer()throws SQLException{
		assertEquals(new ArrayList<ChessStateVO>(),dao.findNotFinishedGameByPlayer("player2"));
		assertEquals("player1",dao.findNotFinishedGameByPlayer("player1").get(0).getPlayerName());
	}
	
	@Test (expected = SQLException.class)
	public void testInsertIntoTableNullVO() throws SQLException{
		dao.insertIntoTable(null);
	}
	
	@Test
	public void testShowAll() throws SQLException{
		dao.showAll();
	}
	
	@Test (expected = SQLException.class)
	public void testUpdateTableNullVO() throws SQLException{
		dao.updateTable(null);
	}
}
