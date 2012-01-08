package de.fhb.projects.Twitchess.controller;

import java.io.IOException;

import de.fhb.projects.Twitchess.exception.UCIException;

public interface UCIEngineInterface {
	public String calculateMove(String fen, Integer movetime)
			throws UCIException;
	public void init() throws IOException;
	public void destroy() throws Throwable;
	public int calculateScore(String fen, Integer calculationTime) throws UCIException;
}
