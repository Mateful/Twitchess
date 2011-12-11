package de.fhb.projects.Twitchess.util;

import java.util.ArrayList;
import java.util.List;

public final class ArrayUtilities {
	public static <E> List<E> toList(E[] array) {
		ArrayList<E> list = new ArrayList<E>();
		for (int i = 0; i < array.length; i++) {
			list.add(array[i]);
		}
		return list;
	}
}
