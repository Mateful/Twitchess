package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.Twitchess.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.Twitchess.exception.InvalidMoveException;
import de.fhb.projects.Twitchess.exception.MoveBlockedException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class CastlingTest {
	@Test
	public void whiteCastlingKingSide() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move castleMove = Move.right(new Position(4, 0), 2);
		GameState castleMoveDone = new GameState(state, castleMove);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
		assertTrue(castleMoveDone.getFigure(6, 0) instanceof King);
		assertTrue(castleMoveDone.getFigure(5, 0) instanceof Rook);
		assertFalse(castleMoveDone.canWhiteCastleKingSide());
		assertFalse(castleMoveDone.canWhiteCastleQueenSide());
	}

	@Test
	public void whiteCastlingQueenSide() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move castleMove = Move.left(new Position(4, 0), 2);
		GameState castleMoveDone = new GameState(state, castleMove);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
		assertTrue(castleMoveDone.getFigure(2, 0) instanceof King);
		assertTrue(castleMoveDone.getFigure(3, 0) instanceof Rook);
		assertFalse(castleMoveDone.canWhiteCastleKingSide());
		assertFalse(castleMoveDone.canWhiteCastleQueenSide());
	}

	@Test
	public void blackCastlingKingSide() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1");
		GameState state = fen.createGameState();
		Move castleMove = Move.right(new Position(4, 7), 2);
		GameState castleMoveDone = new GameState(state, castleMove);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
		assertTrue(castleMoveDone.getFigure(6, 7) instanceof King);
		assertTrue(castleMoveDone.getFigure(5, 7) instanceof Rook);
		assertFalse(castleMoveDone.canBlackCastleKingSide());
		assertFalse(castleMoveDone.canBlackCastleQueenSide());
	}

	@Test
	public void blackCastlingQueenSide() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R b KQkq - 0 1");
		GameState state = fen.createGameState();
		Move castleMove = Move.left(new Position(4, 7), 2);
		GameState castleMoveDone = new GameState(state, castleMove);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
		assertTrue(castleMoveDone.getFigure(2, 7) instanceof King);
		assertTrue(castleMoveDone.getFigure(3, 7) instanceof Rook);
		assertFalse(castleMoveDone.canBlackCastleKingSide());
		assertFalse(castleMoveDone.canBlackCastleQueenSide());
	}

	@Test(expected = MoveBlockedException.class)
	public void whiteCastlingKingSideBlockedByKnight() {
		GameState state = new Fen("r3k2r/8/8/8/8/8/8/R3K1NR w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = MoveBlockedException.class)
	public void whiteCastlingKingSideBlockedByBishop() {
		GameState state = new Fen("r3k2r/8/8/8/8/8/8/R3KB1R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteCastlingQueenSideBlockedByKnight() {
		GameState state = new Fen("r3k2r/8/8/8/8/8/8/RN2K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void blackCastlingQueenSideBlockedByKnight() {
		GameState state = new Fen("rn2k2r/8/8/8/8/8/8/RN2K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 7), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteCastlingKingSideBlockedByCheck1() {
		GameState state = new Fen("r3k2r/8/8/6r1/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteCastlingKingSideBlockedByCheck2() {
		GameState state = new Fen("r3k2r/8/8/5r2/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test
	public void whiteCastlingKingSideNotBlockedByCheck() {
		GameState state = new Fen("r3k2r/8/8/7r/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 0), 2);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteCastlingQueenSideBlockedByCheck1() {
		GameState state = new Fen("r3k2r/8/8/3r4/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = InvalidMoveException.class)
	public void whiteCastlingQueenSideBlockedByCheck2() {
		GameState state = new Fen("r3k2r/8/8/2r5/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 0), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test
	public void whiteCastlingQueenSideNotBlockedByCheck1() {
		GameState state = new Fen("r3k2r/8/8/r7/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 0), 2);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
	}

	@Test
	public void whiteCastlingQueenSideNotBlockedByCheck2() {
		GameState state = new Fen("r3k2r/8/8/1r6/8/8/8/R3K2R w KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 0), 2);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
	}

	@Test(expected = InvalidMoveException.class)
	public void blackCastlingKingSideBlockedByCheck1() {
		GameState state = new Fen("r3k2r/8/8/6R1/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 7), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = InvalidMoveException.class)
	public void blackCastlingKingSideBlockedByCheck2() {
		GameState state = new Fen("r3k2r/8/8/5R2/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 7), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test
	public void blackCastlingKingSideNotBlockedByCheck() {
		GameState state = new Fen("r3k2r/8/8/7R/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.right(new Position(4, 7), 2);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
	}

	@Test(expected = InvalidMoveException.class)
	public void blackCastlingQueenSideBlockedByCheck1() {
		GameState state = new Fen("r3k2r/8/8/3R4/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 7), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test(expected = InvalidMoveException.class)
	public void blackCastlingQueenSideBlockedByCheck2() {
		GameState state = new Fen("r3k2r/8/8/2R5/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 7), 2);
		ChessLogic.isValidMove(state, castleMove);
	}

	@Test
	public void blackCastlingQueenSideNotBlockedByCheck1() {
		GameState state = new Fen("r3k2r/8/8/R7/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 7), 2);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
	}

	@Test
	public void blackCastlingQueenSideNotBlockedByCheck2() {
		GameState state = new Fen("r3k2r/8/8/1R6/8/8/8/R3K2R b KQkq - 0 1")
				.createGameState();
		Move castleMove = Move.left(new Position(4, 7), 2);
		assertTrue(ChessLogic.isValidMove(state, castleMove));
	}

	@Test
	public void whiteCastlingBlockedBecauseKingMoved() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move kingMoveLeft = Move.left(new Position(4, 0), 1);
		GameState kingMovesLeft = new GameState(state, kingMoveLeft);
		assertFalse(kingMovesLeft.canWhiteCastleKingSide());
		assertFalse(kingMovesLeft.canWhiteCastleQueenSide());
	}

	@Test
	public void whiteCastlingKingSideBlockedBecauseRookMoved() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move rookMoveLeft = Move.left(new Position(7, 0), 1);
		GameState rookMovesLeft = new GameState(state, rookMoveLeft);
		assertFalse(rookMovesLeft.canWhiteCastleKingSide());
		assertTrue(rookMovesLeft.canWhiteCastleQueenSide());
	}

	@Test
	public void whiteCastlingQueenSideBlockedBecauseRookMoved() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move rookMoveRight = Move.right(new Position(0, 0), 1);
		GameState rookMovesLeft = new GameState(state, rookMoveRight);
		assertTrue(rookMovesLeft.canWhiteCastleKingSide());
		assertFalse(rookMovesLeft.canWhiteCastleQueenSide());
	}

	@Test
	public void blackCastlingBlockedBecauseKingMoved() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move kingMoveLeft = Move.left(new Position(4, 7), 1);
		GameState kingMovesLeft = new GameState(state, kingMoveLeft);
		assertFalse(kingMovesLeft.canBlackCastleKingSide());
		assertFalse(kingMovesLeft.canBlackCastleQueenSide());
	}

	@Test
	public void blackCastlingKingSideBlockedBecauseRookMoved() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move rookMoveLeft = Move.left(new Position(7, 7), 1);
		GameState rookMovesLeft = new GameState(state, rookMoveLeft);
		assertFalse(rookMovesLeft.canBlackCastleKingSide());
		assertTrue(rookMovesLeft.canBlackCastleQueenSide());
	}

	@Test
	public void blackCastlingQueenSideBlockedBecauseRookMoved() {
		Fen fen = new Fen("r3k2r/8/8/8/8/8/8/R3K2R w KQkq - 0 1");
		GameState state = fen.createGameState();
		Move rookMoveRight = Move.right(new Position(0, 7), 1);
		GameState rookMovesLeft = new GameState(state, rookMoveRight);
		assertTrue(rookMovesLeft.canBlackCastleKingSide());
		assertFalse(rookMovesLeft.canBlackCastleQueenSide());
	}

	@Test
	public void castlingStillBlockedAfterKingMovesBackWhite() {
		Fen fen = new Fen("8/8/8/8/8/8/8/R2K3R w - - 0 1");
		GameState state = fen.createGameState();
		Move kingMovesBack = Move.right(new Position(3, 0), 1);
		assertTrue(ChessLogic.isValidMove(state, kingMovesBack));
		GameState kingBackToStart = new GameState(state, kingMovesBack);
		assertFalse(kingBackToStart.canWhiteCastleKingSide());
		assertFalse(kingBackToStart.canWhiteCastleQueenSide());
	}

	@Test
	public void kingSideCastlingStillBlockedAfterRookMovesBackWhite() {
		Fen fen = new Fen("8/8/8/8/8/8/8/R3K1R1 w Q - 0 1");
		GameState state = fen.createGameState();
		Move rookMovesBack = Move.right(new Position(6, 0), 1);
		assertTrue(ChessLogic.isValidMove(state, rookMovesBack));
		GameState rookBackToStart = new GameState(state, rookMovesBack);
		assertFalse(rookBackToStart.canWhiteCastleKingSide());
	}

	@Test
	public void queenSideCastlingStillBlockedAfterRookMovesBackWhite() {
		Fen fen = new Fen("8/8/8/8/8/8/8/1R2K2R w K - 0 1");
		GameState state = fen.createGameState();
		Move rookMovesBack = Move.left(new Position(1, 0), 1);
		assertTrue(ChessLogic.isValidMove(state, rookMovesBack));
		GameState rookBackToStart = new GameState(state, rookMovesBack);
		assertFalse(rookBackToStart.canWhiteCastleQueenSide());
	}

	@Test
	public void castlingStillBlockedAfterKingMovesBackBlack() {
		Fen fen = new Fen("r2k3r/8/8/8/8/8/8/8 b - - 0 1");
		GameState state = fen.createGameState();
		Move kingMovesBack = Move.right(new Position(3, 7), 1);
		assertTrue(ChessLogic.isValidMove(state, kingMovesBack));
		GameState kingBackToStart = new GameState(state, kingMovesBack);
		assertFalse(kingBackToStart.canBlackCastleKingSide());
		assertFalse(kingBackToStart.canBlackCastleQueenSide());
	}

	@Test
	public void kingSideCastlingStillBlockedAfterRookMovesBackBlack() {
		Fen fen = new Fen("r2k2r1/8/8/8/8/8/8/8 b - - 0 1");
		GameState state = fen.createGameState();
		Move rookMovesBack = Move.right(new Position(6, 7), 1);
		assertTrue(ChessLogic.isValidMove(state, rookMovesBack));
		GameState rookBackToStart = new GameState(state, rookMovesBack);
		assertFalse(rookBackToStart.canBlackCastleKingSide());
	}

	@Test
	public void queenSideCastlingStillBlockedAfterRookMovesBackBlack() {
		Fen fen = new Fen("1r1k3r/8/8/8/8/8/8/8 b - - 0 1");
		GameState state = fen.createGameState();
		Move rookMovesBack = Move.left(new Position(1, 7), 1);
		assertTrue(ChessLogic.isValidMove(state, rookMovesBack));
		GameState rookBackToStart = new GameState(state, rookMovesBack);
		assertFalse(rookBackToStart.canBlackCastleQueenSide());
	}
}
