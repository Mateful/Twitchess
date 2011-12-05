package de.fhb.projects.chesstwitterbot.chesslogic;

import static de.fhb.projects.chesstwitterbot.chesslogic.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.InfiniteDirection;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public final class ChessLogic {
	private static GameState stateInProcess;

	private ChessLogic() {
	}

	public static boolean isValidMove(final GameState state, final Move move) {
		stateInProcess = state;
		Figure figure = state.getMovingFigure(move);

		hasNoFigure(move, figure);
		hasWrongColor(move, figure);
		hasDirection(move, figure);
		isBlocked(move);
		return true;
	}

	private static void isBlocked(final Move move) {
		if (isMoveBlocked(move))
			throw new InvalidMoveException(
					"The move is invalid because there is a figure blocking the way. Your move:"
							+ move.toString());
	}

	private static void hasDirection(final Move move, final Figure figure) {
		if (!figure.canDoMove(move))
			if (!isPawnHit(move, figure) && !isInitialPawn2Step(move, figure))
				throw new InvalidMoveException(
						"The move is invalid because this figure can't make this move. Your move:"
								+ move.toString());
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
				? move.getStart().getY() == 1
				: move.getStart().getY() == 6;
	}

	private static void hasWrongColor(final Move move, final Figure figure) {
		if (!figure.getColor().equals(
				stateInProcess.currentTurnPlayer.getColor()))
			throw new InvalidMoveException(
					"The move is invalid this is not your figure. Your move:"
							+ move.toString());
	}

	private static void hasNoFigure(final Move move, final Figure figure) {
		if (figure.equals(NO_FIGURE))
			throw new InvalidMoveException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ move.toString());
	}

	private static boolean isPawnHit(final Move move, final Figure figure) {
		return figure instanceof Pawn
				&& ((Pawn) figure).canDoHit(move)
				&& (isDestinationOccupied(move).equals(
						stateInProcess.currentTurnPlayer.opponent.getColor()) || isEnPassant());
	}

	private static boolean isEnPassant() {
		return isInitialPawn2Step(stateInProcess.lastMove,
				stateInProcess.board[stateInProcess.lastMove.getDestination()
						.getX()][stateInProcess.lastMove.getDestination()
						.getY()]);
	}

	private static boolean isMoveBlocked(final Move move) {
		if (isDestinationOccupied(move).equals(
				stateInProcess.currentTurnPlayer.getColor()))
			return true;

		if (move.getDirection() instanceof InfiniteDirection) {
			IsMoveBlockedHelper imbh = new IsMoveBlockedHelper(move);
			for (int y = imbh.getyStart(), x = imbh.getxStart(); y != imbh
					.getyDest() || x != imbh.getxDest(); y += imbh.getyToAdd(), x += imbh
					.getxToAdd()) {
				if (!stateInProcess.board[x][y].equals(NO_FIGURE))
					return true;
			}
		}
		return false;
	}

	private static Color isDestinationOccupied(final Move move) {
		return stateInProcess.board[move.getDestination().getX()][move
				.getDestination().getY()].getColor();
	}

	public static boolean isCheck(final GameState state,
			final Player playerInCheck) {
		stateInProcess = state;
		Position kingPos = playerInCheck.getKing();
		Player opponent = playerInCheck.opponent;
		for (int i = 0; i < opponent.getFiguresInGame().size(); i++)
			try {
				if (isValidMove(state, new Move(opponent.getFiguresInGame()
						.get(i).getPosition(), kingPos)))
					return true;
			} catch (RuntimeException e) {
				// This move can't be done, thank goodness.
			}
		return false;
	}

	public static boolean isCheckMate(final GameState state,
			final Player playerInCheck) {
		stateInProcess = state;
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isDraw(final GameState state) {
		stateInProcess = state;
		// TODO Auto-generated method stub
		return false;
	}

	private static class IsMoveBlockedHelper {
		private int yStart, xStart, yDest, xDest, yToAdd, xToAdd;

		public IsMoveBlockedHelper(Move move) {
			xStart = move.getStart().getX();
			yStart = move.getStart().getY();
			xDest = move.getDestination().getX();
			yDest = move.getDestination().getY();

			xToAdd = 0;
			yToAdd = 0;

			setDirection(move);
		}

		private void setDirection(Move move) {
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
