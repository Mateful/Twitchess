package de.fhb.projects.Twitchess.image;

import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import de.fhb.projects.Twitchess.games.chess.Fen;

public class GenerateImage {
	private int fieldDimension;
	private Point origin;
	private String boardFilename;
	private String figureFilenamePatter;

	public GenerateImage(final String propertyFilename)
			throws HeadlessException {
		super();
		initialiseFromPropertyFile(propertyFilename);
	}

	private void initialiseFromPropertyFile(final String propertyFilename) {
		try {
			int x, y;
			FileInputStream input = new FileInputStream(propertyFilename);
			Properties p = new Properties();

			p.load(input);

			setFieldDimension(Integer
					.parseInt((String) p.get("fieldDimension")));

			x = Integer.parseInt((String) p.get("origin_x"));
			y = Integer.parseInt((String) p.get("origin_y"));
			setOrigin(new Point(x, y));

			setBoardFilename((String) p.get("boardFilename"));
			setFigureFilenamePatter((String) p.get("figureFilenamePatter"));
			input.close();
		} catch (FileNotFoundException e) {
			System.err.println("Can't find " + propertyFilename);
		} catch (IOException e) {
			System.err
					.println("I/O failed. Could not initialise image generation!");
		} catch (NumberFormatException e) {
			System.err.println(propertyFilename
					+ " contains bad data. Setting default values...");
			setFieldDimension(55);
			setOrigin(new Point(52, 0));
		}
	}

	public BufferedImage generateImageFromFen(final String fen) {
		BufferedImage backgroundImage = null;

		try {
			backgroundImage = resetImage();
			addFiguresToImage(fen, backgroundImage);

		} catch (IOException e) {
			backgroundImage = null;
		}

		return backgroundImage;
	}

	private void addFiguresToImage(final String fen,
			final BufferedImage backgroundImage) {
		char c;
		int row = 0, column = 0;

		new Fen(fen);

		for (int i = 0; ' ' != fen.charAt(i); i++) {
			c = fen.charAt(i);

			if (Character.isLetter(c)) {
				generateImage(getFigureFilename(c), row, column,
						backgroundImage);
				column++;
			} else if (Character.isDigit(c)) {
				column += c - '0';
			} else if (c == '/') {
				row++;
				column = 0;
			}

		}
	}

	public void generateImage(final String filename, final int row,
			final int column, final BufferedImage backgroundImage) {
		try {
			BufferedImage foregroundImage = ImageIO.read(new File(filename));
			backgroundImage.getGraphics().drawImage(foregroundImage,
					columnToCoordinate(column), rowToCoordinate(row), null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveImage(final File file, final BufferedImage bimg) {
		try {
			ImageIO.write(bimg, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFigureFilename(final char c) {
		String result = figureFilenamePatter;

		result = result.replace("%c", Character.isUpperCase(c) ? "w" : "b");
		result = result.replace("%f", "" + Character.toLowerCase(c));

		return result;
	}

	public BufferedImage resetImage() throws IOException {

		BufferedImage backgroundImage;
		backgroundImage = ImageIO.read(new File(boardFilename));
		return backgroundImage;
	}

	public int columnToCoordinate(final int column) {
		return origin.x + column * fieldDimension;
	}

	public int rowToCoordinate(final int row) {
		return origin.y + row * fieldDimension;
	}

	public int getFieldDimension() {
		return fieldDimension;
	}

	public void setFieldDimension(final int fieldDimension) {
		this.fieldDimension = fieldDimension;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public String getBoardFilename() {
		return boardFilename;
	}

	public void setBoardFilename(final String boardFilename) {
		this.boardFilename = boardFilename;
	}

	public String getFigureFilenamePatter() {
		return figureFilenamePatter;
	}

	public void setFigureFilenamePatter(final String figureFilenamePatter) {
		if (figureFilenamePatter == null
				|| !figureFilenamePatter.contains("%f")
				|| !figureFilenamePatter.contains("%c"))
			this.figureFilenamePatter = "%f-%c.png";
		else
			this.figureFilenamePatter = figureFilenamePatter;
	}
}