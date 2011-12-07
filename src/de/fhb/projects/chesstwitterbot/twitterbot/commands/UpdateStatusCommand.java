package de.fhb.projects.chesstwitterbot.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.chesstwitterbot.twitterbot.main.TwitterBot;

public class UpdateStatusCommand extends Command {
	private String newStatus;

	public UpdateStatusCommand(String newStatus) {
		this.newStatus = newStatus;
	}

	@Override
	public void execute(TwitterBot twitterBot) throws TwitterException {
		twitterBot.updateStatus(newStatus);
	}

}
