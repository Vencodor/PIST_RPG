package main.java.pist.api.function;

import java.util.Random;

public class Key {

	public static String getRandomKey(int targetStringLength) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
		  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
		  .limit(targetStringLength)
		  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
		  .toString();

		return generatedString;
	}

	public static String getSimpleRandomKey(int targetStringLength) {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		Random random = new Random();

		String generatedString = random.ints(leftLimit,rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();

		return generatedString;
	}
	
}
