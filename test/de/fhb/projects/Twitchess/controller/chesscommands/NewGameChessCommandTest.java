package de.fhb.projects.Twitchess.controller.chesscommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

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
		dao = EasyMock.createStrictMock(ChessStateDAOInterface.class);
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
	
	@Test (expected = NullPointerException.class)
	public void processInputNoColor1Test() throws ChessManagerException, SQLException{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		EasyMock.replay(dao);
		chessCommand.processInput("player1", null);
		EasyMock.verify(dao);
	}
	
	@Test (expected = ChessManagerException.class)
	public void processInputNoColor2Test() throws ChessManagerException, SQLException{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(null);
		EasyMock.replay(dao);
		parameters.add("d");
		chessCommand.processInput("player1", parameters);
		EasyMock.verify(dao);
	}
	
	@Test 
	public void processInputRunningGameTest() throws SQLException, ChessManagerException{
		
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		EasyMock.replay(dao);
		
		assertEquals("You already have a running game. You have to cancel it first before you can play another game.", chessCommand.processInput("player1", parameters));
		
		EasyMock.verify(dao);
		
	}
	
	@Test 
	public void processInputSQLExceptionTest() throws SQLException, ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(new SQLException());
		EasyMock.replay(dao);
		
        chessCommand.processInput("player1", parameters);
		
		EasyMock.verify(dao);
	}
	
	
//	TODO Zeitangabe verhindert momentan das Testen in InsertIntoTable(...)
//	@Test
//	public void processInputNewGameTest() throws ChessManagerException, SQLException{
//		
//		EasyMock.expect(dao.findNotFinishedGameByPlayer("player2")).andReturn(null);
//		EasyMock.expect(dao.insertIntoTable(chessState)).andReturn(1);
//		EasyMock.replay(dao);		
//		
//		parameters.add("w");
//		assertEquals("new game successfully started, you are now playing as white", chessCommand.processInput("player2", parameters));
//		
//		EasyMock.verify(dao);
//	}
}
