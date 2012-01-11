package de.fhb.projects.Twitchess.twitterbot.main;

import de.fhb.projects.Twitchess.exception.TokenNotFoundException;
import de.fhb.projects.Twitchess.twitterbot.commands.Command;
import de.fhb.projects.Twitchess.twitterbot.commands.ExitCommand;

public class TwitterViewStub extends TwitterBotAbstract{

	private boolean exitCommand = false;
	
	public void receiveCommand(Command command){
		if(command instanceof ExitCommand)
			exitCommand= true;			
	}

	public boolean isExitCommand() {
		return exitCommand;
	}

	public void setExitCommand(boolean exitCommand) {
		this.exitCommand = exitCommand;
	}

	@Override
	public void loadAccessToken(String token) throws TokenNotFoundException {
		// TODO Auto-generated method stub		
	}

	@Override
	public void startStream() {
		// TODO Auto-generated method stub		
	}

	@Override
	public void startAuthentification() {
		// TODO Auto-generated method stub		
	}

	@Override
	public String getAuthentificationLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getAccessTokenFromTwitter(String pin) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void saveAccessToken() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAnswering() {
		// TODO Auto-generated method stub
		return false;
	};
}
