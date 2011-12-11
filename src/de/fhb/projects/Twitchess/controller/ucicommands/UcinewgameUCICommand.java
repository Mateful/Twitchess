package de.fhb.projects.Twitchess.controller.ucicommands;

public class UcinewgameUCICommand extends UCICommand {

	public UcinewgameUCICommand() {
		super("ucinewgame");
		setFinished(true);
	}

	@Override
	public void processResponse(String s) {
		// ucinewgame command has no response
	}

	@Override
	public String toString() {
		return getUciCommand();
	}

}
