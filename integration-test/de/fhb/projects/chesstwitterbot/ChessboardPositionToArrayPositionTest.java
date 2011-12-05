package de.fhb.projects.chesstwitterbot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessboardPositionToArrayPosition;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.exception.InvalidMoveException;

public class ChessboardPositionToArrayPositionTest {
	@Test
	public void chessboardFromArray() {
		assertEquals("a8",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(0, 7)));
		assertEquals("b7",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(1, 6)));
		assertEquals("c6",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(2, 5)));
		assertEquals("d5",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(3, 4)));
		assertEquals("e4",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(4, 3)));
		assertEquals("f3",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(5, 2)));
		assertEquals("g2",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(6, 1)));
		assertEquals("h1",
				ChessboardPositionToArrayPosition
						.parseArrayPostion(new Position(7, 0)));
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardYPositiveFromArray() {
		ChessboardPositionToArrayPosition
				.parseArrayPostion(new Position(0, 12));
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardYNegativeFromArray() {
		ChessboardPositionToArrayPosition
				.parseArrayPostion(new Position(0, -1));
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardXPositiveFromArray() {
		ChessboardPositionToArrayPosition
				.parseArrayPostion(new Position(12, 0));
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardXNegativeFromArray() {
		ChessboardPositionToArrayPosition
				.parseArrayPostion(new Position(-1, 0));
	}

	@Test
	public void arrayFromChessboard() {
		assertEquals(new Position(0, 7),
				ChessboardPositionToArrayPosition.parseChessboardPosition("a8"));
		assertEquals(new Position(1, 6),
				ChessboardPositionToArrayPosition.parseChessboardPosition("b7"));
		assertEquals(new Position(2, 5),
				ChessboardPositionToArrayPosition.parseChessboardPosition("c6"));
		assertEquals(new Position(3, 4),
				ChessboardPositionToArrayPosition.parseChessboardPosition("d5"));
		assertEquals(new Position(4, 3),
				ChessboardPositionToArrayPosition.parseChessboardPosition("e4"));
		assertEquals(new Position(5, 2),
				ChessboardPositionToArrayPosition.parseChessboardPosition("f3"));
		assertEquals(new Position(6, 1),
				ChessboardPositionToArrayPosition.parseChessboardPosition("g2"));
		assertEquals(new Position(7, 0),
				ChessboardPositionToArrayPosition.parseChessboardPosition("h1"));
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardYPositiveFromChessboard() {
		ChessboardPositionToArrayPosition.parseChessboardPosition("a9");
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardYNegativeFromChessboard() {
		ChessboardPositionToArrayPosition.parseChessboardPosition("a0");
	}

	@Test(expected = InvalidMoveException.class)
	public void outOfChessboardXPositiveFromChessboard() {
		ChessboardPositionToArrayPosition
				.parseChessboardPosition((char) ('a' - 1) + "1");
	}
}
