package de.fhb.projects.Twitchess.games.chess;

import static de.fhb.projects.Twitchess.games.chess.ChessProperties.BLACK_PAWN_RANK;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_HEIGHT;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.CHESSBOARD_WIDTH;
import static de.fhb.projects.Twitchess.games.chess.ChessProperties.WHITE_PAWN_RANK;
import static de.fhb.projects.Twitchess.games.chess.figures.NoFigure.NO_FIGURE;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.Twitchess.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.Twitchess.exception.InvalidMoveException;
import de.fhb.projects.Twitchess.exception.MoveBlockedException;
import de.fhb.projects.Twitchess.exception.NoFigureException;
import de.fhb.projects.Twitchess.exception.PromoteException;
import de.fhb.projects.Twitchess.exception.WrongColorException;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.move.DirectionType;
import de.fhb.projects.Twitchess.games.chess.move.InfiniteDirection;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public final class ChessLogic {
	private ChessLogic() {
	}

	public static boolean isValidMove(final GameState state, final Move move) {
		return isValidMove(state, move, false, false);
	}

	public static boolean isValidMoveWithoutCheck(final GameState state,
			final Move move) {
		isValidMove(state, move, false, true);
		return true;
	}

	public static boolean isValidMoveIgnoreNotYourTurn(final GameState state,
			final Move move) {
		return isValidMove(state, move, true, false);
	}

	public static boolean isValidMoveIgnoreNotYourTurnWithoutCheck(
			final GameState state, final Move move) {
		isValidMove(state, move, true, true);
		return true;
	}

	private static boolean isValidMove(final GameState state, final Move move,
			final boolean ignoreNotYourTurn, final boolean withoutCheck) {
		setFigures(state, move);
		isPromotion(state, move);
		moveStartHasNoFigure(move);
		moveDestinationIsBlockedByFigureOfSameColor(move);
		if (!ignoreNotYourTurn) {
			hasWrongColor(state, move);
		}
		figureCanDoMove(state, move);
		isBlocked(state, move);
		if (!withoutCheck)
			isCheckAfterMove(state, move);
		return true;
	}

	private static void setFigures(final GameState state, final Move move) {
		move.setMovingFigure(state.getFigure(move.getStart()));
		move.setHitTarget(state.getFigureAtDestination(move));
	}

	private static void isPromotion(final GameState state, final Move move) {
		setPromotion(move);
		hasInvalidPromoteTo(state, move);
	}

	private static void setPromotion(final Move move) {
		if (move.getMovingFigure() instanceof Pawn
				&& (move.getDestination().y == ChessProperties.CHESSBOARD_BOTTOM_RANK || move.getDestination().y == ChessProperties.CHESSBOARD_TOP_RANK)
				&& move.getPromoteTo() == NO_FIGURE) {
			move.setPromoteTo(new Queen(move.getDestination()));
		}
	}

	private static void hasInvalidPromoteTo(final GameState state,
			final Move move) {
		if (move.getPromoteTo() != NO_FIGURE) {
			checkPromoteToForInvalidFigure(move);
			checkPromotedFigure(move);
			checkPawnRankForPromotion(state, move);
		}
	}

	private static void checkPromoteToForInvalidFigure(final Move move) {
		if (move.getPromoteTo() instanceof King) {
			throw new PromoteException("Tried to promote to King.");
		}
		if (move.getPromoteTo() instanceof Pawn) {
			throw new PromoteException("Tried to promote to Pawn.");
		}
	}

	private static void checkPromotedFigure(final Move move) {
		if (!(move.getMovingFigure() instanceof Pawn)) {
			throw new PromoteException(
					"Tried to promote a figure different from Pawn.");
		}
	}

	private static void checkPawnRankForPromotion(final GameState state, final Move move) {
		if (state.getCurrentColor() == Color.WHITE) {
			if (move.getStart().y != ChessProperties.BLACK_PAWN_RANK) {
				throw new PromoteException(
						"Tried to promote Pawn with wrong position.");
			}
		} else if (state.getCurrentColor() == Color.BLACK) {
			if (move.getStart().y != ChessProperties.WHITE_PAWN_RANK) {
				throw new PromoteException(
						"Tried to promote Pawn with wrong position.");
			}
		}
	}

	private static void isCheckAfterMove(final GameState state, final Move move) {
		GameState nextState = new GameState(state, move);
		if (isCheck(nextState, state.getCurrentColor())) {
			throw new InvalidMoveException(
					"Move cant be done because youre King would end up dead o_O");
		}
	}

	private static void figureCanDoMove(final GameState state, final Move move) {
		if (move.getHitTarget().getColor() == Color.NOCOLOR) {
			figureCanMoveIntoDirection(state, move);
		} else {
			figureCanDoHit(move);
		}
	}

	private static void moveDestinationIsBlockedByFigureOfSameColor(
			final Move move) {
		if (move.getHitTarget().getColor() == move.getMovingFigure().getColor()) {
			throw new MoveBlockedException(
					"The move is invalid because the destination is occupied by a figure of your own color. Your move:"
							+ move.toString());
		}
	}

	private static void isBlocked(final GameState state, final Move move) {
		if (isWayBlocked(state, move)) {
			throw new MoveBlockedException(
					"The move is invalid because there is a figure blocking the way. Your move:"
							+ move.toString());
		}
	}

	private static void figureCanMoveIntoDirection(final GameState state,
			final Move move) {
		if (!move.getMovingFigure().canDoMove(move)) {
			if (!isEnPassant(state, move)
					&& !isInitialPawn2Step(move, move.getMovingFigure())
					&& !isCastling(state, move)) {
				throw new FigureCannotMoveIntoDirectionException(
						"The move is invalid because this figure can't make this move. Your move:"
								+ move.toString());
			}
		}
	}

	private static boolean isCastling(final GameState state, final Move move) {
		if (isCastlingMove(move, move.getMovingFigure())) {
			return checkFieldsForMovement(state, move, move.getMovingFigure());
		}
		return false;
	}

	private static boolean checkFieldsForMovement(final GameState state,
			final Move move, final Figure figure) {
		if (move.getDirectionType() == DirectionType.LEFT
				&& canCastleQueenSide(state, figure)
				&& state.getFigure(1, figure.getPosition().y) == NO_FIGURE) {
			isCheckAfterMove(state, Move.left(move.getStart(), 1));
			return true;
		}
		if (move.getDirectionType() == DirectionType.RIGHT
				&& canCastleKingSide(state, figure)) {
			isCheckAfterMove(state, Move.right(move.getStart(), 1));
			return true;
		}
		return false;
	}

	private static boolean canCastleKingSide(final GameState state,
			final Figure figure) {
		return figure.getColor() == Color.WHITE ? state
				.canWhiteCastleKingSide() : state
				.canBlackCastleKingSide();
	}

	private static boolean canCastleQueenSide(final GameState state,
			final Figure figure) {
		return figure.getColor() == Color.WHITE ? state
				.canWhiteCastleQueenSide() : state
				.canBlackCastleQueenSide();
	}

	private static boolean isCastlingMove(final Move move, Figure figure) {
		return figure instanceof King && Position.calculateXDistance(move) == 2;
	}
	private static void figureCanDoHit(final Move move) {
		if (!move.getMovingFigure().canDoHit(move)) {
			throw new FigureCannotMoveIntoDirectionException(
					"The move is invalid because this figure can't make this move. Your move:"
							+ move.toString());
		}
	}

	private static boolean isInitialPawn2Step(final Move move,
			final Figure figure) {
		return figure instanceof Pawn
				&& isPawnInInitialLine((Pawn) figure, move)
				&& Position.calculateXDistance(move) == 0
				&& Position.calculateYDistance(move) == 2;

	}

	private static boolean isPawnInInitialLine(final Pawn pawn, final Move move) {
		return pawn.getColor().equals(Color.WHITE)
				? move.getStart().getY() == WHITE_PAWN_RANK
				: move.getStart().getY() == BLACK_PAWN_RANK;
	}

	private static void hasWrongColor(final GameState state, final Move move) {
		if (!move.getMovingFigure().getColor().equals(state.getCurrentColor())) {
			throw new WrongColorException(
					"The move is invalid this is not your figure. Your move:"
							+ move.toString());
		}
	}

	private static void moveStartHasNoFigure(final Move move) {
		if (move.getMovingFigure().equals(NO_FIGURE)) {
			throw new NoFigureException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ move.toString());
		}
	}

	private static boolean isEnPassant(final GameState state, final Move move) {
		if (move.getMovingFigure() instanceof Pawn
				&& isInitialPawn2Step(state.getLastMove(),
						state.getFigureAtDestination(state.getLastMove()))) {
			move.setHitTarget(state.getFigureAtDestination(state.getLastMove()));
			return true;
		}
		return false;
	}

	private static boolean isWayBlocked(final GameState state, final Move move) {
		if (move.getDirection() instanceof InfiniteDirection) {
			IsMoveBlockedHelper imbh = new IsMoveBlockedHelper(move);
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
			final Color playerInCheck) {
		Player currentTurnPlayer = state.getPlayer(playerInCheck);
		try {
			Position kingPos = currentTurnPlayer.getKing().getPosition();
			Player opponent = state.getOpponent(currentTurnPlayer);
			for (int i = 0; i < opponent.getFiguresInGame().size(); i++) {
				try {
					if (isValidMoveIgnoreNotYourTurnWithoutCheck(state,
							new Move(opponent.getFiguresInGame().get(i)
									.getPosition(), kingPos))) {
						return true;
					}
				} catch (RuntimeException e) {
					// This move can't be done, thank goodness.
				}
			}
			return false;
		} catch (RuntimeException e) {
			return false;
		}
	}

	public static boolean isCheckmate(final GameState state,
			final Color playerInCheck) {
		Player currentTurnPlayer = state.getPlayer(playerInCheck);
		if (isCheck(state, playerInCheck)) {
			for (int i = 0; i < currentTurnPlayer.getFiguresInGame().size(); i++) {
				Figure figure = currentTurnPlayer.getFiguresInGame().get(i);
				try {
					List<Move> moves = getAllMoves(state, figure);
					for (int j = 0; j < moves.size(); j++) {
						GameState nextState = new GameState(state, moves.get(j));
						if (!isCheck(nextState, nextState.getCurrentColor()
								.getInverse())) {
							return false;
						}
					}
				} catch (RuntimeException e) {
					// move is invalid: this does not concern us
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isDraw(final GameState state) {
		List<Move> allMoves = new ArrayList<Move>();
		for (int i = 0; i < state.getCurrentPlayer().getFiguresInGame().size(); i++) {
			allMoves.addAll(getAllMoves(state, state.getCurrentPlayer()
					.getFiguresInGame().get(i)));
		}
		if (allMoves.size() == 0)
			return true;
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
