package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class EnPassantTest {
	private GameState state;
	private Player white, black;
	private Pawn blackPawn, whitePawn;

	@Before
	public void init() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		state = new GameState(white, black);
	}

	@Test
	public void enPassantBlackHitsWhite() {
		blackPawn = new Pawn(new Position(1, 3), BLACK);
		whitePawn = new Pawn(new Position(0, 1), WHITE);
		white.add(whitePawn);
		black.add(blackPawn);
		state.updatePositions();

		GameState nextState = new GameState(state, Move.up(
				whitePawn.getPosition(), 2));
		assertTrue(ChessLogic.isValidMove(nextState,
				Move.downLeft(blackPawn.getPosition(), 1)));
	}

	@Test
	public void enPassantWhiteHitsBlack() {
		blackPawn = new Pawn(new Position(3, 6), BLACK);
		whitePawn = new Pawn(new Position(2, 3), WHITE);
		white.add(whitePawn);
		black.add(blackPawn);
		state.updatePositions();

		GameState whitePawnMoves1Up = new GameState(state, Move.up(
				whitePawn.getPosition(), 1));
		GameState blackPawnMoves2Down = new GameState(whitePawnMoves1Up,
				Move.down(blackPawn.getPosition(), 2));

		assertTrue(ChessLogic.isValidMove(blackPawnMoves2Down,
				Move.upRight(new Position(2, 4), 1)));
	}
	
	@Test
	public void noEnPassantHit() {
		Fen fen = new Fen(Fen.START_POSITION);
		Move m1 = new Move(new Position(4,1), new Position(4,3));
		Move m2 = new Move(new Position(4,6), new Position(4,4));
		GameState state = fen.getGameState();
		
		ChessLogic.isValidMove(state, m1);
		state = new GameState(state, m1);
		
		ChessLogic.isValidMove(state, m2);
		state = new GameState(state, m2);
		
		Fen fen2 = new Fen(state);
		
		assertEquals("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 2", fen2.getFen());
		
	}
	
	
	@Test
	public void EnPassantHit() {
		Fen fen = new Fen(Fen.START_POSITION);
		Fen fen2;
		Move m1 = new Move(new Position(4,1), new Position(4,3));
		Move m2 = new Move(new Position(3,6), new Position(3,4));
		Move m3 = new Move(new Position(4,3), new Position(3,4));
		Move m3a = new Move(new Position(4,3), new Position(4,4));
		Move m3b = new Move(new Position(4,3), new Position(5,4));
		Move m4 = new Move(new Position(4,6), new Position(4,4));
		Move m5 = new Move(new Position(3,4), new Position(4,5));
		GameState state = fen.getGameState();
		
		assertTrue(ChessLogic.isValidMove(state, m1));
		state = new GameState(state, m1);
		
		fen2 = new Fen(state);
		assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", fen2.getFen());
		
		assertTrue(ChessLogic.isValidMove(state, m2));
		state = new GameState(state, m2);
		
		fen2 = new Fen(state);
		assertEquals("rnbqkbnr/ppp1pppp/8/3p4/4P3/8/PPPP1PPP/RNBQKBNR w KQkq d6 0 2", fen2.getFen());
		
		try {
			assertTrue(ChessLogic.isValidMove(state, m3a));
		} catch(RuntimeException e) {
			fail();
		}
		
		try {
			assertTrue(ChessLogic.isValidMove(state, m3b));
			fail();
		} catch(RuntimeException e) {
		}

		assertTrue(ChessLogic.isValidMove(state, m3));
		state = new GameState(state, m3);
		
		fen2 = new Fen(state);
		assertEquals("rnbqkbnr/ppp1pppp/8/3P4/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 2", fen2.getFen());
		
		assertTrue(ChessLogic.isValidMove(state, m4));
		state = new GameState(state, m4);
		
		fen2 = new Fen(state);
		assertEquals("rnbqkbnr/ppp2ppp/8/3Pp3/8/8/PPPP1PPP/RNBQKBNR w KQkq e6 0 3", fen2.getFen());
		
		assertTrue(ChessLogic.isValidMove(state, m5));
		state = new GameState(state, m5);
		
		fen2 = new Fen(state);
		assertEquals("rnbqkbnr/ppp2ppp/4P3/8/8/8/PPPP1PPP/RNBQKBNR b KQkq - 0 3", fen2.getFen());
		
	}
}
