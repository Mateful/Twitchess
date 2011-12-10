package de.fhb.projects.chesstwitterbot.controller.ucicommands;

public class UciUCICommand extends UCICommand {

	public UciUCICommand() {
		super("uci");
		// TODO Auto-generated constructor stub
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
