package de.fhb.projects.chesstwitterbot.chesslogic;

import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;

public class ChessLogic implements IChessLogic {
	public Figure[][] board;
	public Color currentTurnPlayer;

	@Override
	public boolean isValidMove(AbsoluteMove absoluteMove) {
		Figure figure = board[absoluteMove.getStart().getX()][absoluteMove
				.getStart().getY()];

		if(figure == null)
			throw new InvalidMoveException(
					"The move is invalid because there is no figure on the designated position. Your move:"
							+ absoluteMove.toString());

		RelativeMoveList naiveMoves = figure.getNaiveMoves();
		for(RelativeMove rm : naiveMoves)
			System.out.println(rm);
		if(naiveMoves.contains(absoluteMove.getRelativeMove())) {
			if(isMoveBlocked(absoluteMove))
				throw new InvalidMoveException(
						"The move is invalid because there is a figure blocking the way. Your move:"
								+ absoluteMove.toString());
			return true;
		} else
			throw new InvalidMoveException(
					"The move is invalid because this figure can´t make this move. Your move:"
							+ absoluteMove.toString());
	}

	public boolean isMoveBlocked(AbsoluteMove absoluteMove) {
		switch(absoluteMove.getDirection()) {
		case UP:
			for(int y = absoluteMove.getStart().getY() + 1; y <= absoluteMove
					.getDestination().getY(); y++)
				if(board[absoluteMove.getStart().getX()][y] != null)
					return true;
			break;
		case DOWN:
			for(int y = absoluteMove.getStart().getY() - 1; y >= absoluteMove
					.getDestination().getY(); y--)
				if(board[absoluteMove.getStart().getX()][y] != null)
					return true;
			break;
		case RIGHT:
			for(int x = absoluteMove.getStart().getX() + 1; x <= absoluteMove
					.getDestination().getX(); x++)
				if(board[x][absoluteMove.getStart().getY()] != null)
					return true;
		case LEFT:
			for(int x = absoluteMove.getStart().getX() - 1; x >= absoluteMove
					.getDestination().getX(); x--)
				if(board[x][absoluteMove.getStart().getY()] != null)
					return true;
			break;
		case UPRIGHT:
			for(int x = absoluteMove.getStart().getX() + 1, y = absoluteMove
					.getStart().getY() + 1; x <= absoluteMove.getDestination()
					.getX(); x++, y++)
				if(board[x][y] != null)
					return true;
			break;
		case DOWNRIGHT:
			for(int x = absoluteMove.getStart().getX() + 1, y = absoluteMove
					.getStart().getY() - 1; x <= absoluteMove.getDestination()
					.getX(); x++, y--)
				if(board[x][y] != null)
					return true;
			break;
		case DOWNLEFT:
			for(int x = absoluteMove.getStart().getX() - 1, y = absoluteMove
					.getStart().getY() - 1; x >= absoluteMove.getDestination()
					.getX(); x--, y--)
				if(board[x][y] != null)
					return true;
			break;
		case UPLEFT:
			for(int x = absoluteMove.getStart().getX() - 1, y = absoluteMove
					.getStart().getY() + 1; x >= absoluteMove.getDestination()
					.getX(); x--, y++)
				if(board[x][y] != null)
					return true;
			break;
		case KNIGHT:
			if(board[absoluteMove.getDestination().getX()][absoluteMove
					.getDestination().getY()] != null)
				return true;
			break;
		}
		return false;
	}

	@Override
	public boolean isCheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCheckMate() {
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
