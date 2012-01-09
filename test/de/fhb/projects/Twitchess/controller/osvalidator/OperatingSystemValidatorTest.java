package de.fhb.projects.Twitchess.controller.osvalidator;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class OperatingSystemValidatorTest {

	private OperatingSystemValidator osv = new OperatingSystemValidator();

	@Test
	public void testWindows() {
		assertTrue(osv.isWindows("windows 2000"));
		assertTrue(osv.isWindows("windows 7"));
		assertTrue(osv.isWindows("windows 95"));
		assertTrue(osv.isWindows("windows 98"));
		assertTrue(osv.isWindows("windows NT"));
		assertTrue(osv.isWindows("windows Vista"));
		assertTrue(osv.isWindows("windows XP"));
		assertTrue(osv.isWindows("windows CE"));
		assertTrue(osv.isWindows("windows 2003"));
	}
	@Test
	public void testMac() {
		assertTrue(osv.isMac("mac OS 7.5.1"));
		assertTrue(osv.isMac("mac OS 8.1"));
		assertTrue(osv.isMac("mac OS 9.0"));
		assertTrue(osv.isMac("mac OS X 10.1.3"));
		assertTrue(osv.isMac("mac OS X 10.3.8"));
	}
	@Test
	public void testUnix() {
		assertTrue(osv.isUnix("Linux"));
		assertTrue(osv.isUnix("Linux 2.0.31"));
		assertTrue(osv.isUnix("Digital Unix"));
		assertTrue(osv.isUnix("Digital Unix 	4.0"));
		assertTrue(osv.isUnix("Unix"));
	}

}
