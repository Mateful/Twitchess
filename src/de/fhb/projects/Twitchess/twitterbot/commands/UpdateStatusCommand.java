package de.fhb.projects.Twitchess.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class UpdateStatusCommand extends Command {
	private String newStatus;

	public UpdateStatusCommand(String newStatus) {
		this.newStatus = newStatus;
	}

	@Override
	public void execute(TwitterBot twitterBot) {
		if(newStatus != null && twitterBot != null)
		twitterBot.updateStatus(newStatus);
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		
		this.newStatus = newStatus;
	}

}
