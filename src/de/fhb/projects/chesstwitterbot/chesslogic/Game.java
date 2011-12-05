package de.fhb.projects.chesstwitterbot.chesslogic;

import java.util.ArrayList;

import de.fhb.projects.chesstwitterbot.chesslogic.move.Move;

public class Game {
	private ArrayList<GameState> states;
	
	public Game() {
		states = new ArrayList<GameState>();
		states.add(new GameState());
	}
	
	public void doMove(Move move) {
		
	}
}
