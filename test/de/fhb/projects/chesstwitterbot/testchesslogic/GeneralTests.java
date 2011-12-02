package de.fhb.projects.chesstwitterbot.testchesslogic;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Color;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Bishop;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Figure;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;

public class GeneralTests {
	private ChessLogic cl;

	@Before
	public void initGame() {
		cl = new ChessLogic();
		Figure[][] board = new Figure[8][8];
		cl.board = board;
		cl.currentTurnPlayer = Color.WHITE;
	}

	@Test(expected = InvalidMoveException.class)
	public void isNoFigureToMove() {
		cl.isValidMove(new AbsoluteMove(new Position(0, 4), new Position(0, 5)));
	}
}
