package de.fhb.projects.Twitchess.controller.chesscommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.UCIEngineInterface;
import de.fhb.projects.Twitchess.controller.chesscommands.MoveChessCommand.MoveType;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.data.ResultType;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.exception.ChessManagerGameAlreadyOverException;
import de.fhb.projects.Twitchess.exception.UCIException;

public class MoveChessCommandTest {

	private ChessStateDAOInterface dao;
	private UCIEngineInterface uci;
	private ChessStateVO chessState;
	private List<ChessStateVO> state;
	private MoveChessCommand mcc;
	private List<String> parameters;

	@Before
	public void init() {
		uci = EasyMock.createStrictMock(UCIEngineInterface.class);
		dao = EasyMock.createStrictMock(ChessStateDAOInterface.class);
		mcc = new MoveChessCommand(dao, uci);
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

	@Test(expected = ChessManagerException.class)
	public void processInputNullParameterTest() throws ChessManagerException,
			SQLException {
		mcc.processInput("player", null);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputOneParameterTest() throws ChessManagerException,
			SQLException {
		parameters.add("p1");
		mcc.processInput("player", parameters);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputTwoParameterTest() throws ChessManagerException,
			SQLException {
		parameters.add("p1");
		parameters.add("p2");
		mcc.processInput("player", parameters);
	}

	@Test
	public void processInputAiTest() throws Throwable {
		parameters.add("ai");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
						2000)).andReturn("a2a3");
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);

		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}

	@Test
	public void processInputPlayerTest() throws Throwable {
		parameters.add("player");
		parameters.add("a2a3");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);

		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);

		EasyMock.verify(dao);

	}

	@Test
	public void processInputBothTest() throws Throwable {
		parameters.add("both");
		parameters.add("a2a3");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/P7/1PPPPPPP/RNBQKBNR b KQkq - 0 1",
						2000)).andReturn("a7a6");
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);

		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputInvalidMoveTest() throws Throwable {
		parameters.add("a2a7");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
						2000)).andReturn("a2a3");
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}
	
	@Test(expected = ChessManagerException.class)
	public void processInputInvalidMoveTest2() throws Throwable {
		parameters.add("a1k1");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
						2000)).andReturn("a2a3");
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}
	
	@Test(expected = ChessManagerException.class)
	public void processInputInvalidMoveAITest() throws Throwable {
		parameters.add("a2a4");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
						2000)).andReturn("a7k5");
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}

	@Test
	public void processInputValidMoveTest() throws Throwable {
		parameters.add("c2c3");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/2P5/PP1PPPPP/RNBQKBNR b KQkq - 0 1",
						2000)).andReturn("a7a6");
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}

	@Test(expected = ChessManagerException.class)
	public void processInputUCIExeptionTest() throws Throwable {
		parameters.add("c2c3");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/pppppppp/8/8/8/2P5/PP1PPPPP/RNBQKBNR b KQkq - 0 1",
						2000)).andThrow(new UCIException(""));
		uci.destroy();
		dao.updateTable(vo);
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}

	@Test
	public void getCurrentGameTest() throws SQLException, ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);

		EasyMock.replay(dao);
		assertEquals(state.get(0), mcc.getCurrentGame("player1"));

		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void getCurrentGameSQLExceptionTest() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(
				new SQLException());
		EasyMock.replay(dao);
		mcc.getCurrentGame("player1");

		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void getNoGameOpenTest() throws SQLException, ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				null);
		EasyMock.replay(dao);
		mcc.getCurrentGame("player1");
		EasyMock.verify(dao);
	}

	@Test
	public void parseMoveTypeTest() throws ChessManagerException {
		parameters.add("ai");
		assertEquals("AI", mcc.parseMoveType(parameters).toString());

		parameters.clear();
		parameters.add("player");
		parameters.add("a2a3");
		assertEquals(MoveChessCommand.MoveType.PLAYER,
				mcc.parseMoveType(parameters));

		parameters.clear();
		parameters.add("both");
		parameters.add("a2a3");
		assertEquals(MoveChessCommand.MoveType.AI_AFTER_PLAYER,
				mcc.parseMoveType(parameters));
	}

	@Test(expected = ChessManagerException.class)
	public void parseMoveTypeAiFalseTest() throws ChessManagerException {
		parameters.add("ai");
		parameters.add("AI");
		assertEquals(MoveChessCommand.MoveType.AI,
				mcc.parseMoveType(parameters));
	}

	@Test(expected = ChessManagerException.class)
	public void parseMoveTypePlayerFalseTest() throws ChessManagerException {
		parameters.clear();
		parameters.add("player");
		assertEquals(MoveChessCommand.MoveType.PLAYER,
				mcc.parseMoveType(parameters));
	}

	@Test(expected = ChessManagerException.class)
	public void parseMoveTypeBothFalseTest() throws ChessManagerException {
		parameters.clear();
		parameters.add("both");
		parameters.add("a2a3");
		parameters.add("");
		assertEquals(MoveChessCommand.MoveType.AI_AFTER_PLAYER,
				mcc.parseMoveType(parameters));
	}

	@Test(expected = ChessManagerException.class)
	public void parseMoveChessManagerExceptionTest()
			throws ChessManagerException {
		mcc.parseMove(parameters, MoveType.PLAYER);
	}

	@Test(expected = ChessManagerGameAlreadyOverException.class)
	public void isCheckmateTest() throws SQLException, ChessManagerException {
		List<ChessStateVO> state = new ArrayList<ChessStateVO>();
		ChessStateVO checkMateVO = new ChessStateVO();
		checkMateVO.setId(1);
		checkMateVO
				.setFen("rnbqkbnr/1p1p1Qpp/8/p1p1p3/2B1P3/8/PPPP1PPP/RNB1K1NR b KQkq - 0 4");
		checkMateVO.setPlayerName("player1");
		checkMateVO.setResult(null);
		checkMateVO.setDate(null);
		state.add(checkMateVO);
		parameters.add("player");
		parameters.add("c2c3");

		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		dao.updateTable(checkMateVO);
		EasyMock.replay(dao);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test
	public void isGameOverWhiteWinsTest() throws SQLException,
			ChessManagerException {
		List<ChessStateVO> state = new ArrayList<ChessStateVO>();
		ChessStateVO checkMateVO = new ChessStateVO();
		checkMateVO.setId(1);
		checkMateVO
				.setFen("rnbqkbnr/1p1p1ppp/8/p1p1p3/2B1P3/5Q2/PPPP1PPP/RNB1K1NR w KQkq c6 0 4");
		checkMateVO.setPlayerName("player1");
		checkMateVO.setResult(null);
		checkMateVO.setDate(null);
		state.add(checkMateVO);
		parameters.add("player");
		parameters.add("f3f7");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		dao.updateTable(vo);
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			public Object answer() {
				ChessStateVO arg1 = (ChessStateVO) EasyMock
						.getCurrentArguments()[0];
				assertTrue(arg1.getResult() != null);
				assertEquals(ResultType.WHITE_WINS.getNumber(), arg1
						.getResult().intValue());
				return null;
			}
		});
		EasyMock.replay(dao);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test
	public void isGameOverDrawTest() throws SQLException, ChessManagerException {
		List<ChessStateVO> state = new ArrayList<ChessStateVO>();
		ChessStateVO checkMateVO = new ChessStateVO();
		checkMateVO.setId(1);
		checkMateVO.setFen("8/8/8/8/5nk1/6r1/8/7K b - - 0 1");
		checkMateVO.setPlayerName("player1");
		checkMateVO.setResult(null);
		checkMateVO.setDate(null);
		state.add(checkMateVO);
		parameters.add("player");
		parameters.add("g3g2");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		dao.updateTable(vo);
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			public Object answer() {
				ChessStateVO arg1 = (ChessStateVO) EasyMock
						.getCurrentArguments()[0];
				assertTrue(arg1.getResult() != null);
				assertEquals(ResultType.REMIS.getNumber(), arg1.getResult()
						.intValue());
				return null;
			}
		});
		EasyMock.replay(dao);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test
	public void promotionTest() throws Throwable {
		List<ChessStateVO> state = new ArrayList<ChessStateVO>();
		ChessStateVO pos = new ChessStateVO();
		pos.setId(1);
		pos.setFen("r1bqkbnr/pppp1ppp/2n5/4P3/8/5N2/PpQ2PPP/RNB1KB1R b KQkq - 0 6");
		pos.setPlayerName("player1");
		pos.setResult(null);
		pos.setDate(null);
		state.add(pos);
		parameters.add("both");
		parameters.add("b2a1n");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"r1bqkbnr/pppp1ppp/2n5/4P3/8/5N2/P1Q2PPP/nNB1KB1R w Kkq - 0 7",
						2000)).andReturn("c2b2");
		uci.destroy();
		dao.updateTable(vo);

		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			public Object answer() {
				ChessStateVO arg1 = (ChessStateVO) EasyMock
						.getCurrentArguments()[0];
				assertEquals(
						"r1bqkbnr/pppp1ppp/2n5/4P3/8/5N2/PQ3PPP/nNB1KB1R b Kkq - 1 7",
						arg1.getFen());
				assertTrue(arg1.getResult() == null);
				return null;
			}
		});
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}

	@Test
	public void whiteIsDrawAITest() throws Throwable {
		List<ChessStateVO> state = new ArrayList<ChessStateVO>();
		ChessStateVO pos = new ChessStateVO();
		pos.setId(1);
		pos.setFen("7k/8/5K2/6R1/8/8/8/R7 w - - 0 1");
		pos.setPlayerName("player1");
		pos.setResult(null);
		pos.setDate(null);
		state.add(pos);
		parameters.add("both");
		parameters.add("a1a7");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		dao.updateTable(vo);

		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			public Object answer() {
				ChessStateVO arg1 = (ChessStateVO) EasyMock
						.getCurrentArguments()[0];
				assertEquals("7k/R7/5K2/6R1/8/8/8/8 b - - 1 1", arg1.getFen());
				assertEquals(arg1.getResult().intValue(),
						ResultType.REMIS.getNumber());
				return null;
			}
		});
		EasyMock.replay(dao);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
	}

	@Test
	public void isGameOverCheckMateAITest() throws Throwable {
		List<ChessStateVO> state = new ArrayList<ChessStateVO>();
		ChessStateVO checkMateVO = new ChessStateVO();
		checkMateVO.setId(1);
		checkMateVO
				.setFen("rnbqkbnr/pppp1pp1/7p/4p3/2B1P3/5Q2/PPPP1PPP/RNB1K1NR b KQkq - 0 3");
		checkMateVO.setPlayerName("player1");
		checkMateVO.setResult(null);
		checkMateVO.setDate(null);
		state.add(checkMateVO);
		parameters.add("both");
		parameters.add("a7a5");
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		uci.init();
		EasyMock.expect(
				uci.calculateMove(
						"rnbqkbnr/1ppp1pp1/7p/p3p3/2B1P3/5Q2/PPPP1PPP/RNB1K1NR w KQkq a6 0 4",
						2000)).andReturn("f3f7");
		uci.destroy();
		dao.updateTable(vo);

		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			public Object answer() {
				ChessStateVO arg1 = (ChessStateVO) EasyMock
						.getCurrentArguments()[0];
				assertTrue(arg1.getResult() != null);
				assertEquals(ResultType.WHITE_WINS.getNumber(), arg1
						.getResult().intValue());
				return null;
			}
		});
		EasyMock.replay(dao);
		EasyMock.replay(uci);
		mcc.processInput("player1", parameters);
		EasyMock.verify(dao);
		EasyMock.verify(uci);
	}
	
	@Test (expected = ChessManagerException.class)
	public void updateInDatabaseSQLExceptionTest () throws SQLException, ChessManagerException{
		ChessStateVO vo = new ChessStateVO();
		vo.setId(1);
		dao.updateTable(vo);
		EasyMock.expectLastCall().andThrow(new SQLException());
		EasyMock.replay(dao);
		
		mcc.updateInDatabase(chessState);
		
		EasyMock.verify(dao);
	}
}
