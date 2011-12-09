package de.fhb.projects.chesstwitterbot.chesslogic;

import static de.fhb.projects.chesstwitterbot.chesslogic.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;

public class GameState {
	private String playerName;
	private Figure[][] board;
	private Player currentTurnPlayer;
	private Player white, black;
	private Move lastMove;
	private GameState lastState;

	public GameState(Player white, Player black) {
		playerName = "";
		board = new Figure[8][8];
		for (int x = 0; x < 8; x++)
			for (int y = 0; y < 8; y++)
				board[x][y] = NO_FIGURE;
		this.white = white;
		this.black = black;
		white.opponent = black;
		black.opponent = white;
		currentTurnPlayer = white;
		lastMove = Move.NO_MOVE;
		updatePositions();
	}

	public GameState(GameState oldState, Move move) {
		lastState = oldState;
		playerName = oldState.playerName;
		board = oldState.board.clone();
		currentTurnPlayer = oldState.currentTurnPlayer.opponent;
		white = oldState.white;
		black = oldState.black;
		lastMove = move;
		board[move.getStart().x][move.getStart().y].setPosition(new Position(
				move.getDestination().x, move.getDestination().y));
		updatePositions();
	}

	public void updatePositions() {
		for (int i = 0; i < white.getFiguresInGame().size(); i++)
			board[white.getFiguresInGame().get(i).getPosition().x][white
					.getFiguresInGame().get(i).getPosition().y] = white
					.getFiguresInGame().get(i);
		for (int i = 0; i < black.getFiguresInGame().size(); i++)
			board[black.getFiguresInGame().get(i).getPosition().x][black
					.getFiguresInGame().get(i).getPosition().y] = black
					.getFiguresInGame().get(i);
	}

	public Figure getMovingFigure(Move absoluteMove) {
		return board[absoluteMove.getStart().x][absoluteMove.getStart().y];
	}

	public Color getCurrentColor() {
		return currentTurnPlayer.getColor();
	}

	public Player getCurrentPlayer() {
		return currentTurnPlayer;
	}

	public Move getLastMove() {
		return lastMove;
	}

	public Figure getFigure(int x, int y) {
		return board[x][y];
	}

	public int getX() {
		return board.length;
	}

	public int getY() {
		return board[0].length;
	}

	public GameState getLastState() {
		return lastState;
	}
}
