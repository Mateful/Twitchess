package de.fhb.projects.Twitchess.controller.ucicommands;

public class IsreadyUCICommand extends UCICommand {

	public IsreadyUCICommand() {
		super("isready");
	}

	@Override
	public void processResponse(String s) {
		if (s != null && s.contains("readyok"))
			setFinished(true);
	}

	@Override
	public String toString() {
		return getUciCommand();
	}

}
