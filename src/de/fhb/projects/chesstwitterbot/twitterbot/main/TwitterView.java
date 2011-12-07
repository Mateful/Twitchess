package de.fhb.projects.chesstwitterbot.twitterbot.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Observable;
import java.util.Observer;

import de.fhb.projects.chesstwitterbot.twitterbot.commands.ExitCommand;
import de.fhb.projects.chesstwitterbot.twitterbot.commands.FollowCommand;
import de.fhb.projects.chesstwitterbot.twitterbot.commands.ToggleAnsweringCommand;
import de.fhb.projects.chesstwitterbot.twitterbot.commands.UpdateStatusCommand;
import de.fhb.projects.chesstwitterbot.twitterbot.exceptions.TokenNotFoundException;

public class TwitterView implements Runnable, Observer {
	private TwitterBot twitterbot;
	private Thread thread;
	private BufferedReader inputReader;
	private boolean running;

	public TwitterView(TwitterBot controller) {
		this.twitterbot = controller;
		inputReader = new BufferedReader(new InputStreamReader(System.in));
		twitterbot.addObserver(this);
		login();
	}

	private void login() {
		boolean loggedIn = false;
		while(!loggedIn) {
			String name = chooseAccount();
			try {
				twitterbot.loadAccessToken(name);
				loggedIn = true;
			} catch(TokenNotFoundException e) {
				printMessage(name + " token was not found.");
				printMessage("Create new token? (y/n): ");
				if(getInput().equals("y")) {
					createToken();
					loggedIn = true;
				}
			}
		}
		twitterbot.startStream();
	}

	private String chooseAccount() {
		printMessage("Enter your Twitter username you want to use the bot with. Leave blank for logging in with " + twitterbot.STANDARD_ACCOUNT + ".");
		String input = getInput();
		if(input.equals(""))
			input = twitterbot.STANDARD_ACCOUNT;
		return input;
	}

	private void createToken() {
		twitterbot.startAuthentification();
		printMessage("Open the following URL and grant access to your account:");
		printMessage(twitterbot.getAuthentificationLink());
		printMessage("Enter the PIN: ");
		twitterbot.getAccessTokenFromTwitter(getInput());
		twitterbot.saveAccessToken();
	}

	public void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}

	@Override
	public void run() {
		while(running) {
			printMenu();
			processInput(getInput());
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				printErrorMessage(e.getMessage());
			}
		}
	}

	private void processInput(String input) {
		switch(getCommandNumber(input)) {
		case 0:
			twitterbot.receiveCommand(new ExitCommand());
			break;
		case 1:
			printMessage("Enter name: ");
			twitterbot.receiveCommand(new FollowCommand(getInput()));
			break;
		case 2:
			twitterbot.receiveCommand(new ToggleAnsweringCommand());
			break;
		case 3:
			printMessage("Enter your new status: ");
			twitterbot.receiveCommand(new UpdateStatusCommand(getInput()));
			break;
		default:
			printErrorMessage("Unknown Command");
			break;
		}
	}

	private int getCommandNumber(String input) {
		int commandNumber;
		try {
			commandNumber = Integer.parseInt(input);
		} catch(NumberFormatException e) {
			commandNumber = -1;
		}
		return commandNumber;
	}

	private String getInput() {
		String input = "";
		try {
			input = inputReader.readLine();
		} catch(IOException e) {
			printErrorMessage(e.getMessage());
		}
		return input;
	}

	private void printMenu() {
		final String menu = "TwitterBotJunior by Marco and Benjamin\n"
				+ "---------------------------------------" + "\n"
				+ "Logged in as: " + twitterbot.getUserName() + "\n"
				+ "Press\n" + " <1> to follow another twitter user\n"
				+ " <2> to toggle automatic answering of mentions\n"
				+ " <3> to update your status manually\n"
				+ " <0> to close TwitterBotJunior";
		printMessage(menu);
		printMessage("Automatic answering of mentions is "
				+ (twitterbot.isAnswering() ? "enabled" : "disabled"));
	}

	private void printMessage(String message) {
		System.out.println(message);
	}

	private void printErrorMessage(String errorMessage) {
		System.err.println(errorMessage);
	}

	@Override
	public void update(Observable observable, Object argument) {
		if(argument instanceof Exception)
			printErrorMessage(((Exception)argument).getMessage());
		else if(argument instanceof String)
			printMessage((String)argument);
	}
}
