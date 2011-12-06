package de.fhb.projects.chesstwitterbot.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UCIEngineController {
	private String filename;
	private OutputStream stdin;
	private InputStream stdout;
	// private BufferedReader brStdout;
	private Process process;
	private List<String> moves;
	private boolean whiteToMove;

	public UCIEngineController(String filename) {
		setFilename(filename);
		process = null;
		stdin = null;
		stdout = null;
		// brStdout = null;
		whiteToMove = true;

		moves = new ArrayList<String>();
	}

	public void close() {
		try {
			stdin.close();
			// brStdout.close();
			stdout.close();
		} catch (IOException e) {
		}

	}

	public void initEngine() {
		try {
			process = Runtime.getRuntime().exec(filename);
			stdin = process.getOutputStream();
			stdout = process.getInputStream();

			// brStdout = new BufferedReader(new InputStreamReader(stdout));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void startGame() {
		String line = null;

		moves.clear();
		whiteToMove = true;

		sendCommandString("uci");
		waitUntilOutputContainsString("uciok");
		sendCommandString("isready");
		waitUntilOutputContainsString("readyok");
		sendCommandString("ucinewgame");

		do {
			sendCommandString("isready");
			waitUntilOutputContainsString("readyok");
			sendCommandString("position startpos" + generatePositionFromMoves());
			sendCommandString("isready");
			waitUntilOutputContainsString("readyok");
			sendCommandString("go movetime 10000");
			line = waitUntilOutputContainsString("bestmove");
			line = extractMove(line);
			moves.add(line);
			System.out.println(line);
			if (line.equals("(none)")) {
				System.out.println((whiteToMove ? "Weiss" : "Schwarz")
						+ " ist matt");
				line = "";
			}
			sendCommandString("isready");
			waitUntilOutputContainsString("readyok");
			sendCommandString("d");
			whiteToMove = !whiteToMove;
		} while (line != "");
	}

	public String generatePositionFromMoves() {
		StringBuilder sb = new StringBuilder();

		if (moves.size() > 0) {
			sb.append(" moves ");
			for (String move : moves)
				sb.append(move + " ");
		}

		return sb.toString();

	}

	private String extractMove(String line) {
		String[] strings = line.split("\\s+");

		if (strings.length > 1) {
			return strings[1];
		} else
			return "";
	}

	public void sendCommandString(String s) {
		try {
			System.out.println("[Stdin] " + s);

			stdin.write((s + '\n').getBytes());
			stdin.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String waitUntilOutputContainsString(String s) {
		String line = null;
		boolean stop = false;
		long lastOutputTime = System.currentTimeMillis();
		int timeout = 50;

		try {
			while (!stop) {

				line = readlineFromConsole();
				System.out.println("[Stdout] " + line);
				if (line != null && line.contains(s)) {
					stop = true;
				} else if (line != null) {
					lastOutputTime = System.currentTimeMillis();
				} else if (System.currentTimeMillis() - timeout > lastOutputTime) {
					System.out
							.println("Engine timeout occured: timeout set to "
									+ timeout + " ms");
					stop = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return line;
	}

	private String readlineFromConsole() throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		boolean stop = false;

		do {
			c = stdout.read();
			// System.out.println(" ===> " + c);
			if (c != -1 && c != '\n')
				sb.append((char) c);
			else
				stop = true;
		} while (!stop);

		if (sb.length() > 0)
			return sb.toString();
		else
			return null;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
