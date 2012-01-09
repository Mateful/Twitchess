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
		if(twitterBot!=null && followerName !=null)
			twitterBot.createFriendship(followerName);
	}

	public String getFollowerName() {
		return followerName;
	}

	public void setFollowerName(String followerName) {
		this.followerName = followerName;
	}
	
}
