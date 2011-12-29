package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.ChessProperties.BLACK_PAWN_LINE;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_HEIGHT;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_WIDTH;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.WHITE_PAWN_LINE;
import static de.fhb.projects.Twitchess.games.chess.figures.NoFigure.NO_FIGURE;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.Twitchess.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.Twitchess.exception.MoveBlockedException;
import de.fhb.projects.Twitchess.exception.NoFigureException;
import de.fhb.projects.Twitchess.exception.WrongColorException;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public final class ChessLogic {
	/**
	 * Every public method should override this field, to ensure that every
	 * private method called works properly.
	 */
	//private static GameState stateInProcess;
	/**
	 * If needed @see stateInProcess.
	 */
	private static Move currentMove;
	/**
	 * If needed @see stateInProcess.
	 */
	private static Figure figureAtStart;
	private static Figure figureAtDestionation;

	private ChessLogic() {
	}

	public static boolean isValidMove(final GameState state, final Move move) {
		return isValidMove(state, move, false);
	}

	public static boolean isValidMoveIgnoreNotYourTurn(final GameState state,
			final Move move) {
		return isValidMove(state, move, true);
	}

	private static boolean isValidMove(final GameState state, final Move move,
			final boolean ignoreNotYourTurn) {
		currentMove = move;
		figureAtStart = state.getFigureAtStart(move);
		figureAtDestionation = state.getFigureAtDestination(move);

		moveStartHasNoFigure();
		moveDestinationIsBlockedByFigureOfSameColor();
		if (!ignoreNotYourTurn) {
			hasWrongColor(state);
		}
		figureCanDoMove(state);
		isBlocked(state);
		return true;
	}

	private static void figureCanDoMove(final GameState state) {
		if (figureAtDestionation.getColor() == Color.NOCOLOR) {
			figureCanMoveIntoDirection(state);
		} else {
			figureCanDoHit();
		}
	}

	private static void moveDestinationIsBlockedByFigureOfSameColor() {
		if (figureAtDestionation.getColor() == figureAtStart.getColor()) {
			throw new MoveBlockedException(
					"The move is invalid because the destination is occupied by a figure of your own color. Your move:"
							+ currentMove.toString());
		}
	}

	private static void isBlocked(final GameState state) {
		if (isWayBlocked(state)) {
			throw new MoveBlockedException(
					"The move is invalid because there is a figure blocking the way. Your move:"
							+ currentMove.toString());
		}
	}

	private static void figureCanMoveIntoDirection(final GameState state) {
		if (!figureAtStart.canDoMove(currentMove)) {
			if (!isEnPassant(state)
					&& !isInitialPawn2Step(currentMove, figureAtStart) && !isCastling()) {
				throw new FigureCannotMoveIntoDirectionException(
						"The move is invalid because this figure can't make this move. Your move:"
								+ currentMove.toString());
			}
		}
	}

	private static boolean isCastling() {
		// TODO Auto-generated method stub
		return false;
	}

	private static void figureCanDoHit() {
		if (!figureAtStart.canDoHit(currentMove)) {
			throw new FigureCannotMoveIntoDirectionException(
					"The move is invalid because this figure can't make this move. Your move:"
							+ currentMove.toString());
		}
	}

	private static boolean isInitialPawn2Step(final Move move,
			final Figure figure) {
		return figure instanceof Pawn
				&& isPawnInInitialLine((Pawn) figure, move)
				&& Position.calculateYDistance(move.getStart(),
						move.getDestination()) == 2;
	}

	private static boolean isPawnInInitialLine(final Pawn pawn, final Move move) {
		return pawn.getColor().equals(Color.WHITE)
				? move.getStart().getY() == WHITE_PAWN_LINE
				: move.getStart().getY() == BLACK_PAWN_LINE;
	}

	private static void hasWrongColor(GameState state) {
		if (!figureAtStart.getColor().equals(state.getCurrentColor())) {
			throw new WrongColorException(
					"The move is invalid this is not your figure. Your move:"
							+ currentMove.toString());
		}
	}

	private static void moveStartHasNoFigure() {
		if (figureAtStart.equals(NO_FIGURE)) {
			throw new NoFigureException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ currentMove.toString());
		}
	}

	private static boolean isEnPassant(final GameState state) {
		return figureAtStart instanceof Pawn
				&& isInitialPawn2Step(state.getLastMove(),
						state.getFigureAtDestination(state
								.getLastMove()));
	}

	private static boolean isWayBlocked(final GameState state) {
		if (currentMove.getDirection() instanceof InfiniteDirection) {
			IsMoveBlockedHelper imbh = new IsMoveBlockedHelper(currentMove);
			for (int y = imbh.getyStart(), x = imbh.getxStart(); y != imbh
					.getyDest() || x != imbh.getxDest(); y += imbh.getyToAdd(), x += imbh
					.getxToAdd()) {
				if (!state.getFigure(x, y).equals(NO_FIGURE)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isCheck(final GameState state,
			final Player playerInCheck) {
		Position kingPos = playerInCheck.getKing().getPosition();
		Player opponent = state.getOpponent(playerInCheck);
		System.out.println("ISCHECK: " + kingPos + " " + opponent);
		for (int i = 0; i < opponent.getFiguresInGame().size(); i++) {
			try {
				if (isValidMoveIgnoreNotYourTurn(state, new Move(
						opponent.getFiguresInGame().get(i).getPosition(),
						kingPos))) {
					return true;
				}
			} catch (RuntimeException e) {
				System.out.println("ISCHECK_ERROR: " + e.getMessage());
				// This move can't be done, thank goodness.
			}
		}
		return false;
	}

	public static boolean isCheckmate(final GameState state,
			final Player playerInCheck) {
		if (isCheck(state, playerInCheck)) {
			King king = playerInCheck.getKing();
			try {
				List<Move> moves = getAllMoves(state, king);
				for (int i = 0; i < moves.size(); i++) {
					GameState nextState = new GameState(state,
							moves.get(i));
					if (!isCheck(nextState, nextState.getOpponent(nextState.getCurrentPlayer()))) {
						return false;
					}
				}
			} catch (RuntimeException e) {
				// move is invalid: this does not concern us
			}
			return true;
		}
		return false;
	}

	public static boolean isDraw(final GameState state) {
		// TODO Auto-generated method stub
		return false;
	}

	// TODO Sollte vielleicht optimiert werden. Im moment wird einfach jeder Zug
	// auf JEDES FELD geprueft.
	public static List<Move> getAllMoves(final GameState state,
			final Figure figure) {

		ArrayList<Move> validMoves = new ArrayList<Move>();
		for (int x = 0; x < CHESSBOARD_WIDTH; x++) {
			for (int y = 0; y < CHESSBOARD_HEIGHT; y++) {
				try {
					Move move = new Move(figure.getPosition(), new Position(x,
							y));
					if (isValidMoveIgnoreNotYourTurn(state, move)) {
						validMoves.add(move);
					}
				} catch (RuntimeException e) {
					// The move is invalid for whatever reason, don't add to
					// list.
				}
			}
		}
		return validMoves;
	}

	/**
	 * Helper class for the isMoveBlocked-method. Handles positions and
	 * directions.
	 */
	private static class IsMoveBlockedHelper {
		private int yStart, xStart, yDest, xDest, yToAdd, xToAdd;

		public IsMoveBlockedHelper(final Move move) {
			xStart = move.getStart().x;
			yStart = move.getStart().y;
			xDest = move.getDestination().x;
			yDest = move.getDestination().y;

			xToAdd = 0;
			yToAdd = 0;

			setDirection(move);
		}

		private void setDirection(final Move move) {
			switch (move.getDirectionType()) {
				case UP :
					setUp();
					break;
				case DOWN :
					setDown();
					break;
				case RIGHT :
					setRight();
					break;
				case LEFT :
					setLeft();
					break;
				case UPRIGHT :
					setUp();
					setRight();
					break;
				case DOWNRIGHT :
					setDown();
					setRight();
					break;
				case DOWNLEFT :
					setDown();
					setLeft();
					break;
				case UPLEFT :
					setUp();
					setLeft();
					break;
				default :
					throw new RuntimeException(
							"Direction is not included in isMoveBlocked. Did you change the enum and forgot the switch?");
			}
		}

		private void setUp() {
			yStart++;
			yToAdd = 1;
		}

		private void setDown() {
			yStart--;
			yToAdd = -1;
		}

		private void setRight() {
			xStart++;
			xToAdd = 1;
		}

		private void setLeft() {
			xStart--;
			xToAdd = -1;
		}

		private int getyStart() {
			return yStart;
		}

		private int getxStart() {
			return xStart;
		}

		private int getyDest() {
			return yDest;
		}

		private int getxDest() {
			return xDest;
		}

		private int getyToAdd() {
			return yToAdd;
		}

		private int getxToAdd() {
			return xToAdd;
		}
	}
}
