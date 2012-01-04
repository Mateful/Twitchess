package de.fhb.projects.Twitchess.integrationtests.chess;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.move.Move;

public class GetAllMovesTest {
	@Test
	public void getAllMovesWhite() {
		Fen f = new Fen(
				"r1bqk1nr/ppp2ppp/2n5/1B1pp3/1b1P4/2N1PN2/PPP2PPP/R1BQK2R w KQkq - 0 5");
		List<Move> l = new ArrayList<Move>();
		GameState s = f.getGameState();

		for (int i = 0; i < s.getCurrentPlayer().getFiguresInGame().size(); i++) {
			l.addAll(ChessLogic.getAllMoves(s, s.getCurrentPlayer()
					.getFiguresInGame().get(i)));
		}

		assertEquals(32, l.size());
	}

	@Test
	public void getAllMovesBlack() {
		Fen f = new Fen(
				"r1bqk1nr/ppp2ppp/2n5/1B1pp3/1b1P4/2N1PN2/PPP2PPP/R1BQK2R b KQkq - 0 5");
		List<Move> l = new ArrayList<Move>();
		GameState s = f.getGameState();

		for (int i = 0; i < s.getCurrentPlayer().getFiguresInGame().size(); i++) {
			l.addAll(ChessLogic.getAllMoves(s, s.getCurrentPlayer()
					.getFiguresInGame().get(i)));
		}

		
		assertEquals(36, l.size());
	}
	
	@Test
	public void getAllMovesDoubleCheckWhite() {
		Fen f = new Fen(
				"6k1/8/8/6r1/4R3/4Rn1b/5P1P/6K1 w - - 0 1");
		List<Move> l = new ArrayList<Move>();
		GameState s = f.getGameState();

		for (int i = 0; i < s.getCurrentPlayer().getFiguresInGame().size(); i++) {
			l.addAll(ChessLogic.getAllMoves(s, s.getCurrentPlayer()
					.getFiguresInGame().get(i)));
		}

		
		assertEquals(1, l.size());
	}
	
	@Test
	public void getAllMovesDoubleCheckBlack() {
		Fen f = new Fen(
				"6K1/8/8/6R1/4r3/4rN2/5p1p/6k1 b - - 0 1");
		List<Move> l = new ArrayList<Move>();
		GameState s = f.getGameState();

		for (int i = 0; i < s.getCurrentPlayer().getFiguresInGame().size(); i++) {
			l.addAll(ChessLogic.getAllMoves(s, s.getCurrentPlayer()
					.getFiguresInGame().get(i)));
		}

		
		assertEquals(2, l.size());
//		System.out.println("COUNT = " + l.size());
//		for (Move m : l) {
//			System.out.println(m);
//		}
	}
}
