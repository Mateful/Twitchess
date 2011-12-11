package de.fhb.projects.Twitchess.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public abstract class Command {
	public abstract void execute(TwitterBot twitterBot) throws TwitterException;
}