package de.fhb.projects.Twitchess.controller.ucicommands;

public class UciUCICommand extends UCICommand {

	public UciUCICommand() {
		super("uci");
	}

	@Override
	public void processResponse(String s) {
		if (s.equals("uciok"))
			this.setFinished(true);
	}

	@Override
	public String toString() {
		return getUciCommand();
	}

}
