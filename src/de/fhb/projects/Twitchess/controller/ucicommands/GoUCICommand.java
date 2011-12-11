package de.fhb.projects.Twitchess.controller.ucicommands;

import java.util.HashMap;
import java.util.Map;

import de.fhb.projects.Twitchess.exception.UCIException;

/**
 * (partial!) implementation of the uci command "go"
 * 
 * @author Ben
 * @version 0.01
 */
public class GoUCICommand extends UCICommand {
	private Map<String, String> result;

	private Integer movetime;
	private Integer depth;

	public GoUCICommand() {
		super("go");

		result = new HashMap<String, String>();
		setMovetime(10000);
		setDepth(null);
	}

	@Override
	public void processResponse(String s) {
		if (s == null)
			return;

		if (s.startsWith("bestmove")) {
			String[] temp = s.split("\\s+");
			String bestMove = "";

			if (temp.length > 1) {
				bestMove = temp[1];
			}

			result.put("bestMove", bestMove);

			setFinished(true);
		}
	}

	public String getBestMove() throws UCIException {
		String bestMove = "";
		if (isFinished()) {
			bestMove = result.get("bestMove");

			if (bestMove.equals("(none)")) {
				throw new UCIException("No best move found!");
			}

			return bestMove;
		} else
			throw new UCIException("Calculation has not been finished yet!");
	}

	public String generateFullCommandString() {
		StringBuilder sb = new StringBuilder();

		sb.append(getUciCommand());

		if (depth != null) {
			sb.append(" depth");
			sb.append(" ");
			sb.append(depth);
		} else if (movetime != null) {
			sb.append(" movetime");
			sb.append(" ");
			sb.append(movetime);
		}

		return sb.toString();
	}

	@Override
	public String toString() {
		return generateFullCommandString();
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		if (depth != null && depth > 0) {
			this.depth = depth;
			setMovetime(null);
		}
	}

	public Integer getMovetime() {
		return movetime;
	}

	public void setMovetime(Integer movetime) {
		if (movetime != null && movetime > 0) {
			this.movetime = movetime;
			setDepth(null);
		}
	}

}
