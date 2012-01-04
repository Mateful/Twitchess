package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_HEIGHT;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_WIDTH;
import static de.fhb.projects.Twitchess.games.chess.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public final class GameState {
	private Figure[][] board;
	private Player currentTurnPlayer;
	private Player white, black;
	private Move lastMove;
	private GameState lastState;
	private boolean whiteCastleKingSide, whiteCastleQueenSide,
			blackCastleKingSide, blackCastleQueenSide;

	public GameState(final Player white, final Player black) {
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];
		this.white = white;
		this.black = black;
		currentTurnPlayer = white;
		lastMove = Move.NO_MOVE;
		whiteCastleKingSide = whiteCastleQueenSide = blackCastleKingSide = blackCastleQueenSide = true;
		updatePositions();
	}

	public GameState(final GameState oldState, final Move move) {
		lastState = oldState;
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];

		white = (Player) oldState.white.clone();
		black = (Player) oldState.black.clone();
		updatePositions();

		if (oldState.getCurrentColor() == Color.WHITE) {
			currentTurnPlayer = black;
		} else {
			currentTurnPlayer = white;
		}

		whiteCastleKingSide = lastState.whiteCastleKingSide;
		whiteCastleQueenSide = lastState.whiteCastleQueenSide;
		blackCastleKingSide = lastState.blackCastleKingSide;
		blackCastleQueenSide = lastState.blackCastleQueenSide;

		doMove(move);
	}

	protected void doMove(final Move move) {
		lastMove = move;
		// TODO: ENPASSANT/CASTLING
		Figure f = board[move.getStart().x][move.getStart().y];
		f.setPosition(new Position(move.getDestination().x, move
				.getDestination().y));
		board[move.getStart().x][move.getStart().y] = NoFigure.NO_FIGURE;
		if (move.getHitTarget() != NO_FIGURE) {
			getPlayer(move.getHitTarget().getColor()).removeFigureFromGame(
					move.getHitTarget());
			board[move.getHitTarget().getPosition().x][move.getHitTarget()
					.getPosition().y] = NO_FIGURE;
		}
		board[move.getDestination().x][move.getDestination().y] = f;
		if (move.getPromoteTo() != NO_FIGURE) {
			getPlayer(getFigureAtDestination(move).getColor())
					.removeFigureFromGame(
							board[move.getDestination().x][move
									.getDestination().y]);
			getPlayer(getFigureAtDestination(move).getColor()).add(
					move.getPromoteTo());
			board[move.getDestination().x][move.getDestination().y] = move
					.getPromoteTo();
		}
		if (f instanceof King) {
			if (Position.calculateXDistance(move) == 2) {
				if (move.getDirectionType() == DirectionType.RIGHT) {
					board[f.getPosition().x - 1][f.getPosition().y] = board[CHESSBOARD_WIDTH - 1][f
							.getPosition().y];
					board[CHESSBOARD_WIDTH - 1][f.getPosition().y] = NO_FIGURE;
				} else if (move.getDirectionType() == DirectionType.LEFT) {
					board[f.getPosition().x + 1][f.getPosition().y] = board[0][f
							.getPosition().y];
					board[0][f.getPosition().y] = NO_FIGURE;
				}
			}
			if (f.getColor() == Color.WHITE) {
				whiteCastleKingSide = whiteCastleQueenSide = false;
			} else if (f.getColor() == Color.BLACK) {
				blackCastleKingSide = blackCastleQueenSide = false;
			}
		}
		if (f instanceof Rook) {
			if (f.getColor() == Color.WHITE
					&& move.getStart().equals(
							ChessProperties.WHITE_ROOK_POSITIONS[0])) {
				whiteCastleQueenSide = false;
			} else if (f.getColor() == Color.WHITE
					&& move.getStart().equals(
							ChessProperties.WHITE_ROOK_POSITIONS[1])) {
				whiteCastleKingSide = false;
			} else if (f.getColor() == Color.BLACK
					&& move.getStart().equals(
							ChessProperties.BLACK_ROOK_POSITIONS[0])) {
				blackCastleQueenSide = false;
			} else if (f.getColor() == Color.BLACK
					&& move.getStart().equals(
							ChessProperties.BLACK_ROOK_POSITIONS[1])) {
				blackCastleKingSide = false;
			}
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

	public Player getPlayer(Color color) {
		switch (color) {
			case WHITE :
				return white;
			case BLACK :
				return black;
			default :
				throw new RuntimeException(
						"Can only get player of color black and white.");
		}
	}

	public void setLastMove(Move m) {
		lastMove = m;
	}

	public boolean isWhiteCastleKingSide() {
		return whiteCastleKingSide;
	}

	public boolean isWhiteCastleQueenSide() {
		return whiteCastleQueenSide;
	}

	public boolean isBlackCastleKingSide() {
		return blackCastleKingSide;
	}

	public boolean isBlackCastleQueenSide() {
		return blackCastleQueenSide;
	}

	public void setWhiteCastleKingSide(boolean whiteCastleKingSide) {
		this.whiteCastleKingSide = whiteCastleKingSide;
	}

	public void setWhiteCastleQueenSide(boolean whiteCastleQueenSide) {
		this.whiteCastleQueenSide = whiteCastleQueenSide;
	}

	public void setBlackCastleKingSide(boolean blackCastleKingSide) {
		this.blackCastleKingSide = blackCastleKingSide;
	}

	public void setBlackCastleQueenSide(boolean blackCastleQueenSide) {
		this.blackCastleQueenSide = blackCastleQueenSide;
	}
}
