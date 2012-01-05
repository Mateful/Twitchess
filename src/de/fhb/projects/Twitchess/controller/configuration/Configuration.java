package de.fhb.projects.Twitchess.controller.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.Properties;

public class Configuration {
	private static final String FILE_NAME = "configuration.properties";
	private static Properties p;

	static {
		try {
			FileInputStream input = new FileInputStream(FILE_NAME);
			p = new Properties();
			p.load(input);
		} catch (IOException e) {
			p = null;
		}
	}

	public static String getString(String key) {
		try {
			return (String) p.get(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		} catch (NullPointerException e) {
			return '!' + FILE_NAME + " is missing" + '!';
		}
	}
}
