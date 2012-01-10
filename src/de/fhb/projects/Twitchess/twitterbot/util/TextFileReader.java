package de.fhb.projects.Twitchess.twitterbot.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextFileReader {

	/**
	 * 
	 * @param filename
	 * @return ArrayList of lines in the textfile, leaving out blank lines.
	 * @throws IOException
	 */
	public static ArrayList<String> readTextFileLineByLine(String filename)
			throws IOException {
		ArrayList<String> lines = new ArrayList<String>();
		if(filename == null)
			return lines;
		String input;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		do {
			input = br.readLine();
			if (input != null)
				lines.add(input);
		} while (input != null);
		br.close();
		return lines;
	}
}
