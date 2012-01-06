package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_HEIGHT;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_WIDTH;
import static de.fhb.projects.Twitchess.games.chess.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

/**
 * This class provides every information of one state in a standard game of
 * chess (with one exception: it does not provide the en passant field. The en
 * passant rule is implemented here: @see {@link ChessLogic}).
 */
public final class GameState {
	private Figure[][] board;
	private Player currentTurnPlayer;
	private Player white, black;
	private Move lastMove;
	private GameState lastState;
	private boolean whiteCastleKingSide, whiteCastleQueenSide,
			blackCastleKingSide, blackCastleQueenSide;
	private int halfMoveClock, fullMoveNumber;

	public GameState(final Player white, final Player black) {
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];
		this.white = white;
		this.black = black;
		currentTurnPlayer = white;
		lastMove = Move.NO_MOVE;
		whiteCastleKingSide = whiteCastleQueenSide = blackCastleKingSide = blackCastleQueenSide = true;
		fullMoveNumber = 1;
		halfMoveClock = 0;
		updatePositions();
	}

	public GameState(final GameState oldState, final Move move) {
		initAttributesFromLastState(oldState, move);
		updatePositions();
		doMove();
		updateCastlingPossibilities();
	}

	private void initAttributesFromLastState(final GameState oldState,
			final Move move) {
		lastState = oldState;
		lastMove = move;
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];
		white = (Player) oldState.white.clone();
		black = (Player) oldState.black.clone();
		if (oldState.getCurrentColor() == Color.WHITE) {
			currentTurnPlayer = black;
		} else {
			currentTurnPlayer = white;
		}
		halfMoveClock = move.getMovingFigure() instanceof Pawn
				|| move.getHitTarget() != NO_FIGURE
				? 0
				: lastState.halfMoveClock + 1;
		fullMoveNumber = lastState.fullMoveNumber
				+ (getCurrentColor() == Color.WHITE ? 1 : 0);
	}

	private void updateCastlingPossibilities() {
		whiteCastleKingSide = lastState.whiteCastleKingSide
				&& kingAtInitialPosition(Color.WHITE)
				&& rightRookAtInitialPosition(Color.WHITE);
		whiteCastleQueenSide = lastState.whiteCastleQueenSide
				&& kingAtInitialPosition(Color.WHITE)
				&& leftRookAtInitialPosition(Color.WHITE);
		blackCastleKingSide = lastState.blackCastleKingSide
				&& kingAtInitialPosition(Color.BLACK)
				&& rightRookAtInitialPosition(Color.BLACK);
		blackCastleQueenSide = lastState.blackCastleQueenSide
				&& kingAtInitialPosition(Color.BLACK)
				&& leftRookAtInitialPosition(Color.BLACK);
	}

	private boolean leftRookAtInitialPosition(final Color color) {
		Position leftRookPosition = ChessProperties.getLeftRookPosition(color);
		return getFigure(leftRookPosition).equals(
				new Rook(leftRookPosition, color));
	}

	private boolean rightRookAtInitialPosition(final Color color) {
		Position rightRookPosition = ChessProperties
				.getRightRookPosition(color);
		return getFigure(rightRookPosition).equals(
				new Rook(rightRookPosition, color));
	}

	private boolean kingAtInitialPosition(final Color color) {
		Position kingPosition = ChessProperties.getKingPosition(color);
		return getFigure(kingPosition).equals(new King(kingPosition, color));
	}

	protected void doMove() {
		Figure movingFigure = getFigure(lastMove.getStart());
		setNoFigure(lastMove.getStart());
		hitFigure();
		movingFigure = promote(movingFigure);
		setPosition(movingFigure);
		doCastling(movingFigure);
	}

	private void setPosition(Figure movingFigure) {
		setFigure(lastMove.getDestination(), movingFigure);
		movingFigure.setPosition(lastMove.getDestination());
	}

	private void setFigure(final Position position, final Figure figure) {
		board[position.x][position.y] = figure;
	}

	private void doCastling(Figure movingFigure) {
		if (movingFigure instanceof King
				&& Position.calculateXDistance(lastMove) == 2) {
			if (lastMove.getDirectionType() == DirectionType.RIGHT) {
				board[movingFigure.getPosition().x - 1][movingFigure
						.getPosition().y] = board[CHESSBOARD_WIDTH - 1][movingFigure
						.getPosition().y];
				board[CHESSBOARD_WIDTH - 1][movingFigure.getPosition().y] = NO_FIGURE;
			} else if (lastMove.getDirectionType() == DirectionType.LEFT) {
				board[movingFigure.getPosition().x + 1][movingFigure
						.getPosition().y] = board[0][movingFigure.getPosition().y];
				board[0][movingFigure.getPosition().y] = NO_FIGURE;
			}
		}
	}

	private Figure promote(Figure movingFigure) {
		if (lastMove.getPromoteTo() != NO_FIGURE) {
			currentTurnPlayer.removeFigureFromGame(movingFigure);
			getOpponent(currentTurnPlayer).add(lastMove.getPromoteTo());
			movingFigure = lastMove.getPromoteTo();
		}
		return movingFigure;
	}

	private void hitFigure() {
		if (lastMove.getHitTarget() != NO_FIGURE) {
			currentTurnPlayer.removeFigureFromGame(lastMove.getHitTarget());
			setNoFigure(lastMove.getHitTarget().getPosition());
		}
	}

	private void setNoFigure(final Position position) {
		board[position.x][position.y] = NoFigure.NO_FIGURE;
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

	public Figure getFigure(final Position position) {
		return getFigure(position.x, position.y);
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

	public boolean canWhiteCastleKingSide() {
		return whiteCastleKingSide;
	}

	public boolean canWhiteCastleQueenSide() {
		return whiteCastleQueenSide;
	}

	public boolean canBlackCastleKingSide() {
		return blackCastleKingSide;
	}

	public boolean canBlackCastleQueenSide() {
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

	public int getHalfMoveClock() {
		return halfMoveClock;
	}

	public void setHalfMoveClock(int halfMoveClock) {
		this.halfMoveClock = halfMoveClock;
	}

	public int getFullMoveNumber() {
		return fullMoveNumber;
	}

	public void setFullMoveNumber(int fullMoveNumber) {
		this.fullMoveNumber = fullMoveNumber;
	}

	public void setCurrentColor(Color color) {
		if (color == Color.WHITE) {
			currentTurnPlayer = white;
		} else if (color == Color.BLACK) {
			currentTurnPlayer = black;
		} else {
			throw new RuntimeException(
					"Can only set current color to white or black.");
		}
	}

	public boolean hasSamePositioningAndColor(final GameState state) {
		return hasSamePositioning(state) && hasSameCurrentTurnColor(state);
	}

	private boolean hasSameCurrentTurnColor(final GameState state) {
		return this.getCurrentColor() == state.getCurrentColor();
	}

	private boolean hasSamePositioning(final GameState state) {
		if (this.board.length != state.board.length
				|| this.board[0].length != state.board[0].length)
			return false;
		for (int x = 0; x < this.board.length; x++) {
			for (int y = 0; y < this.board[x].length; y++) {
				if (!this.getFigure(x, y).equals(state.getFigure(x, y))) {
					return false;
				}
			}
		}
		return true;
	}
}
