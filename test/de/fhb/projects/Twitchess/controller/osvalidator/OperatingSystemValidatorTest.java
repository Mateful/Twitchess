package de.fhb.projects.Twitchess.controller.osvalidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class OperatingSystemValidatorTest {

	private OperatingSystemValidator osv;

	@Test (expected = Exception.class)
	public void testWindows() {
		String os = "mac";
		//assertFalse(osv.isWindows(os));
	}

}
