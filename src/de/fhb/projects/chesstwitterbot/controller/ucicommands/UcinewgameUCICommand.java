package de.fhb.projects.chesstwitterbot.controller.ucicommands;


public class UcinewgameUCICommand extends UCICommand{

	public UcinewgameUCICommand() {
		super("ucinewgame");
		setFinished(true);
	}

	@Override
	public void processResponse(String s) {
		// nothing to do here
	}

	@Override
	public String toString() {
		return getUciCommand();
	}
	
}
