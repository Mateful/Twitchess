package de.fhb.projects.chesstwitterbot.controller.ucicommands;

public class PositionUCICommand extends UCICommand {
	private String fen;

	public PositionUCICommand(String fen) {
		super("position");
		setFen(fen);
		setFinished(true);
	}

	@Override
	public void processResponse(String s) {
		// position command has no response
	}

	@Override
	public String toString() {
		return getUciCommand() + " fen " + fen;
	}

	public String getFen() {
		return fen;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}

}
