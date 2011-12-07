package de.fhb.projects.chesstwitterbot.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.chesstwitterbot.twitterbot.main.TwitterBot;

public abstract class Command {
	public abstract void execute(TwitterBot twitterBot) throws TwitterException;
}