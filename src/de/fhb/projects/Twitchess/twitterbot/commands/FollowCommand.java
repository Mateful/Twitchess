package de.fhb.projects.Twitchess.twitterbot.commands;

import twitter4j.TwitterException;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class FollowCommand extends Command {
	private String followerName;

	public FollowCommand(String followerName) {
		this.followerName = followerName;
	}

	@Override
	public void execute(TwitterBot twitterBot) throws TwitterException {
		twitterBot.createFriendship(followerName);
	}

}
