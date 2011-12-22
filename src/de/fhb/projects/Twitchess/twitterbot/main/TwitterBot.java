package de.fhb.projects.Twitchess.twitterbot.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.UserStreamAdapter;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import de.fhb.projects.Twitchess.controller.ManagerFactory;
import de.fhb.projects.Twitchess.controller.ManagerInterface;
import de.fhb.projects.Twitchess.twitterbot.commands.Command;
import de.fhb.projects.Twitchess.twitterbot.exceptions.TokenNotFoundException;
import de.fhb.projects.Twitchess.twitterbot.util.Serializer;

public class TwitterBot extends Observable {
	public final String STANDARD_ACCOUNT = "MatefulBot";

	private Twitter twitter;
	private TwitterStream twitterStream;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private boolean answering;

	public TwitterBot() {
		this(new TwitterFactory().getInstance(), new TwitterStreamFactory()
				.getInstance());
	}

	public TwitterBot(Twitter t, TwitterStream s) {
		twitter = t;
		twitterStream = s;

		answering = true;
		addListener();
	}

	private void addListener() {
		twitterStream.addListener(new UserStreamAdapter() {
			@Override
			public void onStatus(Status s) {
				try {
					onIncomingStatus(s);
				} catch (Exception e) {
					notifyObservers(e);
				}
			}
		});
	}

	public void startAuthentification() {
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			notifyObservers(e);
		}
	}

	public String getAuthentificationLink() {
		return requestToken.getAuthorizationURL();
	}

	public void getAccessTokenFromTwitter(String pin) {
		try {
			if (pin.length() > 0)
				accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			else
				accessToken = twitter.getOAuthAccessToken();
		} catch (TwitterException e) {
			notifyObservers(e);
		}
	}

	public void startStream() {
		twitter.setOAuthAccessToken(accessToken);
		twitterStream.setOAuthAccessToken(accessToken);
		twitterStream.user();
	}

	private void onIncomingStatus(Status s) {
		notifyObservers(s.getUser().getScreenName() + "'s status update: "
				+ s.getText());

		try {
			if (isMention(s))
				onMention(s);
		} catch (IllegalStateException e) {
			notifyObservers(e);
		} catch (TwitterException e) {
			notifyObservers(e);
		}
	}

	private boolean isMention(Status s) throws TwitterException {
		return s.getText().startsWith("@" + twitter.getScreenName());
	}

	private void onMention(Status s) {
		if (answering && !hasOwnUsername(s))
			answerMention(s);
	}

	private boolean hasOwnUsername(Status s) {
		return s.getUser().getScreenName().equals(getUserName());
	}

	private void answerMention(Status s) {
		String newStatusMessage = generateStatusResponse(s.getUser()
				.getScreenName(), s.getText());

		if (newStatusMessage != null) {
			notifyObservers("generated answer: " + newStatusMessage);
			updateStatus(newStatusMessage);
		}
	}

	public String generateStatusResponse(String from, String text) {
		String result = null;
		if (from != null && text != null && !from.equals("")
				&& !text.equals("")) {

			ManagerInterface manager = ManagerFactory.getRelevantManager(text);

			if (manager != null) {
				result = "@" + from + " " + manager.processInput(from, text);
			}
		}

		return result;
	}

	public void receiveCommand(Command command) {
		try {
			command.execute(this);
		} catch (TwitterException e) {
			notifyObservers(e);
		}
	}

	public void updateStatus(String status) {
		try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			if (!isDuplicateStatusUpdateError(e))
				notifyObservers(e);
		}
	}

	public void createFriendship(String name) throws TwitterException {
		twitter.createFriendship(name);
	}

	public void loadDefaultAccessToken() {
		try {
			loadAccessToken(STANDARD_ACCOUNT);
		} catch (TokenNotFoundException e) {
			notifyObservers(e);
		}
	}

	public void loadAccessToken(String token) throws TokenNotFoundException {
		try {
			accessToken = (AccessToken) Serializer.load(token);
		} catch (FileNotFoundException e) {
			throw new TokenNotFoundException(token + " token not found.");
		} catch (IOException e) {
			notifyObservers(e);
		} catch (ClassNotFoundException e) {
			notifyObservers(new Exception(
					"AccessToken class was not found, the library is probably corrupted."));
		}
	}

	public void saveAccessToken() {
		try {
			Serializer.save(accessToken, accessToken.getScreenName());
		} catch (IOException e) {
			notifyObservers(e);
		}
	}

	public String getUserName() {
		return accessToken.getScreenName();
	}

	public void setAnswering(boolean b) {
		answering = b;
	}

	public boolean isAnswering() {
		return answering;
	}

	private boolean isDuplicateStatusUpdateError(TwitterException e) {
		return e.getStatusCode() == 403;
	}

	private void notifyObservers(Exception e) {
		setChanged();
		super.notifyObservers(e);
	}

	private void notifyObservers(String s) {
		setChanged();
		super.notifyObservers(s);
	}
}