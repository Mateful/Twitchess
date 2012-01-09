package de.fhb.projects.Twitchess.twitterbot.commands;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.twitterbot.main.TwitterBot;

public class ToggleAnsweringCommandTest {

	private ToggleAnsweringCommand tac;
	private TwitterBot tb;
	
	@Before
	public void init(){
		tac = new ToggleAnsweringCommand();
		tb = new TwitterBot();
	}
	
	@Test
	public void executeAnsweringTrueTest(){
		tb.setAnswering(true);
		tac.execute(tb);
		assertTrue(!tb.isAnswering());
	}
	
	@Test
	public void executeAnsweringFalseTest(){
		tb.setAnswering(false);
		tac.execute(tb);
		assertTrue(tb.isAnswering());
	}
	
	@Test
	public void executeNullTest(){
		tac.execute(null);
		assertTrue(tb.isAnswering());
	}
}
