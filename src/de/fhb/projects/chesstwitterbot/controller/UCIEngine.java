package de.fhb.projects.chesstwitterbot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import de.fhb.projects.chesstwitterbot.controller.ucicommands.GoUCICommand;
import de.fhb.projects.chesstwitterbot.controller.ucicommands.IsreadyUCICommand;
import de.fhb.projects.chesstwitterbot.controller.ucicommands.PositionUCICommand;
import de.fhb.projects.chesstwitterbot.controller.ucicommands.UCICommand;
import de.fhb.projects.chesstwitterbot.controller.ucicommands.UciUCICommand;
import de.fhb.projects.chesstwitterbot.controller.ucicommands.UcinewgameUCICommand;
import de.fhb.projects.chesstwitterbot.exception.UCIException;

public class UCIEngine extends Observable {
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
	
	public static void main(String[] args) {
		try {
			UCIEngine eng = new UCIEngine("chessengines/stockfish-211-32-ja-windows.exe");
			
			System.out.println(eng.calculateMove("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UCIException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public UCIEngine(String filename) throws IOException {
		this.filename = filename;
		this.process = null;
		this.stdin = null;
		this.stdout = null;
		this.brStdout = null;

		output = new ArrayList<String>();
		initEngine();
	}

	public void initEngine() throws IOException {
		process = Runtime.getRuntime().exec(filename);
		stdin = process.getOutputStream();
		stdout = process.getInputStream();
		brStdout = new BufferedReader(new InputStreamReader(stdout));

		outputThread = new Thread() {
			public void run() {
				try {
					while (true) {
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
	}
	
	public String calculateMove(String fen) throws UCIException {
		GoUCICommand go;
		sendCommand(new PositionUCICommand(fen),
				SendCommandOptions.DO_NOT_WAIT_UNTIL_FINISHED);
		
		go = new GoUCICommand();
		sendCommand(go, SendCommandOptions.WAIT_UNTIL_FINISHED);
		return go.getBestMove();
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
