package de.fhb.projects.Twitchess.controller.chesscommands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

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
	public void processInputTest() throws SQLException, ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputChessManagerExceptionTest() throws SQLException,
			ChessManagerException {
		parameters.add("");
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test
	public void processInputNullPointerExeptionTest() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", null);
		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputDontFindAGameTest() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				null);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputFindToManyGamesTest() throws SQLException,
			ChessManagerException {
		state.add(state.get(0));
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputSQLExceptionTest() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(
				new SQLException());
		EasyMock.replay(dao);
		chessCommand.processInput("player1", parameters);
		EasyMock.verify(dao);
	}
}
