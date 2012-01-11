package de.fhb.projects.Twitchess.integrationtests.controller.chesscommands;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.chesscommands.NewGameChessCommand;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class NewGameChessCommandTest {

	private ChessStateDAOInterface dao;
	private NewGameChessCommand chessCommand;
	private ChessStateVO chessState;
	private List<ChessStateVO> state;
	private List<String> parameters;

	@Before
	public void init() {
		dao = EasyMock.createNiceMock(ChessStateDAOInterface.class);
		chessCommand = new NewGameChessCommand(dao);
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

	@Test(expected = ChessManagerException.class)
	public void testProcessInputParameterIsNull() throws ChessManagerException,
			SQLException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", null);

	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputParameterIsWrong()
			throws ChessManagerException, SQLException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				null);
		EasyMock.replay(dao);
		parameters.add("d");
		chessCommand.processInput("player1", parameters);
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputRunningGame() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", parameters);
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputSQLException() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(
				new SQLException());
		EasyMock.replay(dao);

		chessCommand.processInput("player1", parameters);
	}

	@Test
	public void testProcessInputNewGame() throws ChessManagerException,
			SQLException {

		EasyMock.expect(dao.findNotFinishedGameByPlayer("player2")).andReturn(
				null);
		EasyMock.expect(dao.insertIntoTable(EasyMock.isA(ChessStateVO.class)))
				.andReturn(1);
		EasyMock.replay(dao);

		assertTrue(chessCommand.processInput("player2", parameters) instanceof String);

		EasyMock.verify(dao);
	}
}
