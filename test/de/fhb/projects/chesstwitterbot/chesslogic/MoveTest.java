package de.fhb.projects.chesstwitterbot.chesslogic;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveTest {
	@Test
	public void MoveTest1() {
		Move m = new Move(Position.getPosition(3, 1), Position.getPosition(3, 3));
		assertTrue(m.toString().equals("d2d4"));
	}
}
