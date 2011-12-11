package de.fhb.projects.Twitchess.unittests;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.NOCOLOR;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.King;
import de.fhb.projects.Twitchess.games.chess.figures.Knight;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Queen;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class PlayerTest {
	private Player white, black;
	
	@Before
	public void init() {
		white = new Player(WHITE);
		black = new Player(BLACK);
	}
	
	@Test(expected = RuntimeException.class)
	public void constructorCallWithNoColor() {
		new Player(NOCOLOR);
	}

	@Test
	public void equals() {
		assertEquals(white, white);
		assertEquals(white, new Player(WHITE));
		assertFalse(white.equals(null));
		assertFalse(white.equals(new Object()));
		assertEquals(white, white);
		assertEquals(black, new Player(BLACK));
		assertFalse(black.equals(null));
		assertFalse(black.equals(new Object()));
		assertFalse(new Player(WHITE).equals(new Player(BLACK)));
	}

	@Test
	public void add() {
		Player player = new Player(WHITE);
		player.add(new King(new Position(0, 0)));
		assertTrue(player.getFiguresInGame().contains(
				new King(new Position(0, 0), WHITE)));
		player.add(new King(new Position(0, 0), WHITE));
		assertTrue(player.getFiguresInGame().contains(
				new King(new Position(0, 0), WHITE)));
		player.add(new King(new Position(0, 0), BLACK));
		assertTrue(player.getFiguresInGame().contains(
				new King(new Position(0, 0), WHITE)));
	}

	@Test(expected = RuntimeException.class)
	public void dontAddNull() {
		Player player = new Player(WHITE);
		player.add(null);
	}

	@Test
	public void getFullyInitializedPlayerWhite() {
		Player player = Player.getFullyInitializedPlayer(WHITE);
		List<Figure> figures = player.getFiguresInGame();
		assertEquals(player.getColor(), WHITE);
		assertEquals(16, figures.size());
		assertEquals(0, player.getFiguresOutOfGame().size());
		assertTrue(figures.contains(new King(new Position(4, 0), WHITE)));
		assertTrue(figures.contains(new Queen(new Position(3, 0), WHITE)));
		assertTrue(figures.contains(new Bishop(new Position(2, 0), WHITE)));
		assertTrue(figures.contains(new Bishop(new Position(5, 0), WHITE)));
		assertTrue(figures.contains(new Knight(new Position(1, 0), WHITE)));
		assertTrue(figures.contains(new Knight(new Position(6, 0), WHITE)));
		assertTrue(figures.contains(new Rook(new Position(0, 0), WHITE)));
		assertTrue(figures.contains(new Rook(new Position(7, 0), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(0, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(1, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(2, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(3, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(4, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(5, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(6, 1), WHITE)));
		assertTrue(figures.contains(new Pawn(new Position(7, 1), WHITE)));
	}

	@Test
	public void getfullyInitializedPlayerBlack() {
		Player player = Player.getFullyInitializedPlayer(BLACK);
		List<Figure> figures = player.getFiguresInGame();
		assertEquals(player.getColor(), BLACK);
		assertEquals(16, figures.size());
		assertEquals(0, player.getFiguresOutOfGame().size());
		assertTrue(figures.contains(new King(new Position(4, 7), BLACK)));
		assertTrue(figures.contains(new Queen(new Position(3, 7), BLACK)));
		assertTrue(figures.contains(new Bishop(new Position(2, 7), BLACK)));
		assertTrue(figures.contains(new Bishop(new Position(5, 7), BLACK)));
		assertTrue(figures.contains(new Knight(new Position(1, 7), BLACK)));
		assertTrue(figures.contains(new Knight(new Position(6, 7), BLACK)));
		assertTrue(figures.contains(new Rook(new Position(0, 7), BLACK)));
		assertTrue(figures.contains(new Rook(new Position(7, 7), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(0, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(1, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(2, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(3, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(4, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(5, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(6, 6), BLACK)));
		assertTrue(figures.contains(new Pawn(new Position(7, 6), BLACK)));
	}

	@Test(expected = RuntimeException.class)
	public void getfullyInitializedPlayerNoColor() {
		Player.getFullyInitializedPlayer(NOCOLOR);
	}
	
	@Test(expected = RuntimeException.class)
	public void getKingButNoKingFound() {
		white.getKing();
	}
	
	@Test
	public void getKing() {
		King king = new King(new Position(0, 0));
		white.add(king);
		assertEquals(king, white.getKing());
	}
}
