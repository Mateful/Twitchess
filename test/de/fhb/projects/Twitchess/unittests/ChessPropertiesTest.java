package de.fhb.projects.Twitchess.unittests;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.ChessProperties;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.util.ArrayUtilities;

public class ChessPropertiesTest {
	@Test
	public void getWhiteKing() {
		King king = ChessProperties.getKing(WHITE);
		assertEquals(new Position(4, 0), king.getPosition());
	}

	@Test
	public void getBlackKing() {
		King king = ChessProperties.getKing(BLACK);
		assertEquals(new Position(4, 7), king.getPosition());
	}

	@Test
	public void getWhiteQueen() {
		Queen queen = ChessProperties.getQueen(WHITE);
		assertEquals(new Position(3, 0), queen.getPosition());
	}

	@Test
	public void getBlackQueen() {
		Queen queen = ChessProperties.getQueen(BLACK);
		assertEquals(new Position(3, 7), queen.getPosition());
	}

	@Test
	public void getWhiteBishops() {
		List<Bishop> bishops = ArrayUtilities.toList(ChessProperties
				.getBishops(WHITE));
		assertEquals(2, bishops.size());
		assertTrue(bishops.contains(new Bishop(new Position(2, 0), WHITE)));
		assertTrue(bishops.contains(new Bishop(new Position(5, 0), WHITE)));
	}

	@Test
	public void getBlackBishops() {
		List<Bishop> bishops = ArrayUtilities.toList(ChessProperties
				.getBishops(BLACK));
		assertEquals(2, bishops.size());
		assertTrue(bishops.contains(new Bishop(new Position(2, 7), BLACK)));
		assertTrue(bishops.contains(new Bishop(new Position(5, 7), BLACK)));
	}

	@Test
	public void getWhiteKnights() {
		List<Knight> knights = ArrayUtilities.toList(ChessProperties
				.getKnights(WHITE));
		assertEquals(2, knights.size());
		assertTrue(knights.contains(new Knight(new Position(1, 0), WHITE)));
		assertTrue(knights.contains(new Knight(new Position(6, 0), WHITE)));
	}

	@Test
	public void getBlackKnights() {
		List<Knight> knights = ArrayUtilities.toList(ChessProperties
				.getKnights(BLACK));
		assertEquals(2, knights.size());
		assertTrue(knights.contains(new Knight(new Position(1, 7), BLACK)));
		assertTrue(knights.contains(new Knight(new Position(6, 7), BLACK)));
	}

	@Test
	public void getWhiteRooks() {
		List<Rook> rooks = ArrayUtilities.toList(ChessProperties
				.getRooks(WHITE));
		assertEquals(2, rooks.size());
		assertTrue(rooks.contains(new Rook(new Position(0, 0), WHITE)));
		assertTrue(rooks.contains(new Rook(new Position(7, 0), WHITE)));
	}

	@Test
	public void getBlackRooks() {
		List<Rook> rooks = ArrayUtilities.toList(ChessProperties
				.getRooks(BLACK));
		assertEquals(2, rooks.size());
		assertTrue(rooks.contains(new Rook(new Position(0, 7), BLACK)));
		assertTrue(rooks.contains(new Rook(new Position(7, 7), BLACK)));
	}

	@Test
	public void getWhitePawns() {
		List<Pawn> pawns = ArrayUtilities.toList(ChessProperties
				.getPawns(WHITE));
		assertEquals(8, pawns.size());
		assertTrue(pawns.contains(new Pawn(new Position(0, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(1, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(2, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(3, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(4, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(5, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(6, 1), WHITE)));
		assertTrue(pawns.contains(new Pawn(new Position(7, 1), WHITE)));
	}

	@Test
	public void getBlackPawns() {
		List<Pawn> pawns = ArrayUtilities.toList(ChessProperties
				.getPawns(BLACK));
		assertEquals(8, pawns.size());
		assertTrue(pawns.contains(new Pawn(new Position(0, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(1, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(2, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(3, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(4, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(5, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(6, 6), BLACK)));
		assertTrue(pawns.contains(new Pawn(new Position(7, 6), BLACK)));
	}
}
