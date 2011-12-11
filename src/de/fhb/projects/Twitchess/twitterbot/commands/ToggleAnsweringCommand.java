package de.fhb.projects.Twitchess.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class ToggleAnsweringCommand extends Command {
	@Override
	public void execute(TwitterBot twitterBot) throws TwitterException {
		twitterBot.setAnswering(!twitterBot.isAnswering());
	}
}
