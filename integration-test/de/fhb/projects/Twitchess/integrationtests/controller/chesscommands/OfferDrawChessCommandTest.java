package de.fhb.projects.Twitchess.integrationtests.controller.chesscommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.UCIEngineInterface;
import de.fhb.projects.Twitchess.controller.chesscommands.OfferDrawChessCommand;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;

public class OfferDrawChessCommandTest {
	
	private OfferDrawChessCommand odcc;
	private ChessStateDAOInterface dao;
	private UCIEngineInterface uci;
	private List<String> parameters;
	private ChessStateVO chessState;
	private List<ChessStateVO> state;
	
	@Before
	public void init(){
		uci = EasyMock.createStrictMock(UCIEngineInterface.class);
		dao = EasyMock.createStrictMock(ChessStateDAOInterface.class);
		odcc = new OfferDrawChessCommand(dao, uci);
		parameters = new ArrayList<String>();
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
	
	@Test (expected = ChessManagerException.class)
	public void testProcessNullParameter() throws ChessManagerException{
		
		odcc.processInput("player1",null);
	}
	
	@Test (expected = ChessManagerException.class)
	public void testProcessInputNotARunningGame1() throws SQLException, ChessManagerException{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(null);
		EasyMock.replay(dao);
		odcc.processInput("player1", parameters);
	}
	
	@Test (expected = ChessManagerException.class)
	public void testProcessInputNotARunningGame2() throws SQLException, ChessManagerException{
		state.clear();
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		EasyMock.replay(dao);
		odcc.processInput("player1", parameters);
	}
	
	@Test (expected = ChessManagerException.class)
	public void testProcessInputToManyGames() throws SQLException, ChessManagerException{
		state.add(state.get(0));
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		EasyMock.replay(dao);
		odcc.processInput("player1", parameters);
	}
	
	@Test 
	public void testProcessInput() throws Throwable{
		state.get(0).setFen("7k/8/8/8/8/8/8/7K w KQkq - 20 21");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		uci.init();
		EasyMock.expect(uci.calculateScore("7k/8/8/8/8/8/8/7K w KQkq - 20 21", 2000)).andReturn(10);		
		dao.updateTable(vo);
		uci.destroy();
		EasyMock.replay(uci);
		EasyMock.replay(dao);
		assertEquals("Fair enough, I accept your offer! {7k/8/8/8/8/8/8/7K w KQkq - 20 21}",odcc.processInput("player1", parameters));		
		EasyMock.verify(uci);
		EasyMock.verify(dao);
	}
	
	@Test
	public void testProcessInputDontAcceptDraw() throws Throwable{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		uci.init();
		EasyMock.expect(uci.calculateScore("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 2000)).andReturn(100);		
		uci.destroy();
		EasyMock.replay(uci);
		EasyMock.replay(dao);
		assertEquals("It is too early to call it a draw!",odcc.processInput("player1", parameters));		
		EasyMock.verify(uci);
		EasyMock.verify(dao);
	}

	@Test (expected = ChessManagerException.class)
	public void testProcessInputIOException() throws SQLException, IOException, ChessManagerException{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		uci.init();
		EasyMock.expectLastCall().andThrow(new IOException());
		EasyMock.replay(uci);
		EasyMock.replay(dao);
		odcc.processInput("player1", parameters);
	}
	
	@Test (expected = ChessManagerException.class)
	public void testProcessInputThrowableException() throws Throwable{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(state);
		uci.init();
		EasyMock.expect(uci.calculateScore("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", 2000)).andReturn(100);		
		uci.destroy();
		EasyMock.expectLastCall().andThrow(new Throwable());
		EasyMock.replay(uci);
		EasyMock.replay(dao);
		odcc.processInput("player1", parameters);
	}
	
	@Test (expected = ChessManagerException.class)
	public void testProcessInputSQLException() throws Throwable{
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(new SQLException());
		EasyMock.replay(dao);
		odcc.processInput("player1", parameters);
	}
	
	@Test
	public void testAcceptDrawScoreLimitValue() throws ChessManagerException{
		state.get(0).setFen("7k/8/8/8/8/8/8/7K w KQkq - 20 21");
		Fen fen = new Fen (state.get(0).getFen());
		GameState s = fen.getGameState();
		assertTrue(odcc.acceptDraw(s, 99));
		assertTrue(!odcc.acceptDraw(s, 100));
		assertTrue(odcc.acceptDraw(s, Integer.MIN_VALUE));
		assertTrue(!odcc.acceptDraw(s, Integer.MAX_VALUE));
	}
	
	@Test
	public void testAcceptDrawGameState() throws ChessManagerException{
		Fen fen = new Fen (state.get(0).getFen());
		GameState s = fen.getGameState();
		assertTrue(!odcc.acceptDraw(s, 0));
		
		state.get(0).setFen("7k/8/8/8/8/8/8/7K w KQkq - 0 10");
		fen = new Fen (state.get(0).getFen());
		s = fen.getGameState();		
		assertTrue(!odcc.acceptDraw(s, 0));
		
		state.get(0).setFen("7k/8/8/8/8/8/8/7K w KQkq - 0 11");
		fen = new Fen (state.get(0).getFen());
		s = fen.getGameState();		
		assertTrue(odcc.acceptDraw(s, 0));
	}
	
	@Test (expected = ChessManagerException.class)
	public void testAcceptDrawNullState() throws ChessManagerException{
		odcc.acceptDraw(null, 0);
	}
}
