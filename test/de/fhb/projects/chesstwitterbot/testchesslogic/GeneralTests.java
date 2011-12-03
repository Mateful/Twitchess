package de.fhb.projects.chesstwitterbot.testchesslogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.InvalidMoveException;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public class GeneralTests {
	private ChessLogic cl;

	@Before
	public void initGame() {
		cl = new ChessLogic();
		cl.white.add(new King(new Position(0, 0)));
		cl.white.add(new Queen(new Position(1, 0)));
		cl.black.add(new King(new Position(1, 3)));
		cl.updatePositions();
	}

	@Test(expected = InvalidMoveException.class)
	public void isNoFigureToMove() {
		cl.isValidMove(new AbsoluteMove(new Position(0, 4), new Position(0, 5)));
	}
	
	@Test
	public void isInCheck() {
		assertFalse(cl.isCheck(cl.white));
		assertTrue(cl.isCheck(cl.black));
	}
}
