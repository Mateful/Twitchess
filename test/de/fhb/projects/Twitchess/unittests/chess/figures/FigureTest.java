package de.fhb.projects.Twitchess.unittests.chess.figures;

import static org.junit.Assert.*;

import org.junit.Test;

import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Bishop;
import de.fhb.projects.Twitchess.games.chess.figures.Figure;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.figures.Rook;
import de.fhb.projects.Twitchess.games.chess.player.Color;

public class FigureTest {
	@Test(expected = RuntimeException.class)
	public void setColorToNoColor() {
		Figure f = new Bishop(new Position(0, 0));
		f.setColor(Color.NOCOLOR);
	}
	
	@Test
	public void equals() {
		Figure pawn = new Pawn(new Position(0, 0));
		assertEquals(pawn, pawn);
		assertEquals(new Pawn(new Position(0, 0)), pawn);
		assertFalse(pawn.equals(null));
		assertFalse(pawn.equals(new Object()));
		assertFalse(pawn.equals(new Rook(new Position(0, 0))));
	}
}
