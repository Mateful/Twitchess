package de.fhb.projects.Twitchess.twitterbot.commands;

import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class ExitCommand extends Command {
	@Override
	public void execute(TwitterBot twitterBot) {
		System.exit(0);
	}
}
