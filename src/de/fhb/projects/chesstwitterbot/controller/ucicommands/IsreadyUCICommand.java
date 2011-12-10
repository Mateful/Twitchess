package de.fhb.projects.chesstwitterbot.controller.ucicommands;

public class IsreadyUCICommand extends UCICommand {

	public IsreadyUCICommand() {
		super("isready");
	}

	@Override
	public void processResponse(String s) {
		if (s.contains("readyok"))
			setFinished(true);
	}

	@Override
	public String toString() {
		return getUciCommand();
	}

}
