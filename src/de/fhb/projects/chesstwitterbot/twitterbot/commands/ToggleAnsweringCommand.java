package de.fhb.projects.chesstwitterbot.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.chesstwitterbot.twitterbot.main.TwitterBot;

public class ToggleAnsweringCommand extends Command {
	@Override
	public void execute(TwitterBot twitterBot) throws TwitterException {
		twitterBot.setAnswering(!twitterBot.isAnswering());
	}
}
