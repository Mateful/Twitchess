package de.fhb.projects.Twitchess.controller.chesscommands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import de.fhb.projects.Twitchess.controller.UCIEngineInterface;
import de.fhb.projects.Twitchess.controller.configuration.Configuration;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.data.ResultType;
import de.fhb.projects.Twitchess.exception.ChessManagerException;
import de.fhb.projects.Twitchess.exception.ChessManagerGameAlreadyOverException;
import de.fhb.projects.Twitchess.exception.UCIException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public class MoveChessCommand implements ChessCommand {
	public static String commandText = "move";
	protected ChessStateDAOInterface dao;
	protected UCIEngineInterface uciEngine;

	protected static enum MoveType {
		AI, PLAYER, AI_AFTER_PLAYER;
	}

	public MoveChessCommand(ChessStateDAOInterface dao,
			UCIEngineInterface uciEngine) {
		setDao(dao);
		setUciEngine(uciEngine);
	}

	@Override
	public String processInput(String player, List<String> parameters)
			throws ChessManagerException {
		String result = null;
		MoveType t = null;
		Move playersMove;
		Move aiMove;

		if (parameters == null || parameters.size() < 1
				|| parameters.size() > 2) {
			throwCommandSyntaxException();
		}

		t = parseMoveType(parameters);
		playersMove = parseMove(parameters, t);

		if (t == null && playersMove != null)
			t = MoveType.AI_AFTER_PLAYER;

		ChessStateVO vo = getCurrentGame(player);
		Fen fen = new Fen(vo.getFen());
		GameState state = fen.getGameState();
		
		if (setResultInChessState(vo, state)) {
			updateInDatabase(vo);
			throw new ChessManagerGameAlreadyOverException(
					"Game's already ended!");
		}

		if ((t == MoveType.PLAYER || t == MoveType.AI_AFTER_PLAYER)
				&& playersMove != null) {
			state = doPlayerMove(playersMove, state);
			result = getResultPlayer(vo, state);
		}
		if ((t == MoveType.AI || t == MoveType.AI_AFTER_PLAYER)
				&& vo.getResult() == null) {
			fen = new Fen(state);
			aiMove = doAIMove(fen, state);
			state = new GameState(state, aiMove);
			result = getResultAI(aiMove, vo, fen, state);
		}

		
		fen = new Fen(state);
		vo.setFen(fen.getFen());
		updateInDatabase(vo);

		return result;
	}

	protected String getResultAI(Move aiMove, ChessStateVO vo, Fen fen,
			GameState state) {
		String result;
		result = "Computer move: " + aiMove.getLongNotation();

		if (setResultInChessState(vo, state)) {
			if (vo.getResult() == ResultType.REMIS.getNumber())
				result += " -> Game ends with remis.";
			else
				result += "#";
			result += " {" + fen.getFen() + "}";
		}
		return result;
	}

	protected Move doAIMove(Fen fen, GameState state)
			throws ChessManagerException {
		Move aiMove;
		try {
			aiMove = calculateMove(fen);
		} catch (Throwable e) {
			throw new ChessManagerException(
					"Error while calculating your move: " + e.getMessage());
		}

		try {
			ChessLogic.isValidMove(state, aiMove);
		} catch (RuntimeException e) {
			throw new ChessManagerException("Computer's move is invalid!");
		}
		return aiMove;
	}

	protected String getResultPlayer(ChessStateVO vo, GameState state) {
		String result;
		if (setResultInChessState(vo, state)) {
			if (vo.getResult() == ResultType.REMIS.getNumber())
				result = "Game's remis! Well played.";
			else
				result = "You won the game! Congratulations!";
		} else {
			result = "Your move has been executed.";
		}
		return result;
	}

	protected GameState doPlayerMove(Move playersMove, GameState state)
			throws ChessManagerException {
		try {
			ChessLogic.isValidMove(state, playersMove);
		} catch (RuntimeException e) {
			throw new ChessManagerException("Your move is invalid!");
		}

		state = new GameState(state, playersMove);
		
		return state;
	}

	protected boolean setResultInChessState(ChessStateVO vo, GameState state) {
		if (ChessLogic.isCheckmate(state, state.getCurrentColor())) {
			if (state.getCurrentColor() == Color.BLACK) {
				vo.setResult(ResultType.WHITE_WINS.getNumber());
			} else {
				vo.setResult(ResultType.BLACK_WINS.getNumber());
			}
			return true;
		} else if (ChessLogic.isDraw(state)) {
			vo.setResult(ResultType.REMIS.getNumber());
			return true;
		}

		return false;
	}

	protected void updateInDatabase(ChessStateVO vo)
			throws ChessManagerException {
		try {
			dao.updateTable(vo);
		} catch (SQLException e) {
			throw new ChessManagerException(
					"Error while writing to the database.");
		}
	}

	protected ChessStateVO getCurrentGame(String player)
			throws ChessManagerException {
		List<ChessStateVO> listVO;
		try {
			listVO = dao.findNotFinishedGameByPlayer(player);
		} catch (SQLException e) {
			throw new ChessManagerException(
					"Error while loading from the database.");
		}

		if (listVO == null || listVO.size() != 1) {
			throw new ChessManagerException(
					"You must have a single ongoing game in order to do a move.");
		}
		return listVO.get(0);
	}
	protected Move parseMove(List<String> parameters, MoveType t)
			throws ChessManagerException {
		Move playersMove = null;

		try {
			if (t != MoveType.AI) {
				if (t == null) {
					playersMove = Move.valueOf(parameters.get(0));
				} else if (parameters.size() != 2) {
					throwCommandSyntaxException();
				} else {
					playersMove = Move.valueOf(parameters.get(1));
				}
			}
		} catch (RuntimeException e) {
			throw new ChessManagerException("Your move could not be parsed!");
		}

		return playersMove;
	}

	protected MoveType parseMoveType(List<String> parameters)
			throws ChessManagerException {
		MoveType t = null;
		String p = parameters.get(0);

		if (p.equalsIgnoreCase("ai")) {
			if (parameters.size() != 1)
				throwCommandSyntaxException();
			t = MoveType.AI;
		} else if (p.equalsIgnoreCase("player")) {
			if (parameters.size() != 2)
				throwCommandSyntaxException();
			t = MoveType.PLAYER;
		} else if (p.equalsIgnoreCase("both")) {
			if (parameters.size() != 2)
				throwCommandSyntaxException();
			t = MoveType.AI_AFTER_PLAYER;
		}

		return t;
	}

	protected void throwCommandSyntaxException() throws ChessManagerException {
		throw new ChessManagerException("Error! \"" + commandText
				+ " ([both|player] <move>) | [ai]\" expected");
	}

	protected Move calculateMove(Fen fen) throws IOException, UCIException,
			Throwable, ChessManagerException {
		Move move;
		uciEngine.init();
		String calculatedMove = uciEngine.calculateMove(fen.getFen(), Configuration.getInt("Engine.TimePerMove", 2000));
		uciEngine.destroy();

		try {
			move = Move.valueOf(calculatedMove);
		} catch (RuntimeException e) {
			throw new ChessManagerException("Computer's move could not be parsed");
		}

		return move;
	}

	public ChessStateDAOInterface getDao() {
		return dao;
	}

	public void setDao(ChessStateDAOInterface dao) {
		this.dao = dao;
	}

	public UCIEngineInterface getUciEngine() {
		return uciEngine;
	}

	public void setUciEngine(UCIEngineInterface uciEngine) {
		this.uciEngine = uciEngine;
	}

}
