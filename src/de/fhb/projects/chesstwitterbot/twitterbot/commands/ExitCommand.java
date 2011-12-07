package de.fhb.projects.chesstwitterbot.twitterbot.commands;

import de.fhb.projects.chesstwitterbot.twitterbot.main.TwitterBot;

public class ExitCommand extends Command {
	@Override
	public void execute(TwitterBot twitterBot) {
		System.exit(0);
	}
}
