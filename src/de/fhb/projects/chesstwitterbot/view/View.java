package de.fhb.projects.chesstwitterbot.view;

import de.fhb.projects.chesstwitterbot.controller.UCIEngineController;

public class View {
	
	private UCIEngineController uciEngine;
	
	public static void main(String argv[]) {
		new View();
	}
	
	public View() {
		uciEngine = new UCIEngineController("chessengines\\stockfish-211-32-ja-windows.exe");
		start();
	}
	
	public void start()  {
		uciEngine.initEngine();
		
		uciEngine.startGame();
		uciEngine.close();
	}
	
	
}
