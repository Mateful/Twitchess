package de.fhb.projects.Twitchess.twitterbot.commands;

import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class FollowCommandTest {

	private FollowCommand fc;
	private TwitterBot tb;
	private Twitter twitter;
	private TwitterStream ts;
	
	@Before
	public void init(){
		fc = new FollowCommand("MatefulBot");		
		twitter = EasyMock.createStrictMock(Twitter.class);	
		ts = EasyMock.createStrictMock(TwitterStream.class);
		tb = new TwitterBot(twitter,ts);
	}
	@Test (expected = TwitterException.class)
	public void executeTwitterExceptionTest()throws TwitterException{
		tb.createFriendship("MatefulBot");
		EasyMock.expectLastCall().andThrow(new TwitterException(""));
		EasyMock.replay(twitter);
		fc.execute(tb);
		EasyMock.verify(twitter);
	}
	
	
	@Test
	public void executeNullTest() throws TwitterException{
		fc.execute(null);
		assertTrue(tb.isAnswering());
	}
	
	@Test
	public void executeTest() throws TwitterException{
		fc.execute(tb);
	}
	
	@Test
	public void executeNullFollowerTest() throws TwitterException{
		fc.setFollowerName(null);
		fc.execute(tb);
	}
	
	@Test
	public void executeEmptyFollowerTest() throws TwitterException{
		fc.setFollowerName("");
		fc.execute(tb);
	}
	
}
