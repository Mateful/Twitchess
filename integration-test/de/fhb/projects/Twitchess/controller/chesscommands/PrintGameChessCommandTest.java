package de.fhb.projects.Twitchess.controller.chesscommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.chesscommands.PrintGameChessCommand;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class PrintGameChessCommandTest {

	private ChessStateDAOInterface dao;
	private PrintGameChessCommand chessCommand;
	private ChessStateVO chessState;
	private List<ChessStateVO> state;
	private List<String> parameters;

	@Before
	public void init() {
		dao = EasyMock.createStrictMock(ChessStateDAOInterface.class);
		chessCommand = new PrintGameChessCommand(dao);
		state = new ArrayList<ChessStateVO>();
		parameters = new ArrayList<String>();
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
	public void testProcessInput() throws SQLException, ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		assertEquals("Current Position: {"
				+ "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
				+ "}", chessCommand.processInput("player1", parameters));
		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputEmptyParameter() throws SQLException,
			ChessManagerException {
		parameters.clear();
		parameters.add("");
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		assertNotNull(chessCommand.processInput("player1", parameters));
	}
	
	@Test (expected = ChessManagerException.class)
	public void testProccessInputNullState() throws SQLException, ChessManagerException{
		parameters.clear();
		parameters.add("");
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				null);
		EasyMock.replay(dao);
		assertNotNull(chessCommand.processInput("player1", parameters));
	}

	@Test
	public void testProcessInputNullParameter() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		assertEquals("Current Position: {"
				+ "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
				+ "}", chessCommand.processInput("player1", null));
		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputDontFindAGame() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				null);
		EasyMock.replay(dao);
		assertNotNull(chessCommand.processInput("player1", parameters));
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputFindToManyGames() throws SQLException,
			ChessManagerException {
		state.add(state.get(0));
		state.add(state.get(0));
		state.add(state.get(0));
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		assertNotNull(chessCommand.processInput("player1", parameters));
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputSQLException() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(
				new SQLException());
		EasyMock.replay(dao);
		assertNotNull(chessCommand.processInput("player1", parameters));
	}
}
