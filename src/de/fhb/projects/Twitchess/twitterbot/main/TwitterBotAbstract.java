package de.fhb.projects.Twitchess.twitterbot.main;

import static de.fhb.projects.Twitchess.controller.configuration.Configuration.getString;

import java.util.Observable;

import de.fhb.projects.Twitchess.exception.TokenNotFoundException;
import de.fhb.projects.Twitchess.twitterbot.commands.Command;

public abstract class TwitterBotAbstract extends Observable{

	public final String STANDARD_ACCOUNT = getString("Twitter.StandardAccount", "MatefulBot");

	public abstract void loadAccessToken(String token)throws TokenNotFoundException;
	
	public abstract void startStream() ;
	
	public abstract void startAuthentification();
	
	public abstract String getAuthentificationLink();
	
	public abstract void getAccessTokenFromTwitter(String pin);
	
	public abstract void saveAccessToken();
	
	public abstract void receiveCommand(Command command);
	
	public abstract String getUserName();
	
	public abstract boolean isAnswering();
	
}