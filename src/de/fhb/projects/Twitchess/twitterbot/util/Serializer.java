package de.fhb.projects.Twitchess.twitterbot.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializer {
	public static void save(Object o, String name) throws IOException {
		save(o, name, "");
	}

	public static void save(Object o, String name, String folder)
			throws IOException {
		ObjectOutputStream outStream;
		outStream = new ObjectOutputStream(new FileOutputStream(folder + name));
		outStream.writeObject(o);
		outStream.flush();
		outStream.close();
	}

	public static Object load(String name) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		return load(name, "");
	}

	public static Object load(String name, String folder)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		Object o = new Object();
		ObjectInputStream inStream;
		if (!folder.equals(""))
			folder += "/";
		inStream = new ObjectInputStream(new FileInputStream(folder + name));
		o = inStream.readObject();
		return o;
	}
}