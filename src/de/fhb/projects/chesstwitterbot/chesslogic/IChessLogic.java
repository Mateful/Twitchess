package de.fhb.projects.chesstwitterbot.chesslogic;

import java.util.List;

public interface IChessLogic {
	public boolean isValidMove(AbsoluteMove m);
	public boolean isCheck();
	public boolean isCheckMate();
	public boolean isDraw();
	public List<RelativeMove> generateAllValidMoves();
}
