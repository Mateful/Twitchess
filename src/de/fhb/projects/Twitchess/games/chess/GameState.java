package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_HEIGHT;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_WIDTH;
import static de.fhb.projects.Twitchess.games.chess.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public final class GameState {
	private String playerName;
	private Figure[][] board;
	private Player currentTurnPlayer;
	private Player white, black;
	private Move lastMove;
	private GameState lastState;

	public GameState(final Player white, final Player black) {
		playerName = "";
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];
		this.white = white;
		this.black = black;
		currentTurnPlayer = white;
		lastMove = Move.NO_MOVE;
		updatePositions();
	}

	public GameState(final GameState oldState, final Move move) {
		lastState = oldState;
		playerName = oldState.playerName;
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];

		white = (Player) oldState.white.clone();
		black = (Player) oldState.black.clone();
		updatePositions();

		if (oldState.getCurrentColor() == Color.WHITE) {
			currentTurnPlayer = black;
		} else {
			currentTurnPlayer = white;
		}

		doMove(move);
	}

	protected void doMove(final Move move) {
		lastMove = move;
		// TODO: ENPASSANT/CASTLING
		if (move.getStart() != move.getDestination()) {
			Figure f = board[move.getStart().x][move.getStart().y];

			f.setPosition(new Position(move.getDestination().x, move
					.getDestination().y));

			board[move.getStart().x][move.getStart().y] = NoFigure.NO_FIGURE;
			board[move.getDestination().x][move.getDestination().y] = f;
		}
	}

	public void updatePositions() {
		clearBoard();

		for (int i = 0; i < white.getFiguresInGame().size(); i++) {
			board[white.getFiguresInGame().get(i).getPosition().x][white
					.getFiguresInGame().get(i).getPosition().y] = white
					.getFiguresInGame().get(i);
		}
		for (int i = 0; i < black.getFiguresInGame().size(); i++) {
			board[black.getFiguresInGame().get(i).getPosition().x][black
					.getFiguresInGame().get(i).getPosition().y] = black
					.getFiguresInGame().get(i);
		}
	}

	protected void clearBoard() {
		for (int x = 0; x < CHESSBOARD_WIDTH; x++) {
			for (int y = 0; y < CHESSBOARD_HEIGHT; y++) {
				board[x][y] = NO_FIGURE;
			}
		}
	}

	public Figure getFigureAtStart(final Move move) {
		return board[move.getStart().x][move.getStart().y];
	}

	public Figure getFigureAtDestination(final Move move) {
		return board[move.getDestination().x][move.getDestination().y];
	}

	public Color getCurrentColor() {
		return currentTurnPlayer.getColor();
	}

	public Player getCurrentPlayer() {
		return currentTurnPlayer;
	}

	public void setCurrentPlayer(final Player currentTurnPlayer) {
		if (currentTurnPlayer == null)
			throw new RuntimeException(
					"in GameState.setCurrentTurnPlayer: CurrentTurnPlayer cannot be null!");
		this.currentTurnPlayer = currentTurnPlayer;
	}

	public Player getOpponent(final Player player) {
		if (player.getColor().equals(Color.WHITE)) {
			return black;
		} else {
			return white;
		}
	}

	public Move getLastMove() {
		return lastMove;
	}

	public Figure getFigure(final int x, final int y) {
		return board[x][y];
	}

	public GameState getLastState() {
		return lastState;
	}
}
