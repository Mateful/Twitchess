package de.fhb.projects.Twitchess.integrationtests.twitterbot.commands;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import de.fhb.projects.Twitchess.twitterbot.commands.UpdateStatusCommand;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class UpdateStatusCommandTest {

	private UpdateStatusCommand usc;
	private TwitterBot tb;
	private Twitter twitter;
	private TwitterStream ts;

	@Before
	public void init() {
		usc = new UpdateStatusCommand("hallo");
		twitter = EasyMock.createStrictMock(Twitter.class);
		ts = EasyMock.createStrictMock(TwitterStream.class);
		tb = new TwitterBot(twitter, ts);

	}

	@Test
	public void executeNullStatusTest() throws TwitterException {
		usc.setNewStatus(null);
		usc.execute(tb);
	}

	@Test
	public void executeTest() throws TwitterException {
		usc.execute(tb);
	}

	@Test
	public void executeNullTest() throws TwitterException {
		usc.execute(null);
	}

	@Test
	public void executeTwitterExceptionTest() throws TwitterException {
		twitter.updateStatus("hallo");
		EasyMock.expectLastCall().andThrow(new TwitterException(""));
		EasyMock.replay(twitter);
		usc.execute(tb);
		EasyMock.verify(twitter);
	}
}
