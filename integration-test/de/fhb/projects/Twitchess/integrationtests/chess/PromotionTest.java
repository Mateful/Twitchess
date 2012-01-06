package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.Twitchess.exception.PromoteException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.NoFigure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public class PromotionTest {
	@Test
	public void promoteWhiteToQueen() {
		Fen fen = new Fen("k7/7P/8/8/8/8/8/7K w - - 0 1");
		Position pawn = new Position(7, 6);
		Move move = Move.up(pawn, 1);
		Queen queen = new Queen(new Position(7, 7), Color.WHITE);
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isValidMove(state, move));
		GameState nextState = new GameState(state, move);
		assertEquals(nextState.getFigure(7, 7), queen);
		assertEquals(nextState.getFigure(pawn), NoFigure.NO_FIGURE);
	}

	@Test
	public void promoteWhiteToBishop() {
		Fen fen = new Fen("k7/7P/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 6), 1);
		Bishop bishop = new Bishop(new Position(7, 7));
		move.setPromoteTo(bishop);
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isValidMove(state, move));
		GameState nextState = new GameState(state, move);
		assertEquals(nextState.getFigure(7, 7), bishop);
	}

	@Test(expected = PromoteException.class)
	public void dontPromoteWhiteToKing() {
		Fen fen = new Fen("k7/7P/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 6), 1);
		King king = new King(new Position(7, 7));
		move.setPromoteTo(king);
		GameState state = fen.getGameState();
		ChessLogic.isValidMove(state, move);
	}

	@Test(expected = PromoteException.class)
	public void dontPromoteWhiteToPawn() {
		Fen fen = new Fen("k7/7P/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 6), 1);
		Pawn pawn = new Pawn(new Position(7, 7));
		move.setPromoteTo(pawn);
		GameState state = fen.getGameState();
		ChessLogic.isValidMove(state, move);
	}

	public void promoteToQueenIfNoPromoteToSet() {
		Fen fen = new Fen("k7/7P/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 6), 1);
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isValidMove(state, move));
		GameState nextState = new GameState(state, move);
		assertTrue(nextState.getFigure(7, 7) instanceof Queen);
	}

	@Test
	public void promoteBlackToQueen() {
		Fen fen = new Fen("k7/8/8/8/8/8/p7/7K b - - 0 1");
		Move move = Move.down(new Position(0, 1), 1);
		Queen queen = new Queen(new Position(0, 0));
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isValidMove(state, move));
		GameState nextState = new GameState(state, move);
		assertEquals(nextState.getFigure(0, 0), queen);
	}

	@Test(expected = PromoteException.class)
	public void dontPromoteOtherFigure() {
		Fen fen = new Fen("k7/7R/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 6), 1);
		Queen queen = new Queen(new Position(7, 7));
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		ChessLogic.isValidMove(state, move);
	}

	@Test(expected = PromoteException.class)
	public void dontPromotePawnNotInLastRankWhite() {
		Fen fen = new Fen("k7/8/7P/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 5), 1);
		Queen queen = new Queen(new Position(7, 6));
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		ChessLogic.isValidMove(state, move);
	}

	@Test(expected = PromoteException.class)
	public void dontPromotePawnNotInLastRankBlack() {
		Fen fen = new Fen("k7/8/8/8/8/p7/8/7K b - - 0 1");
		Move move = Move.down(new Position(0, 1), 1);
		Queen queen = new Queen(new Position(0, 0));
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		ChessLogic.isValidMove(state, move);
	}

	@Test
	public void setPromotePosition() {
		Fen fen = new Fen("k7/7P/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.up(new Position(7, 6), 1);
		Queen queen = new Queen(new Position(0, 0));
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isValidMove(state, move));
		GameState nextState = new GameState(state, move);
		nextState.updatePositions();
		assertEquals(new Queen(new Position(7, 7), Color.WHITE),
				nextState.getFigure(7, 7));
	}

	@Test
	public void promoteWhiteToQueenAfterHit() {
		Fen fen = new Fen("k5p1/7P/8/8/8/8/8/7K w - - 0 1");
		Move move = Move.upLeft(new Position(7, 6), 1);
		Queen queen = new Queen(new Position(6, 7));
		move.setPromoteTo(queen);
		GameState state = fen.getGameState();
		assertTrue(ChessLogic.isValidMove(state, move));
		GameState nextState = new GameState(state, move);
		assertEquals(nextState.getFigure(6, 7), queen);
	}
}
