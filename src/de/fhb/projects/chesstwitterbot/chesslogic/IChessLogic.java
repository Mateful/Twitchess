package de.fhb.projects.chesstwitterbot.chesslogic;

import java.util.List;

import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Player;

public interface IChessLogic {
	public boolean isValidMove(AbsoluteMove m);
	public boolean isCheck(Player colorInCheck);
	public boolean isCheckMate(Player colorInCheck);
	public boolean isDraw();
	public List<RelativeMove> generateAllValidMoves();
}
