package de.fhb.projects.Twitchess.twitterbot.main;


import static de.fhb.projects.Twitchess.controller.configuration.Configuration.getString;

import java.awt.image.BufferedImage;
import java.io.File;
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
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import de.fhb.projects.Twitchess.controller.ManagerFactory;
import de.fhb.projects.Twitchess.controller.ManagerInterface;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.image.GenerateImage;
import de.fhb.projects.Twitchess.twitterbot.commands.Command;
import de.fhb.projects.Twitchess.twitterbot.exceptions.TokenNotFoundException;
import de.fhb.projects.Twitchess.twitterbot.util.Serializer;

public class TwitterBot extends Observable {
	public final String STANDARD_ACCOUNT = getString("Twitter.StandardAccount", "MatefulBot");

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

	protected void addListener() {
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

	protected void onIncomingStatus(Status s) {
		try {
			if (isMention(s)) {
				notifyObservers("FROM " + s.getUser().getScreenName() + ": "
						+ s.getText());
				onMention(s);
			}
		} catch (IllegalStateException e) {
			notifyObservers(e);
		} catch (TwitterException e) {
			notifyObservers(e);
		}
	}

	protected boolean isMention(Status s) throws TwitterException {
		return s.getText().startsWith("@" + twitter.getScreenName());
	}

	protected void onMention(Status s) {
		if (answering && !hasOwnUsername(s))
			answerMention(s);
	}

	protected boolean hasOwnUsername(Status s) {
		return s.getUser().getScreenName().equals(getUserName());
	}

	protected void answerMention(Status s) {
		String newStatusMessage = generateStatusResponse(s.getUser()
				.getScreenName(), s.getText());

		if (newStatusMessage != null) {
			notifyObservers("----------------------------------------------\n"
					+ "ANSWER: " + newStatusMessage
					+ "\n----------------------------------------------\n");
			updateStatus(newStatusMessage);
		}
	}

	public synchronized String generateStatusResponse(String from, String text) {
		String result = null;
		if (from != null && text != null && !from.equals("")
				&& !text.equals("")) {

			ManagerInterface manager = ManagerFactory.getRelevantManager(text);

			if (manager != null) {
				result = manager.processInput(from, text);
				result = replaceFenWithImageUrl(result);
				result = "@" + from + " " + result;
			}
		}

		return result;
	}

	protected String replaceFenWithImageUrl(final String param) {
		String result = param;
		if(param == null)
			return result;

		int start = param.indexOf('{');
		

		if (start >= 0) {
			int end = param.indexOf('}', start + 1);

			if (end >= 0) {
				String s = param.substring(start + 1, end);
				Fen fen = new Fen(s);
				File f = generateImageFromFen(fen);

				String url = null;

				try {
					url = uploadFile(f);

					if (url != null) {
						result = param.substring(0, start) + url
								+ param.substring(end + 1);
					}
				} catch (TwitterException e) {
					notifyObservers(e);
					return param;
				}
			}
		}

		return result;
	}

	protected File generateImageFromFen(Fen fen) {
		if(fen ==null)
			return null;
		GenerateImage gen = new GenerateImage("img/board.properties");
		File f = new File("generatedImage.png");
		BufferedImage img = gen.generateImageFromFen(fen.getFen());
		gen.saveImage(f, img);
		return f;
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

	public String uploadFile(File file) throws TwitterException {
		if (file != null && file.exists() && accessToken != null) {
			ConfigurationBuilder configBuilder = new ConfigurationBuilder();
			configBuilder.setOAuthAccessToken(accessToken.getToken());
			configBuilder.setOAuthAccessTokenSecret(accessToken
					.getTokenSecret());
			Configuration conf = configBuilder.build();
			ImageUploadFactory factory = new ImageUploadFactory(conf);
			ImageUpload upload = factory.getInstance();

			return upload.upload(file);
		}
		return null;
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
		try {
			return twitter.getScreenName();
		} catch (IllegalStateException e) {
			return null;
		} catch (TwitterException e) {
			return null;
		}
	}

	public void setAnswering(boolean b) {
		answering = b;
	}

	public boolean isAnswering() {
		return answering;
	}

	protected boolean isDuplicateStatusUpdateError(TwitterException e) {
		return e.getStatusCode() == 403;
	}

	protected void notifyObservers(Exception e) {
		setChanged();
		super.notifyObservers(e);
	}

	protected void notifyObservers(String s) {
		setChanged();
		super.notifyObservers(s);
	}
	
	public RequestToken getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(RequestToken requestToken) {
		this.requestToken = requestToken;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}
}