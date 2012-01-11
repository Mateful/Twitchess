package de.fhb.projects.Twitchess.twitterbot.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import de.fhb.projects.Twitchess.games.chess.Fen;
import de.fhb.projects.Twitchess.twitterbot.commands.FollowCommand;
import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class TwitterBotTest {

	private Twitter twitterInterface;
	private TwitterBot tb;
	private TwitterStream twitterStreamInterface;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private Status status;
	private User user;

	@Before
	public void init() {
		twitterInterface = EasyMock.createStrictMock(Twitter.class);
		twitterStreamInterface = EasyMock.createStrictMock(TwitterStream.class);
		tb = new TwitterBot(twitterInterface, twitterStreamInterface);
		user = EasyMock.createStrictMock(User.class);
		status = EasyMock.createStrictMock(Status.class);
		requestToken = new RequestToken("rToken", "sToken");
		accessToken = new AccessToken(
				"404279119-gO2LepPJrIMB4mXJnyfjVoZdugHWFT6tooIU5qE",
				"gimlxVg1S0m1W7cEDOPyDzF5Mn0dY9pr9ZbBh9iM3Zk");
	}

	@Test
	public void testGetAccessTokenFromTwitter() throws TwitterException {
		EasyMock.expect(
				twitterInterface.getOAuthAccessToken(requestToken, "pin"))
				.andReturn(accessToken);
		EasyMock.replay(twitterInterface);
		tb.setRequestToken(requestToken);
		tb.getAccessTokenFromTwitter("pin");
		assertEquals(accessToken, tb.getAccessToken());
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testGetAccessTokenFromTwitterWithoutPin()
			throws TwitterException {
		EasyMock.expect(twitterInterface.getOAuthAccessToken()).andReturn(
				accessToken);
		EasyMock.replay(twitterInterface);
		tb.setRequestToken(requestToken);
		tb.getAccessTokenFromTwitter("");
		assertEquals(accessToken, tb.getAccessToken());
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testGetAccessTokenFromTwitterExeption() throws TwitterException {
		EasyMock.expect(twitterInterface.getOAuthAccessToken()).andThrow(
				new TwitterException(""));
		EasyMock.replay(twitterInterface);
		tb.setRequestToken(requestToken);
		tb.getAccessTokenFromTwitter("");
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testIsMention() throws TwitterException {
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"twitterBot");
		EasyMock.replay(twitterInterface);
		assertTrue(tb.isMention(status));
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}

	@Test
	public void testIsMentionWithoutAT() throws TwitterException {
		EasyMock.expect(status.getText()).andReturn("twitterBot Hallo");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"twitterBot");
		EasyMock.replay(twitterInterface);
		assertFalse(tb.isMention(status));
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}

	@Test
	public void testIsMentionWrongName() throws TwitterException {
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("OtherBot");
		EasyMock.replay(twitterInterface);
		assertFalse(tb.isMention(status));
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}

	@Test
	public void testIsMentionWithoutName() throws TwitterException {
		EasyMock.expect(status.getText()).andReturn("@");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("OtherBot");
		EasyMock.replay(twitterInterface);
		assertFalse(tb.isMention(status));
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}

	@Test
	public void testHasOwnUsername() throws IllegalStateException,
			TwitterException {
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"twitterBot");
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		assertTrue(tb.hasOwnUsername(status));
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testHasOwnUsernameFail() throws IllegalStateException,
			TwitterException {
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("");
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		assertFalse(tb.hasOwnUsername(status));
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testStartAuthentification() throws TwitterException {
		EasyMock.expect(twitterInterface.getOAuthRequestToken()).andReturn(
				requestToken);
		EasyMock.replay(twitterInterface);
		tb.startAuthentification();
		assertEquals(requestToken, tb.getRequestToken());
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testStartAuthentificationFail() throws TwitterException {
		EasyMock.expect(twitterInterface.getOAuthRequestToken()).andThrow(
				new TwitterException(""));
		EasyMock.replay(twitterInterface);
		tb.startAuthentification();
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testOnIncomingStatus() throws IllegalStateException,
			TwitterException {
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"twitterBot");
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"twitterBot");
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		tb.onIncomingStatus(status);
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testOnIncomingStatusIllegalStateException()
			throws IllegalStateException, TwitterException {
		EasyMock.expect(status.getText()).andThrow(new IllegalStateException());
		EasyMock.replay(status);
		tb.onIncomingStatus(status);
		EasyMock.verify(status);
	}

	@Test
	public void testOnIncomingStatusTwitterException()
			throws IllegalStateException, TwitterException {
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.expect(twitterInterface.getScreenName()).andThrow(
				new TwitterException(""));
		EasyMock.replay(status);
		EasyMock.replay(twitterInterface);
		tb.onIncomingStatus(status);
		EasyMock.verify(status);
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testUpdateStatus() throws TwitterException {
		EasyMock.expect(twitterInterface.updateStatus("@twitterBot Hallo"))
				.andReturn(status);
		EasyMock.replay(twitterInterface);
		tb.updateStatus("@twitterBot Hallo");
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testUpdateStatusException() throws TwitterException {
		EasyMock.expect(twitterInterface.updateStatus("@twitterBot Hallo"))
				.andThrow(new TwitterException(""));
		EasyMock.replay(twitterInterface);
		tb.updateStatus("@twitterBot Hallo");
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testGenerateStatusResponse() {
		assertTrue(null != tb.generateStatusResponse("TwitterBot",
				"@TwitterBot chess"));
	}

	@Test
	public void testGenerateStateResponseInvalidInput() {
		assertTrue(null == tb.generateStatusResponse("TwitterBot", "chess"));
		assertTrue(null == tb.generateStatusResponse("", "@TwitterBot chess"));
		assertTrue(null == tb.generateStatusResponse("TwitterBot", ""));
		assertTrue(null == tb.generateStatusResponse("TwitterBot", null));
		assertTrue(null == tb.generateStatusResponse(null, "@TwitterBot chess"));
		assertTrue(null == tb.generateStatusResponse(null, null));
	}

	@Test
	public void testGetUserName() throws IllegalStateException,
			TwitterException {
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"TwitterBot");
		EasyMock.replay(twitterInterface);
		assertEquals("TwitterBot", tb.getUserName());
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testGetUserNameIllegalStateException()
			throws IllegalStateException, TwitterException {
		EasyMock.expect(twitterInterface.getScreenName()).andThrow(
				new IllegalStateException());
		EasyMock.replay(twitterInterface);
		assertTrue(null == tb.getUserName());
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testGetUserNameTwitterException() throws IllegalStateException,
			TwitterException {
		EasyMock.expect(twitterInterface.getScreenName()).andThrow(
				new TwitterException(""));
		EasyMock.replay(twitterInterface);
		assertTrue(null == tb.getUserName());
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testAnswerMention() throws IllegalStateException, TwitterException {
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(status.getText()).andReturn("@twitterBot chess");
		EasyMock.expect(
				twitterInterface
						.updateStatus("@twitterBot Error: Message does not contain a command."))
				.andReturn(status);
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		tb.answerMention(status);
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testOnMention() throws IllegalStateException, TwitterException {
		EasyMock.expect(status.getUser()).andReturn(user).times(2);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot").times(2);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn(
				"twiitterBot");
		EasyMock.expect(status.getText()).andReturn("Hallo");
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		tb.onMention(status);
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}

	@Test
	public void testReplaceFenWithImageUrl() throws TwitterException {
		tb.setAccessToken(accessToken);
		assertEquals(
				"{rnbqkbnr/pppppppp/7p/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1}",
				tb.replaceFenWithImageUrl("{rnbqkbnr/pppppppp/7p/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1}"));

	}

	@Test
	public void replaceFenWithImageUrlNullStringTest() {
		tb.setAccessToken(accessToken);
		assertEquals(null, tb.replaceFenWithImageUrl(null));
	}

	@Test
	public void testGenerateImageFromFen() {
		Fen fen = new Fen(
				"rnbqkbnr/pppppppp/7p/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		assertEquals(new File("generatedImage.png"),
				tb.generateImageFromFen(fen));
		assertEquals(null, tb.generateImageFromFen(null));
	}

	@Test
	public void testReceiveCommand() {
		FollowCommand command = new FollowCommand("@Matefulbot");
		tb.receiveCommand(command);
	}

	@Test
	public void testReceiveCommandExeption() throws TwitterException {
		FollowCommand command = new FollowCommand("@Test");
		EasyMock.expect(twitterInterface.createFriendship("@Test")).andThrow(
				new TwitterException(""));
		EasyMock.replay(twitterInterface);

		tb.receiveCommand(command);
	}
}
