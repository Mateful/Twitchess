package de.fhb.projects.chesstwitterbot.testchesslogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.move.AbsoluteMove;
import de.fhb.projects.chesstwitterbot.chesslogic.move.Direction;
import de.fhb.projects.chesstwitterbot.chesslogic.move.RelativeMove;

public class MoveClasses {
	@Test
	public void relativeMoveEquals() {
		assertEquals(new RelativeMove(Direction.UP, true), new RelativeMove(
				Direction.UP, true));
	}

	@Test
	public void getRelativeMoveFromAbsoluteMove() {
		AbsoluteMove oneStepUpA = new AbsoluteMove(new Position(0, 0),
				new Position(0, 1));
		AbsoluteMove oneStepDownA = new AbsoluteMove(new Position(0, 0),
				new Position(0, -1));
		AbsoluteMove oneStepLeftA = new AbsoluteMove(new Position(0, 0),
				new Position(-1, 0));
		AbsoluteMove oneStepRightA = new AbsoluteMove(new Position(0, 0),
				new Position(1, 0));
		RelativeMove oneStepUpR = new RelativeMove(Direction.UP, false);
		RelativeMove oneStepDownR = new RelativeMove(Direction.DOWN, false);
		RelativeMove oneStepLeftR = new RelativeMove(Direction.LEFT, false);
		RelativeMove oneStepRightR = new RelativeMove(Direction.RIGHT, false);

		assertTrue(oneStepUpR.equals(oneStepUpA.getRelativeMove()));
		assertTrue(oneStepDownR.equals(oneStepDownA.getRelativeMove()));
		assertTrue(oneStepLeftR.equals(oneStepLeftA.getRelativeMove()));
		assertTrue(oneStepRightR.equals(oneStepRightA.getRelativeMove()));
		assertFalse(oneStepUpR.equals(oneStepDownA.getRelativeMove()));
		assertFalse(oneStepLeftR.equals(oneStepRightA.getRelativeMove()));
	}

	@Test
	public void absoluteMoveDirection() {
		Position start = new Position(3, 3);
		assertEquals(Direction.UP,
				new AbsoluteMove(start, new Position(3, 4)).getDirection());
		assertEquals(Direction.DOWN,
				new AbsoluteMove(start, new Position(3, 2)).getDirection());
		assertEquals(Direction.LEFT,
				new AbsoluteMove(start, new Position(2, 3)).getDirection());
		assertEquals(Direction.RIGHT, new AbsoluteMove(start,
				new Position(4, 3)).getDirection());
		assertEquals(Direction.UPRIGHT, new AbsoluteMove(start, new Position(4,
				4)).getDirection());
		assertEquals(Direction.DOWNRIGHT, new AbsoluteMove(start, new Position(
				4, 2)).getDirection());
		assertEquals(Direction.UPLEFT, new AbsoluteMove(start, new Position(2,
				4)).getDirection());
		assertEquals(Direction.DOWNLEFT, new AbsoluteMove(start, new Position(
				2, 2)).getDirection());
		assertEquals(Direction.KNIGHT, new AbsoluteMove(start, new Position(4,
				5)).getDirection());
		assertEquals(Direction.KNIGHT, new AbsoluteMove(start, new Position(2,
				5)).getDirection());
		assertEquals(Direction.KNIGHT, new AbsoluteMove(start, new Position(2,
				5)).getDirection());
		assertEquals(Direction.KNIGHT, new AbsoluteMove(start, new Position(1,
				2)).getDirection());
		assertEquals(Direction.KNIGHT, new AbsoluteMove(start, new Position(5,
				2)).getDirection());
	}

	@Test(expected = RuntimeException.class)
	public void invalidDirection1() {
		new AbsoluteMove(new Position(3, 3), new Position(4, 6));
	}

	@Test(expected = RuntimeException.class)
	public void invalidDirection2() {
		new AbsoluteMove(new Position(3, 3), new Position(7, 6));
	}

	@Test(expected = RuntimeException.class)
	public void invalidDirection3() {
		new AbsoluteMove(new Position(3, 3), new Position(0, 4));
	}

	@Test(expected = RuntimeException.class)
	public void invalidDirection4() {
		new AbsoluteMove(new Position(3, 3), new Position(7, 5));
	}
}
