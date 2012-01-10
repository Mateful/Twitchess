package de.fhb.projects.Twitchess.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class ChessManagerTest {

	private UCIEngineInterface uci;
	private ChessManager cm;
	private ChessStateDAOInterface dao;;
	private ChessStateVO chessState;
	private List<ChessStateVO> state;

	@Before
	public void init() {
		uci = EasyMock.createStrictMock(UCIEngineInterface.class);
		dao = EasyMock.createStrictMock(ChessStateDAOInterface.class);
		cm = new ChessManager(dao, uci);
		state = new ArrayList<ChessStateVO>();
		chessState = new ChessStateVO();
		chessState.setId(1);
		chessState
				.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		chessState.setPlayerName("player1");
		chessState.setResult(null);
		chessState.setDate(null);
		state.add(chessState);
	}

	@Test
	public void getParameterTest() throws ChessManagerException {
		String message = "@MatefulBot chess print";
		assertEquals("print", cm.getParameters(message).get(0));
		message = "chess d4d2 dasisteintest param3 param4";
		assertEquals("d4d2", cm.getParameters(message).get(0));
		assertEquals("dasisteintest", cm.getParameters(message).get(1));
		assertEquals("param3", cm.getParameters(message).get(2));
		assertEquals("param4", cm.getParameters(message).get(3));
	}

	@Test(expected = ChessManagerException.class)
	public void getParameterWithoutStartCommandTest()
			throws ChessManagerException {
		String message = "that message throw a exception";
		cm.getParameters(message);
	}

	// zweiter Zweig konnte ich nicht erreichen
	@Test(expected = ChessManagerException.class)
	public void getParameterWithoutParameterTest() throws ChessManagerException {
		String message = "@blubb dfnio bsdnuiof";
		cm.getParameters(message);
	}

	@Test(expected = ChessManagerException.class)
	public void getParameterOnlyNameParamTest() throws ChessManagerException {
		String message = "@MatefulBot";
		cm.getParameters(message);
	}

	@Test(expected = ChessManagerException.class)
	public void getParameterOnlyChessCommandTest() throws ChessManagerException {
		String message = "chess";
		cm.getParameters(message);
	}

	@Test
	public void processInputTest() throws SQLException {
		String message = "@MateFulBot chess print";
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);

		cm.processInput("player1", message);

		EasyMock.verify(dao);
	}

	@Test
	public void processInputInvalidComandTest() throws SQLException {
		String message = "@MatefulBot chess invaldiCommand";
		assertTrue(cm.processInput("player1", message).contains("Error"));
	}
}