package de.fhb.projects.chesstwitterbot.testchesslogic.figureMoves;

import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.BLACK;
import static de.fhb.projects.chesstwitterbot.chesslogic.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessLogic;
import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;

public class EnPassant {
	private GameState state;
	private Pawn blackPawn, whitePawn;

	@Before
	public void initPawnTests() {
		state = new GameState();
	}

	@Test
	public void enPassantBlackHitsWhite() {
		blackPawn = new Pawn(new Position(1, 3), BLACK);
		whitePawn = new Pawn(new Position(0, 1), WHITE);
		state.white.add(whitePawn);
		state.black.add(blackPawn);
		state.updatePositions();
		
		state.lastMove = new AbsoluteMove(whitePawn.position, new Position(0,
				3));
		whitePawn.position = new Position(0, 3);
		state.updatePositions();
		state.currentTurnPlayer = state.black;
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(blackPawn.position,
				new Position(0, 2))));
	}
	
	@Test
	public void enPassantWhiteHitsBlack() {
		blackPawn = new Pawn(new Position(3, 6), BLACK);
		whitePawn = new Pawn(new Position(2, 4), WHITE);
		state.white.add(whitePawn);
		state.black.add(blackPawn);
		state.updatePositions();
		
		state.lastMove = new AbsoluteMove(blackPawn.position, new Position(3,
				4));
		blackPawn.position = new Position(3, 4);
		state.updatePositions();
		
		state.currentTurnPlayer = state.white;
		assertTrue(ChessLogic.isValidMove(state, new AbsoluteMove(whitePawn.position,
				new Position(3, 5))));
	}
}
