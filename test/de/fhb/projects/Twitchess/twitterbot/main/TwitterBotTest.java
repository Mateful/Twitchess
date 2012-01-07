package de.fhb.projects.Twitchess.twitterbot.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.media.ImageUpload;

public class TwitterBotTest {

	private Twitter twitterInterface;
	private TwitterBot tb;
	private TwitterStream twitterStreamInterface;
	private RequestToken requestToken;
	private AccessToken accessToken;
	private ImageUpload upload;
	private Status status;
	private User user;
	
	
	@Before
	public void init(){
		twitterInterface = EasyMock.createStrictMock(Twitter.class);
		twitterStreamInterface = EasyMock.createStrictMock(TwitterStream.class);
		tb = new TwitterBot(twitterInterface,twitterStreamInterface);
		user = EasyMock.createStrictMock(User.class);
		status = EasyMock.createStrictMock(Status.class);
		requestToken = new RequestToken("rToken", "sToken");
		accessToken = new AccessToken("404279119-gO2LepPJrIMB4mXJnyfjVoZdugHWFT6tooIU5qE", "gimlxVg1S0m1W7cEDOPyDzF5Mn0dY9pr9ZbBh9iM3Zk");
	}
	
	@Test
	public void getAccessTokenFromTwitterTest() throws TwitterException{
		EasyMock.expect(twitterInterface.getOAuthAccessToken(requestToken,"pin")).andReturn(accessToken);
		EasyMock.replay(twitterInterface);
		tb.setRequestToken(requestToken);
		tb.getAccessTokenFromTwitter("pin");
		assertEquals(accessToken,tb.getAccessToken());
				
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void getAccessTokenFromTwitterWithoutPinTest() throws TwitterException{
		EasyMock.expect(twitterInterface.getOAuthAccessToken()).andReturn(accessToken);
		EasyMock.replay(twitterInterface);
		tb.setRequestToken(requestToken);
		
		tb.getAccessTokenFromTwitter("");
		assertEquals(accessToken,tb.getAccessToken());
				
		EasyMock.verify(twitterInterface);
	}
	
	@Test 
	public void getAccessTokenFromTwitterExeptionTest() throws TwitterException{
		EasyMock.expect(twitterInterface.getOAuthAccessToken()).andThrow(new TwitterException(""));
		EasyMock.replay(twitterInterface);
		tb.setRequestToken(requestToken);
		
		tb.getAccessTokenFromTwitter("");
				
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void isMentionTest()throws TwitterException{
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("twitterBot");
		EasyMock.replay(twitterInterface);
		assertTrue(tb.isMention(status));
		
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}
	
	@Test
	public void isMentionWithoutATTest()throws TwitterException{
		EasyMock.expect(status.getText()).andReturn("twitterBot Hallo");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("twitterBot");
		EasyMock.replay(twitterInterface);
		assertFalse(tb.isMention(status));
		
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}
	
	@Test
	public void isMentionWrongNameTest()throws TwitterException{
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("OtherBot");
		EasyMock.replay(twitterInterface);
		assertFalse(tb.isMention(status));
		
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}
	
	@Test
	public void isMentionWithoutNameTest()throws TwitterException{
		EasyMock.expect(status.getText()).andReturn("@");
		EasyMock.replay(status);
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("OtherBot");
		EasyMock.replay(twitterInterface);
		assertFalse(tb.isMention(status));
		
		EasyMock.verify(twitterInterface);
		EasyMock.verify(status);
	}
	
	@Test
	public void hasOwnUsernameTest() throws IllegalStateException, TwitterException{ 
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("twitterBot");
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		
		assertTrue(tb.hasOwnUsername(status));
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void hasOwnUsernameFailTest() throws IllegalStateException, TwitterException{ 
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
	public void startAuthentificationTest() throws TwitterException{
		EasyMock.expect(twitterInterface.getOAuthRequestToken()).andReturn(requestToken);
		EasyMock.replay(twitterInterface);
		tb.startAuthentification();
		assertEquals(requestToken,tb.getRequestToken());
		
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void startAuthentificationFailTest() throws TwitterException{
		EasyMock.expect(twitterInterface.getOAuthRequestToken()).andThrow(new TwitterException(""));
		EasyMock.replay(twitterInterface);
		//TODO komme nicht an die exception ran
		tb.startAuthentification();
		
		EasyMock.verify(twitterInterface);
	}
	

	@Test
	public void onIncomingStatusTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("twitterBot");
		
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		
		tb.onIncomingStatus(status);
		
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void onIncomingStatusIllegalStateExceptionTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(status.getText()).andThrow(new IllegalStateException());
		EasyMock.replay(status);
		//TODO komme nicht an die exception ran
		tb.onIncomingStatus(status);
		
		EasyMock.verify(status);
	}
	
	@Test
	public void onIncomingStatusTwitterExceptionTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(status.getText()).andReturn("@twitterBot Hallo");
		EasyMock.expect(twitterInterface.getScreenName()).andThrow(new TwitterException(""));			
		EasyMock.replay(status);
		EasyMock.replay(twitterInterface);
		//TODO komme nicht an die exception ran
		tb.onIncomingStatus(status);
		
		EasyMock.verify(status);
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void updateStatusTest() throws TwitterException{
		EasyMock.expect(twitterInterface.updateStatus("@twitterBot Hallo")).andReturn(status);
		EasyMock.replay(twitterInterface);
		
		tb.updateStatus("@twitterBot Hallo");
		
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void updateStatusExceptionTest() throws TwitterException{
		EasyMock.expect(twitterInterface.updateStatus("@twitterBot Hallo")).andThrow(new TwitterException(""));
		EasyMock.replay(twitterInterface);
		//TODO Exception so wie schon so oft^^
		tb.updateStatus("@twitterBot Hallo");

		
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void generateStatusResponseTest(){
		assertTrue(null!=tb.generateStatusResponse("TwitterBot", "@TwitterBot chess"));
	}
	
	@Test
	public void generateStateResponseInvalidInputTest(){
		assertTrue(null==tb.generateStatusResponse("TwitterBot", "chess"));
		assertTrue(null==tb.generateStatusResponse("", "@TwitterBot chess"));
		assertTrue(null==tb.generateStatusResponse("TwitterBot", ""));
		assertTrue(null==tb.generateStatusResponse("TwitterBot", null));
		assertTrue(null==tb.generateStatusResponse(null, "@TwitterBot chess"));
		assertTrue(null==tb.generateStatusResponse(null,null));
	}
	
	
	@Test
	public void getUserNameTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("TwitterBot");
		EasyMock.replay(twitterInterface);
		
		assertEquals("TwitterBot",tb.getUserName());
		
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void getUserNameIllegalStateExceptionTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(twitterInterface.getScreenName()).andThrow(new IllegalStateException());
		EasyMock.replay(twitterInterface);
		
		assertTrue(null==tb.getUserName());
		
		EasyMock.verify(twitterInterface);
	}
	
	@Test
	public void getUserNameTwitterExceptionTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(twitterInterface.getScreenName()).andThrow(new TwitterException(""));
		EasyMock.replay(twitterInterface);
		
		assertTrue(null==tb.getUserName());
		
		EasyMock.verify(twitterInterface);
	}
	
	@Ignore
	@Test
	public void replaceFenWithImageUrlTest() throws TwitterException{
		upload = EasyMock.createStrictMock(ImageUpload.class);
//		EasyMock.expect(upload.upload(new File("generateImage.png"))).andReturn("www.Twitter.de");
//		EasyMock.replay(upload);
		tb.setAccessToken(accessToken);
		
		System.out.println(tb.replaceFenWithImageUrl("{rnbqkbnr/pppppppp/7p/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1}"));
		
//		EasyMock.verify(upload);
	}
	
	@Test
	public void answerMention() throws IllegalStateException, TwitterException{
		EasyMock.expect(status.getUser()).andReturn(user);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot");
		EasyMock.expect(status.getText()).andReturn("@twitterBot chess");
		EasyMock.expect(twitterInterface.updateStatus("@twitterBot Error while processing your request: Message does not contain a command.")).andReturn(status);
		
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		
		tb.answerMention(status);	
		
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);		
	}
	
	@Test
	public void onMentionTest() throws IllegalStateException, TwitterException{
		EasyMock.expect(status.getUser()).andReturn(user).times(2);
		EasyMock.expect(user.getScreenName()).andReturn("twitterBot").times(2);	
		EasyMock.expect(twitterInterface.getScreenName()).andReturn("twiitterBot");
		EasyMock.expect(status.getText()).andReturn("Hallo");
		
		EasyMock.replay(status);
		EasyMock.replay(user);
		EasyMock.replay(twitterInterface);
		
		tb.onMention(status);	
		
		EasyMock.verify(status);
		EasyMock.verify(user);
		EasyMock.verify(twitterInterface);
	}

}
