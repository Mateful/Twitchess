package de.fhb.projects.Twitchess.controller.osvalidator;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import de.fhb.projects.Twitchess.exception.OperatingSystemNotSupportedException;

public class OperatingSystemValidatorTest {
	private OperatingSystemValidator osv = new OperatingSystemValidator();

	@Test
	public void testWindows() {
		assertTrue(osv.isWindows("Windows 2000"));
		assertTrue(osv.isWindows("Windows 7"));
		assertTrue(osv.isWindows("Windows 95"));
		assertTrue(osv.isWindows("Windows 98"));
		assertTrue(osv.isWindows("Windows NT"));
		assertTrue(osv.isWindows("Windows Vista"));
		assertTrue(osv.isWindows("Windows XP"));
		assertTrue(osv.isWindows("Windows CE"));
		assertTrue(osv.isWindows("Windows 2003"));
	}
	@Test
	public void testMac() {
		assertTrue(osv.isMac("Mac OS 7.5.1"));
		assertTrue(osv.isMac("Mac OS 8.1"));
		assertTrue(osv.isMac("Mac OS 9.0"));
		assertTrue(osv.isMac("Mac OS X 10.1.3"));
		assertTrue(osv.isMac("Mac OS X 10.3.8"));
	}
	@Test
	public void testUnix() {
		assertTrue(osv.isUnix("Linux"));
		assertTrue(osv.isUnix("Linux 2.0.31"));
		assertTrue(osv.isUnix("Digital Unix"));
		assertTrue(osv.isUnix("Digital Unix 	4.0"));
		assertTrue(osv.isUnix("Unix"));
	}
	@Test
	public void testSolaris() {
		assertTrue(osv.isSolaris("Solaris 2.x"));
		assertTrue(osv.isSolaris("SunOS 5.7"));
		assertTrue(osv.isSolaris("SunOS"));
		assertTrue(osv.isSolaris("SunOS 5.9"));
	}
	@Test(expected = OperatingSystemNotSupportedException.class)
	public void testInput() {
		osv.getOperatingSystem("Any System");
		osv.getOperatingSystem("x86");
		osv.getOperatingSystem("Mamis PC");
		osv.getOperatingSystem("unsupported OS");
	}
}
