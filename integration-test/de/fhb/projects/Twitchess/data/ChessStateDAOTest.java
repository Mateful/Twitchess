package de.fhb.projects.Twitchess.data;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import de.fhb.projects.Twitchess.data.ChessStateDAO;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;

public class ChessStateDAOTest {
	protected static final String DB_NAME = "test-files/test.db";
	protected static ChessStateDAOInterface dao;
	protected static ChessStateDAO csdao;

	@BeforeClass
	public static void init() throws SQLException, ClassNotFoundException {
		File f = new File(DB_NAME);

		if (f.exists())
			f.delete();

		dao = new ChessStateDAO(DB_NAME);
		csdao = new ChessStateDAO("test-files/chess.db");
	}

	@Test
	public void countWinsOfPlayerTest() throws SQLException {
		dao.insertIntoTable(new ChessStateVO("Name", "fen", "moves", null, 1));
		dao.insertIntoTable(new ChessStateVO("Name", "fen", "moves", null, 1));
		dao.insertIntoTable(new ChessStateVO("Name", "fen2", "moves", null, 0));
		dao.insertIntoTable(new ChessStateVO("Name", "fen3", "moves", null, -1));
		dao.insertIntoTable(new ChessStateVO("Namea", "fen3", "moves", null, 1));
		assertEquals("Test of countWinsOfPlayer", 2,
				dao.countWinsOfPlayer("Name"));
		assertEquals("Test of countWinsOfPlayer", 1,
				dao.countWinsOfPlayer("Namea"));
	}

	@After
	public void clean() throws SQLException {
		dao.truncateTable();
	}

	@Test
	public void insertAndFindByPlayerTestStandard() throws SQLException {
		String name = "name";
		String fen = "fen";
		String moves = "moves";
		Date date = new Date(0);
		Integer i = new Integer(1);

		dao.insertIntoTable(new ChessStateVO(name, fen, moves, date, i));
		List<ChessStateVO> result = dao.findGameByPlayer(name);

		assertEquals("size = 1", 1, result.size());

		ChessStateVO element = result.get(0);

		assertEquals("player = ", name, element.getPlayerName());
		assertEquals("fen = ", fen, element.getFen());
		assertEquals("moves= ", moves, element.getMoves());
		assertEquals("date = ", date, element.getDate());
		assertEquals("result = ", i, element.getResult());
	}

	@Test(expected = SQLException.class)
	public void insertTestPlayerNameIsNull() throws SQLException {
		dao.insertIntoTable(new ChessStateVO(null, "fen", "moves", null, 1));
	}

	@Test(expected = SQLException.class)
	public void insertTestPlayerFenIsNull() throws SQLException {
		dao.insertIntoTable(new ChessStateVO("name", null, "moves",
				new Date(0), 0));
	}

	@Test
	public void insertAndFindByPlayerTestPlayerNameAndFenAreNotNull()
			throws SQLException {
		String name = "name";
		String fen = "fen";
		String moves = null;
		Date date = null;
		Integer i = null;

		dao.insertIntoTable(new ChessStateVO(name, fen, moves, date, i));
		List<ChessStateVO> result = dao.findGameByPlayer(name);

		assertEquals("size = 1", 1, result.size());

		ChessStateVO element = result.get(0);

		assertEquals("player = ", name, element.getPlayerName());
		assertEquals("fen = ", fen, element.getFen());
		assertNull("moves= ", element.getMoves());
		assertNull("date = ", element.getDate());
		assertNull("result = ", element.getResult());
	}

	@Test
	public void UpdateAndFindByPlayerTest() throws SQLException {
		String name = "name";
		String fen = "fen";
		String moves = null;
		Date date = null;
		Integer i = null;

		ChessStateVO v = new ChessStateVO(name, fen, moves, date, i);

		dao.insertIntoTable(v);
		moves = "e2e4";
		v.setMoves(moves);
		dao.updateTable(v);

		List<ChessStateVO> result = dao.findGameByPlayer(name);

		assertEquals("size = 1", 1, result.size());

		ChessStateVO element = result.get(0);

		assertEquals("player = ", name, element.getPlayerName());
		assertEquals("fen = ", fen, element.getFen());
		assertEquals("moves= ", moves, element.getMoves());
		assertEquals("date = ", date, element.getDate());
		assertEquals("result = ", i, element.getResult());
	}

	@Test
	public void testFindNotFinishedGameByPlayer()throws SQLException{
		assertEquals(new ArrayList<ChessStateVO>(),csdao.findNotFinishedGameByPlayer("player2"));
		assertEquals("player1",csdao.findNotFinishedGameByPlayer("player1").get(0).getPlayerName());
	}
	
	@Test (expected = SQLException.class)
	public void testInsertIntoTableNullVO() throws SQLException{
		csdao.insertIntoTable(null);
	}
	
	@Test
	public void testShowAll() throws SQLException{
		csdao.showAll();
	}
	
	@Test (expected = SQLException.class)
	public void testUpdateTableNullVO() throws SQLException{
		csdao.updateTable(null);
	}
	
	@Test
	public void testClose() throws SQLException{
		Connection c;
		c = EasyMock.createStrictMock(Connection.class);
		csdao.connection=c;
		EasyMock.expect(c.isClosed()).andReturn(true);
		EasyMock.expect(c.isClosed()).andReturn(false);
		c.close();
		EasyMock.replay(c);
		csdao.close();
		csdao.close();
		csdao.connection = null;
		csdao.close();
		EasyMock.verify(c);
	}
	
	@AfterClass
	public static void cleanUp() {
		File f = new File(DB_NAME);

		if (f.exists())
			f.delete();
	}
}
