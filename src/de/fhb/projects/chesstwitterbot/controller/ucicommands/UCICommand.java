package de.fhb.projects.chesstwitterbot.controller.ucicommands;

public abstract class UCICommand {
	protected String uciCommand;
	protected boolean finished;

	public UCICommand(String uciCommand) {
		super();
		this.uciCommand = uciCommand;
	}

	public String getUciCommand() {
		return uciCommand;
	}

	public void setUciCommand(String uciCommand) {
		this.uciCommand = uciCommand;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public abstract void processResponse(String s);
	public abstract String toString();
	

}
