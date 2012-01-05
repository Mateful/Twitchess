package de.fhb.projects.Twitchess.twitterbot.main;

/**
 * 
 * app website: https://dev.twitter.com/apps/1364050/show
 * 
 * @author Benjamin, Marco, Sebastian, Curtis
 * @version 1.0
 */

public class TestDriver {
	public static void main(String[] args) {
		TwitterView tv = new TwitterView(new TwitterBot());
		tv.start();
	}
}
