package de.fhb.projects.Twitchess.twitterbot.commands;

import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class ToggleAnsweringCommand extends Command {
	@Override
	public void execute(TwitterBot twitterBot){
		if(twitterBot!=null)
		twitterBot.setAnswering(!twitterBot.isAnswering());
	}
}
