package de.fhb.projects.Twitchess.controller.osvalidator;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.chesscommands.NewGameChessCommand;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.exception.OperatingSystemNotSupportedException;

public class OperatingSystemValidatorTest {
	private OperatingSystemValidator osv;

	@Before
	public void init() {
		osv = new OperatingSystemValidator();
	}

	@Test
	public void isWindowsTest() {
		assertTrue(osv.isWindows("Windows 2000"));
		assertTrue(osv.isWindows("Windows 7"));
		assertTrue(osv.isWindows("Windows 95"));
		assertTrue(osv.isWindows("Windows 98"));
		assertTrue(osv.isWindows("Windows NT"));
		assertTrue(osv.isWindows("Windows Vista"));
		assertTrue(osv.isWindows("Windows XP"));
		assertTrue(osv.isWindows("Windows CE"));
		assertTrue(osv.isWindows("win 3.1"));
		assertTrue(osv.isWindows("win xp"));
		assertTrue(osv.isWindows("windows seven"));
		assertTrue(osv.isWindows("windows 2003"));
	}
	@Test
	public void isMacTest() {
		assertTrue(osv.isMac("Mac OS 7.5.1"));
		assertTrue(osv.isMac("Mac OS 8.1"));
		assertTrue(osv.isMac("Mac OS 9.0"));
		assertTrue(osv.isMac("Mac OS X 10.1.3"));
		assertTrue(osv.isMac("Mac OS X 10.3.8"));
	}
	@Test
	public void isUnixTest() {
		assertTrue(osv.isUnix("Linux"));
		assertTrue(osv.isUnix("Linux 2.0.31"));
		assertTrue(osv.isUnix("Digital Unix"));
		assertTrue(osv.isUnix("Digital Unix 	4.0"));
		assertTrue(osv.isUnix("Unix"));
	}
	@Test
	public void isSolarisTest() {
		assertTrue(osv.isSolaris("Solaris 2.x"));
		assertTrue(osv.isSolaris("SunOS 5.7"));
		assertTrue(osv.isSolaris("SunOS"));
		assertTrue(osv.isSolaris("SunOS 5.9"));
	}
	@Test(expected = OperatingSystemNotSupportedException.class)
	public void getOperatingSystemTest1() {
		osv.getOperatingSystem("Any System");
	}
	@Test(expected = OperatingSystemNotSupportedException.class)
	public void getOperatingSystemTest2() {
		osv.getOperatingSystem("x86");
	}
	@Test(expected = OperatingSystemNotSupportedException.class)
	public void getOperatingSystemTest3() {
		osv.getOperatingSystem("Mamis PC");
	}
	@Test(expected = OperatingSystemNotSupportedException.class)
	public void getOperatingSystemTest4() {
		osv.getOperatingSystem("unsupported OS");
	}
}
