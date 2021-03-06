package de.fhb.projects.Twitchess.image;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

import javax.imageio.ImageIO;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.ArrayComparisonFailure;

import de.fhb.projects.Twitchess.image.GenerateImage;

public class GenerateImageTest {
	private static GenerateImage gi;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		gi = new GenerateImage("test-files/board.properties");
	}

	@Test
	public void testGenerateColumnToCoordinate() {
		gi.setFieldDimension(1);
		gi.setOrigin(new Point(0, 0));
		assertEquals("Column 1 is 1", 1, gi.columnToCoordinate(1));
		assertEquals("Column 2 is 2", 2, gi.columnToCoordinate(2));
		assertEquals("Column 3 is 3", 3, gi.columnToCoordinate(3));
		assertEquals("Column 4 is 4", 4, gi.columnToCoordinate(4));
		assertEquals("Column 5 is 5", 5, gi.columnToCoordinate(5));
		assertEquals("Column 6 is 6", 6, gi.columnToCoordinate(6));
		assertEquals("Column 7 is 7", 7, gi.columnToCoordinate(7));
		assertEquals("Column 8 is 8", 8, gi.columnToCoordinate(8));
		assertTrue(Integer.MIN_VALUE == gi
				.columnToCoordinate(Integer.MIN_VALUE));
		assertTrue(Integer.MAX_VALUE == gi
				.columnToCoordinate(Integer.MAX_VALUE));
	}

	@Test
	public void testGenerateRowToCoordinate() {
		gi.setFieldDimension(1);
		gi.setOrigin(new Point(0, 0));
		assertEquals("Column 0 is 0", 0, gi.rowToCoordinate(0));
		assertEquals("Column 1 is 1", 1, gi.rowToCoordinate(1));
		assertEquals("Column 2 is 2", 2, gi.rowToCoordinate(2));
		assertEquals("Column 3 is 3", 3, gi.rowToCoordinate(3));
		assertEquals("Column 4 is 4", 4, gi.rowToCoordinate(4));
		assertEquals("Column 5 is 5", 5, gi.rowToCoordinate(5));
		assertEquals("Column 6 is 6", 6, gi.rowToCoordinate(6));
		assertEquals("Column 7 is 7", 7, gi.rowToCoordinate(7));
		assertEquals("Column 8 is 8", 8, gi.rowToCoordinate(8));
		assertTrue(Integer.MIN_VALUE == gi.rowToCoordinate(Integer.MIN_VALUE));
		assertTrue(Integer.MAX_VALUE == gi.rowToCoordinate(Integer.MAX_VALUE));
	}

	@Test(expected = RuntimeException.class)
	public void testGenerateInvalidFen() {

		gi = new GenerateImage("test-files/board.properties");
		assertNotNull(gi
				.generateImageFromFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
		assertNull(gi
				.generateImageFromFen("rnbqdsfafkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"));
	}

	@Test
	public void testResetImage() {
		gi = new GenerateImage("test-files/board.properties");
		try {
			assertArrayEquals("",
					getMD5Hash(ImageIO.read(new File("test-files/field.png"))),
					getMD5Hash(gi.resetImage()));
		} catch (ArrayComparisonFailure e) {
			fail();
		} catch (IOException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testinitialiseFromPropertyFile() {
		gi = new GenerateImage("test-files/board.properties");
		assertTrue(gi.getFieldDimension() == 55);
		assertEquals(new Point(52, 0), gi.getOrigin());
		assertEquals("test-files/field.png", gi.getBoardFilename());
		assertEquals("test-files/%f-%c.png", gi.getFigureFilenamePatter());
	}

	@Test
	public void testSetFigureFilenamePatterNullFileName() {
		gi.getFigureFilenamePatter();
		gi.setFigureFilenamePatter(null);
		gi.getFigureFilenamePatter();
		assertEquals("%f-%c.png", gi.getFigureFilenamePatter());
	}

	private byte[] getMD5Hash(BufferedImage buffImg) throws Exception {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(buffImg, "png", outputStream);
		byte[] data = outputStream.toByteArray();
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(data);
		byte[] hash = md.digest();
		return hash;
	}

}
