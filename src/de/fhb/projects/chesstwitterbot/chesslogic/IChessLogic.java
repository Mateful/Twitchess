package de.fhb.projects.chesstwitterbot.chesslogic;

import java.util.List;

public interface IChessLogic {
	public boolean isValidMove(Move m);
	public boolean isCheck();
	public boolean isCheckMate();
	public boolean isDraw();
	public List<Move> generateAllValidMoves();
}
