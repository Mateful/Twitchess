package de.fhb.projects.chesstwitterbot.chesslogic;

import static de.fhb.projects.chesstwitterbot.chesslogic.figures.NoFigure.NO_FIGURE;

import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.exception.MoveBlockedException;
import de.fhb.projects.chesstwitterbot.exception.NoFigureException;
import de.fhb.projects.chesstwitterbot.exception.WrongColorException;

public final class ChessLogic {
	private static final int WHITE_PAWN_LINE = 1;
	private static final int BLACK_PAWN_LINE = 6;
	/**
	 * Every public method should override this field, to ensure that every
	 * private method called works properly.
	 */
	private static GameState stateInProcess;
	/**
	 * See stateInProcess.
	 */
	private static Move currentMove;
	/**
	 * See stateInProcess.
	 */
	private static Figure figureDoingCurrentMove;

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
			boolean ignoreNotYourTurn) {
		stateInProcess = state;
		currentMove = move;
		figureDoingCurrentMove = state.getMovingFigure(move);

		hasNoFigure();
		if (!ignoreNotYourTurn)
			hasWrongColor();
		hasDirection();
		isBlocked();
		return true;
	}

	private static void isBlocked() {
		if (isMoveBlocked()) {
			throw new MoveBlockedException(
					"The move is invalid because there is a figure blocking the way. Your move:"
							+ currentMove.toString());
		}
	}

	private static void hasDirection() {
		if (!figureDoingCurrentMove.canDoMove(currentMove)) {
			if (!isPawnHit()
					&& !isInitialPawn2Step(currentMove, figureDoingCurrentMove)) {
				throw new FigureCannotMoveIntoDirectionException(
						"The move is invalid because this figure can't make this move. Your move:"
								+ currentMove.toString());
			}
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

	private static void hasWrongColor() {
		if (!figureDoingCurrentMove.getColor().equals(
				stateInProcess.currentTurnPlayer.getColor())) {
			throw new WrongColorException(
					"The move is invalid this is not your figure. Your move:"
							+ currentMove.toString());
		}
	}

	private static void hasNoFigure() {
		if (figureDoingCurrentMove.equals(NO_FIGURE)) {
			throw new NoFigureException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ currentMove.toString());
		}
	}

	private static boolean isPawnHit() {
		return figureDoingCurrentMove instanceof Pawn
				&& ((Pawn) figureDoingCurrentMove).canDoHit(currentMove)
				&& (isDestinationOccupied().equals(
						stateInProcess.currentTurnPlayer.opponent.getColor()) || isEnPassant());
	}

	private static boolean isEnPassant() {
		return isInitialPawn2Step(
				stateInProcess.lastMove,
				stateInProcess.board[stateInProcess.lastMove.getDestination().x][stateInProcess.lastMove
						.getDestination().y]);
	}

	private static boolean isMoveBlocked() {
		if (isDestinationOccupied().equals(
				figureDoingCurrentMove.getColor())) {
			return true;
		}

		if (currentMove.getDirection() instanceof InfiniteDirection) {
			IsMoveBlockedHelper imbh = new IsMoveBlockedHelper(currentMove);
			for (int y = imbh.getyStart(), x = imbh.getxStart(); y != imbh
					.getyDest() || x != imbh.getxDest(); y += imbh.getyToAdd(), x += imbh
					.getxToAdd()) {
				if (!stateInProcess.board[x][y].equals(NO_FIGURE)) {
					return true;
				}
			}
		}
		return false;
	}

	private static Color isDestinationOccupied() {
		return stateInProcess.board[currentMove.getDestination().x][currentMove
				.getDestination().y].getColor();
	}

	public static boolean isCheck(final GameState state,
			final Player playerInCheck) {
		stateInProcess = state;
		Position kingPos = playerInCheck.getKing().getPosition();
		Player opponent = playerInCheck.opponent;
		for (int i = 0; i < opponent.getFiguresInGame().size(); i++) {
			try {
				if (isValidMoveIgnoreNotYourTurn(stateInProcess, new Move(opponent
						.getFiguresInGame().get(i).getPosition(), kingPos))) {
					return true;
				}
			} catch (RuntimeException e) {
				// This move can't be done, thank goodness.
			}
		}
		return false;
	}

	public static boolean isCheckmate(final GameState state,
			final Player playerInCheck) {
		stateInProcess = state;
		if (isCheck(stateInProcess, playerInCheck)) {
			King king = playerInCheck.getKing();
			List<Move> moves = getAllMoves(stateInProcess, king);
			for (int i = 0; i < moves.size(); i++) {
				GameState nextState = new GameState(stateInProcess,
						moves.get(i));
				if (!isCheck(nextState, playerInCheck)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isDraw(final GameState state) {
		stateInProcess = state;
		// TODO Auto-generated method stub
		return false;
	}

	// TODO Sollte vielleicht optimiert werden. Im moment wird einfach jeder Zug
	// auf JEDES FELD geprueft.
	public static List<Move> getAllMoves(GameState state, Figure figure) {
		stateInProcess = state;
		ArrayList<Move> validMoves = new ArrayList<Move>();
		for (int x = 0; x < stateInProcess.board.length; x++) {
			for (int y = 0; y < stateInProcess.board[x].length; y++) {
				try {
					Move move = new Move(figure.getPosition(), new Position(x,
							y));
					if (isValidMoveIgnoreNotYourTurn(stateInProcess, move)) {
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
