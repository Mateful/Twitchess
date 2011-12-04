package de.fhb.projects.chesstwitterbot;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.chesstwitterbot.chesslogic.GameState;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Bishop;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.King;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Knight;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Pawn;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Queen;
import de.fhb.projects.chesstwitterbot.chesslogic.figures.Rook;
import de.fhb.projects.chesstwitterbot.chesslogic.player.Color;

public class MoveProcessorTest {
	private GameState state;

	@Before
	public void init() {
		state = new GameState();

		state.white.add(new Rook(new Position(0, 0)));
		state.white.add(new Rook(new Position(7, 0)));
		state.black.add(new Rook(new Position(0, 7)));
		state.black.add(new Rook(new Position(7, 7)));

		state.white.add(new Knight(new Position(1, 0)));
		state.white.add(new Knight(new Position(6, 0)));
		state.black.add(new Knight(new Position(1, 7)));
		state.black.add(new Knight(new Position(6, 7)));

		state.white.add(new Bishop(new Position(2, 0)));
		state.white.add(new Bishop(new Position(5, 0)));
		state.black.add(new Bishop(new Position(2, 7)));
		state.black.add(new Bishop(new Position(5, 7)));

		state.white.add(new Queen(new Position(3, 0)));
		state.black.add(new Queen(new Position(3, 7)));

		state.white.add(new King(new Position(4, 0)));
		state.black.add(new King(new Position(4, 7)));

		for(int i = 0; i < 8; i++) {
			state.white.add(new Pawn(new Position(i, 1), Color.WHITE));
			state.black.add(new Pawn(new Position(i, 6), Color.BLACK));
		}
	}

	@Test
	public void game() {

	}
}
