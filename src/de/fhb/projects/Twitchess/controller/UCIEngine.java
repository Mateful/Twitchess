package de.fhb.projects.Twitchess.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.fhb.projects.Twitchess.controller.ucicommands.GoUCICommand;
import de.fhb.projects.Twitchess.controller.ucicommands.IsreadyUCICommand;
import de.fhb.projects.Twitchess.controller.ucicommands.PositionUCICommand;
import de.fhb.projects.Twitchess.controller.ucicommands.UCICommand;
import de.fhb.projects.Twitchess.controller.ucicommands.UciUCICommand;
import de.fhb.projects.Twitchess.controller.ucicommands.UcinewgameUCICommand;
import de.fhb.projects.Twitchess.exception.UCIException;

public class UCIEngine implements UCIEngineInterface {
	public static boolean debug = true;

	private String filename;

	private OutputStream stdin;
	private InputStream stdout;
	private BufferedReader brStdout;
	private Process process;

	private Thread outputThread;

	private List<String> output;
	private UCICommand currentCommand;

	private enum SendCommandOptions {
		WAIT_UNTIL_FINISHED, DO_NOT_WAIT_UNTIL_FINISHED;
	}

	public UCIEngine(String filename, Process p, InputStream stdout, OutputStream stdin){
		this.filename = filename;
		this.process = p;
		this.stdin = stdin;
		this.stdout = stdout;
		this.brStdout = new BufferedReader(new InputStreamReader(stdout));
	}
	
	public UCIEngine(String filename) throws IOException {
		this.filename = filename;
		this.process = null;
		this.stdin = null;
		this.stdout = null;
		this.brStdout = null;

		output = new ArrayList<String>();
	}

	public void init() throws IOException {
		process = Runtime.getRuntime().exec(filename);
		stdin = process.getOutputStream();
		stdout = process.getInputStream();
		brStdout = new BufferedReader(new InputStreamReader(stdout));

		outputThread = new Thread() {
			public void run() {
				try {
					while (!isInterrupted()) {
						synchronized (output) {
							addEngineOutput(brStdout.readLine());
						}
					}
				} catch (IOException e) {
					System.out.println("error in run(): " + e.getMessage());
				}
			}
		};
		outputThread.start();

		sendCommand(new UciUCICommand(), SendCommandOptions.WAIT_UNTIL_FINISHED);
		sendCommand(new UcinewgameUCICommand(),
				SendCommandOptions.DO_NOT_WAIT_UNTIL_FINISHED);
		sendCommand(new IsreadyUCICommand(),
				SendCommandOptions.WAIT_UNTIL_FINISHED);
	}

	protected void finalize() throws Throwable {
		outputThread.interrupt();
		process.destroy();
	}

	public void destroy() throws Throwable {
		finalize();
		close();
	}

	@Override
	public String calculateMove(String fen, Integer movetime)
			throws UCIException {
		GoUCICommand go;
		sendCommand(new PositionUCICommand(fen),
				SendCommandOptions.DO_NOT_WAIT_UNTIL_FINISHED);

		go = new GoUCICommand();
		go.setMovetime(movetime);
		sendCommand(go, SendCommandOptions.WAIT_UNTIL_FINISHED);
		return go.getBestMove();
	}

	@Override
	public int calculateScore(String fen, Integer calculationTime)
			throws UCIException {
		GoUCICommand go;
		sendCommand(new PositionUCICommand(fen),
				SendCommandOptions.DO_NOT_WAIT_UNTIL_FINISHED);

		go = new GoUCICommand();
		go.setMovetime(calculationTime);
		sendCommand(go, SendCommandOptions.WAIT_UNTIL_FINISHED);
		return go.getScore();
	}

	public void close() {
		try {
			stdin.close();
			brStdout.close();
		} catch (IOException e) {
		}
	}

	protected void sendCommand(UCICommand c, SendCommandOptions o) {
		currentCommand = c;

		if (c != null) {
			sendCommandString(c.toString());

			if (o == SendCommandOptions.WAIT_UNTIL_FINISHED) {
				waitForCurrentCommandToEnd();
			}
		}
	}

	private void waitForCurrentCommandToEnd() {
		Thread t = new Thread() {
			public void run() {
				synchronized (currentCommand) {
					while (!currentCommand.isFinished()) {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		t.start();

		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void sendCommandString(String s) {
		if (s == null)
			return;

		try {
			if (debug)
				System.out.println("[stdin] " + s);

			stdin.write((s + '\n').getBytes());
			stdin.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addEngineOutput(String s) {
		if (s == null)
			return;

		if (debug)
			System.out.println("[stdout] " + s);
		output.add(s);
		processEngineOutput(s);
	}

	public void processEngineOutput(String s) {
		if (currentCommand != null)
			currentCommand.processResponse(s);
	}

	public String getFilename() {
		return filename;
	}

	public OutputStream getStdin() {
		return stdin;
	}

	public InputStream getStdout() {
		return stdout;
	}

	public BufferedReader getBrStdout() {
		return brStdout;
	}

	public Process getProcess() {
		return process;
	}

	public List<String> getOutput() {
		return output;
	}

	public Thread getOutputThread() {
		return outputThread;
	}

	public UCICommand getCurrentCommand() {
		return currentCommand;
	}
}
