package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;

public class EnPassant {
	private ChessLogic cl;
	private Pawn blackPawn, initWhitePawn;

	@Before
	public void initPawnTests() {
		cl = new ChessLogic();
		blackPawn = new Pawn(new Position(1, 3), BLACK);
		initWhitePawn = new Pawn(new Position(0, 1), WHITE);
		cl.white.add(initWhitePawn);
		cl.black.add(blackPawn);
		cl.updatePositions();
	}

	@Test
	public void enPassant() {
		cl.lastMove = new AbsoluteMove(initWhitePawn.position, new Position(0,
				3));
		initWhitePawn.position = new Position(0, 3);
		cl.updatePositions();
		cl.currentTurnPlayer = cl.black;
		assertTrue(cl.isValidMove(new AbsoluteMove(blackPawn.position,
				new Position(0, 2))));
	}
}
