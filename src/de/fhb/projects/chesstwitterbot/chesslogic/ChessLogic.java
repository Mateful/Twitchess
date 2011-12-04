package de.fhb.projects.chesstwitterbot.chesslogic;

import static de.fhb.projects.chesstwitterbot.chesslogic.figures.NoFigure.NO_FIGURE;

import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;

public final class ChessLogic {
	private static GameState stateInProcess;

	private ChessLogic() {}

	public static boolean isValidMove(final GameState state,
			final AbsoluteMove absoluteMove) {
		stateInProcess = state;
		Figure figure = state.getMovingFigure(absoluteMove);

		checkNoFigure(absoluteMove, figure);
		checkWrongColor(absoluteMove, figure);
		checkDirection(absoluteMove, figure);
		checkIsBlocked(absoluteMove);
		return true;
	}

	private static void checkIsBlocked(final AbsoluteMove absoluteMove) {
		if(isMoveBlocked(absoluteMove))
			throw new InvalidMoveException(
					"The move is invalid because there is a figure blocking the way. Your move:"
							+ absoluteMove.toString());
	}

	private static void checkDirection(final AbsoluteMove absoluteMove,
			final Figure figure) {
		if(!figure.getNaiveMoves().contains(absoluteMove))
			if(!isPawnHit(absoluteMove, figure)
					&& !isInitialPawn2Step(absoluteMove, figure))
				throw new InvalidMoveException(
						"The move is invalid because this figure can't make this move. Your move:"
								+ absoluteMove.toString());
	}

	private static boolean isInitialPawn2Step(final AbsoluteMove absoluteMove,
			final Figure figure) {
		return figure instanceof Pawn
				&& isPawnInInitialLine((Pawn)figure, absoluteMove)
				&& absoluteMove.getTotalYDistance() == 2;
	}

	private static boolean isPawnInInitialLine(final Pawn pawn,
			final AbsoluteMove absoluteMove) {
		return pawn.color.equals(Color.WHITE) ? absoluteMove.getStart().getY() == 1
				: absoluteMove.getStart().getY() == 6;
	}

	private static void checkWrongColor(final AbsoluteMove absoluteMove,
			final Figure figure) {
		if(!figure.color.equals(stateInProcess.currentTurnPlayer.getColor()))
			throw new InvalidMoveException(
					"The move is invalid this is not your figure. Your move:"
							+ absoluteMove.toString());
	}

	private static void checkNoFigure(final AbsoluteMove absoluteMove,
			final Figure figure) {
		if(figure.equals(NO_FIGURE))
			throw new InvalidMoveException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ absoluteMove.toString());
	}

	private static boolean isPawnHit(final AbsoluteMove absoluteMove,
			final Figure figure) {
		return figure instanceof Pawn
				&& ((Pawn)figure).getHitMoves().contains(absoluteMove)
				&& (isDestinationOccupied(absoluteMove).equals(
						stateInProcess.currentTurnPlayer.opponent.getColor()) || isEnPassant());
	}

	private static boolean isEnPassant() {
		return isInitialPawn2Step(stateInProcess.lastMove,
				stateInProcess.board[stateInProcess.lastMove.getDestination()
						.getX()][stateInProcess.lastMove.getDestination()
						.getY()]);
	}

	private static boolean isMoveBlocked(final AbsoluteMove absoluteMove) {
		if(isDestinationOccupied(absoluteMove).equals(
				stateInProcess.currentTurnPlayer.getColor()))
			return true;

		IsMoveBlockedHelper imbh = new IsMoveBlockedHelper();
		switch(absoluteMove.getDirection()) {
		case UP:
			imbh.setUp(absoluteMove);
			break;
		case DOWN:
			imbh.setDown(absoluteMove);
			break;
		case RIGHT:
			imbh.setRight(absoluteMove);
			break;
		case LEFT:
			imbh.setLeft(absoluteMove);
			break;
		case UPRIGHT:
			imbh.setUp(absoluteMove);
			imbh.setRight(absoluteMove);
			break;
		case DOWNRIGHT:
			imbh.setDown(absoluteMove);
			imbh.setRight(absoluteMove);
			break;
		case DOWNLEFT:
			imbh.setDown(absoluteMove);
			imbh.setLeft(absoluteMove);
			break;
		case UPLEFT:
			imbh.setUp(absoluteMove);
			imbh.setLeft(absoluteMove);
			break;
		case KNIGHT:
			break;
		default:
			throw new RuntimeException("Direction is not included in isMoveBlocked. Did you update the enum and forgot the switch?");
		}

		for(int y = imbh.yStart, x = imbh.xStart; y < imbh.yDest
				|| x < imbh.xDest; y += imbh.yToAdd, x += imbh.xToAdd)
			if(!stateInProcess.board[absoluteMove.getStart().getX()][y]
					.equals(NO_FIGURE))
				return true;

		return false;
	}

	private static Color isDestinationOccupied(final AbsoluteMove absoluteMove) {
		return stateInProcess.board[absoluteMove.getDestination().getX()][absoluteMove
				.getDestination().getY()].color;
	}

	public static boolean isCheck(final GameState state,
			final Player playerInCheck) {
		stateInProcess = state;
		Position kingPos = playerInCheck.getKing();
		Player opponent = playerInCheck.opponent;
		for(int i = 0; i < opponent.getFiguresInGame().size(); i++)
			try {
				if(isValidMove(state, new AbsoluteMove(opponent
						.getFiguresInGame().get(i).position, kingPos)))
					return true;
			} catch(RuntimeException e) {
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

	public static List<RelativeMove> generateAllValidMoves(final GameState state) {
		stateInProcess = state;
		// TODO Auto-generated method stub
		return null;
	}
}
