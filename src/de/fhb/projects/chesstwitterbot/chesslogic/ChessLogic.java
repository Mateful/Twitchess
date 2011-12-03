package de.fhb.projects.chesstwitterbot.chesslogic;

import static de.fhb.projects.chesstwitterbot.chesslogic.figures.NoFigure.NO_FIGURE;

import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Black;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;
import de.fhb.projects.chesstwitterbot.chesslogic.player.White;

public class ChessLogic implements IChessLogic {
	public Figure[][] board;
	public Player currentTurnPlayer;
	public Player white, black;
	public AbsoluteMove lastMove;

	public ChessLogic() {
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

	@Override
	public boolean isValidMove(AbsoluteMove absoluteMove) {
		Figure figure = getFigure(absoluteMove);

		checkNoFigure(absoluteMove, figure);
		checkWrongColor(absoluteMove, figure);
		checkDirection(absoluteMove, figure);
		checkIsBlocked(absoluteMove);
		return true;
	}

	private void checkIsBlocked(AbsoluteMove absoluteMove) {
		if(isMoveBlocked(absoluteMove))
			throw new InvalidMoveException(
					"The move is invalid because there is a figure blocking the way. Your move:"
							+ absoluteMove.toString());
	}

	private void checkDirection(AbsoluteMove absoluteMove, Figure figure) {
		if(!figure.getNaiveMoves().contains(absoluteMove))
			if(!isPawnHit(absoluteMove, figure)
					&& !isInitialPawn2Step(absoluteMove, figure))
				throw new InvalidMoveException(
						"The move is invalid because this figure can´t make this move. Your move:"
								+ absoluteMove.toString());
	}

	private boolean isInitialPawn2Step(AbsoluteMove absoluteMove, Figure figure) {
		return figure instanceof Pawn && isPawnInInitialLine((Pawn)figure, absoluteMove) && absoluteMove.getTotalYDistance() == 2;
	}
	
	private boolean isPawnInInitialLine(Pawn pawn, AbsoluteMove absoluteMove) {
		return pawn.color.equals(Color.WHITE) ? absoluteMove.getStart()
				.getY() == 1 : absoluteMove.getStart().getY() == 6;
	}

	private void checkWrongColor(AbsoluteMove absoluteMove, Figure figure) {
		if(!figure.color.equals(currentTurnPlayer.getColor()))
			throw new InvalidMoveException(
					"The move is invalid this is not your figure. Your move:"
							+ absoluteMove.toString());
	}

	private void checkNoFigure(AbsoluteMove absoluteMove, Figure figure) {
		if(figure.equals(NO_FIGURE))
			throw new InvalidMoveException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ absoluteMove.toString());
	}

	private Figure getFigure(AbsoluteMove absoluteMove) {
		return board[absoluteMove.getStart().getX()][absoluteMove.getStart()
				.getY()];
	}

	private boolean isPawnHit(AbsoluteMove absoluteMove, Figure figure) {
		return figure instanceof Pawn
				&& ((Pawn)figure).getHitMoves().contains(absoluteMove) && (isDestinationOccupied(absoluteMove).equals(currentTurnPlayer.opponent.getColor()) || isEnPassant());
	}

	private boolean isEnPassant() {
		return isInitialPawn2Step(lastMove, board[lastMove.getDestination().getX()][lastMove.getDestination().getY()]);
	}

	public boolean isMoveBlocked(AbsoluteMove absoluteMove) {
		if(isDestinationOccupied(absoluteMove).equals(
				currentTurnPlayer.getColor()))
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
		}

		for(int y = imbh.yStart, x = imbh.xStart; y < imbh.yDest
				|| x < imbh.xDest; y += imbh.yToAdd, x += imbh.xToAdd)
			if(!board[absoluteMove.getStart().getX()][y].equals(NO_FIGURE))
				return true;

		return false;
	}

	private Color isDestinationOccupied(AbsoluteMove absoluteMove) {
		return board[absoluteMove.getDestination().getX()][absoluteMove
				.getDestination().getY()].color;
	}

	@Override
	public boolean isCheck(Player playerInCheck) {
		Position kingPos = playerInCheck.getKing();
		Player opponent = playerInCheck.opponent;
		for(int i = 0; i < opponent.getFiguresInGame().size(); i++)
			try {
				if(isValidMove(new AbsoluteMove(opponent.getFiguresInGame()
						.get(i).position, kingPos)))
					return true;
			} catch(RuntimeException e) {
				// This move can´t be done, thank goodness.
			}
		return false;
	}

	@Override
	public boolean isCheckMate(Player playerInCheck) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDraw() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<RelativeMove> generateAllValidMoves() {
		// TODO Auto-generated method stub
		return null;
	}
}
