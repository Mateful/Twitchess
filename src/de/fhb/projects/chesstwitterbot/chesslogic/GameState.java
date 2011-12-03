package de.fhb.projects.chesstwitterbot.chesslogic;

import static de.fhb.projects.chesstwitterbot.chesslogic.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Black;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.chesslogic.player.White;

public class GameState {
	public String playerName;
	public Figure[][] board;
	public Player currentTurnPlayer;
	public Player white, black;
	public AbsoluteMove lastMove;

	public GameState() {
		playerName = "";
		board = new Figure[8][8];
		for(int x = 0; x < 8; x++)
			for(int y = 0; y < 8; y++)
				board[x][y] = NO_FIGURE;
		white = new White();
		black = new Black();
		white.opponent = black;
		black.opponent = white;
		currentTurnPlayer = white;
		lastMove = AbsoluteMove.NO_MOVE;
	}
	
	public GameState(GameState oldState, AbsoluteMove move) {
		throw new RuntimeException("Not yet implemented");
	}

	public void updatePositions() {
		for(int i = 0; i < white.getFiguresInGame().size(); i++)
			board[white.getFiguresInGame().get(i).position.getX()][white
					.getFiguresInGame().get(i).position.getY()] = white
					.getFiguresInGame().get(i);
		for(int i = 0; i < black.getFiguresInGame().size(); i++)
			board[black.getFiguresInGame().get(i).position.getX()][black
					.getFiguresInGame().get(i).position.getY()] = black
					.getFiguresInGame().get(i);
	}

	public Figure getMovingFigure(AbsoluteMove absoluteMove) {
		return board[absoluteMove.getStart().getX()][absoluteMove.getStart()
				.getY()];
	}
}
