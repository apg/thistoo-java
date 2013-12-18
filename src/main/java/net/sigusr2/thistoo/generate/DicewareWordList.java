package net.sigusr2.thistoo.generate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.Map;
import java.util.TreeMap;

/**
 * Implements a word list for Diceware
 *   @url(http://world.std.com/~reinhold/diceware.html)
 *
 */
public class DicewareWordList implements WordList {
	public final static int DICEWARE_WORD_LIST_SIZE = 7776;

	public enum Lang {
		EN("en"),
		ES("es"),
		DE("de");

		private String code;

		Lang(String code) {
			this.code = code;
		}

		public String code() {
			return this.code;
		}
	};

	/* overboard! */
	private static Map<Lang, DicewareWordList> wordLists = new TreeMap<Lang, DicewareWordList>();
	private String words[];
	private boolean initialized = false;
	private Lang language;

	private DicewareWordList(Lang language) {
		this.language = language;
	}

	public static DicewareWordList getLanguage(Lang lang) {
		DicewareWordList list = wordLists.get(lang);
		if (list == null) {
			list = new DicewareWordList(lang);
			wordLists.put(lang, list);
		}
		return list;
	}

	/**
	 * Gets a word from key. Keys in diceware are a string of 5 chars 1-6, the
	 * representing the faces of a 5 D6 rolls.
	 *
	 * As a result of this, we can treat this as a 5 digit, base 6 number, and do
	 * a simple array lookup.
	 */
	public String get(String key) {
		try {
			init();
		}
		catch (IOException e) {
			throw new RuntimeException("Word list could not be read: " +
																 e.toString());
		}

		byte bytes[] = key.getBytes();
		if (bytes.length != 5) {
			throw new RuntimeException("Got more than 5 digits");
		}

		// Decrement, to make it base 6.
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] <= '6' && bytes[i] >= '1') {
				bytes[i] -= 1;
			}
			else {
				throw new RuntimeException("Got a digit other than 1-6");
			}
		}

		final int index = Integer.parseInt(new String(bytes), 6);

		return words[index];
	}

	private void init() throws IOException {
		if (initialized) {
			return;
		}

		int lineno = 0;
		final String fileName = "/diceware." + language.code() + ".list";
		final URL resource = getClass().getResource(fileName);
		final InputStream in = resource.openStream();

		words = new String[DICEWARE_WORD_LIST_SIZE];

		BufferedReader reader = new BufferedReader(new InputStreamReader(in));

		String line = reader.readLine();
		while (line != null && lineno < DICEWARE_WORD_LIST_SIZE) {
			String bits[] = line.split(" ");
			if (bits.length == 2) {
				words[lineno] = bits[1];
			}
			else {
				throw new AssertionError(
					String.format("Invalid Diceware list failed at: {lineno=%d}\n", 
												lineno + 1));
			}
			lineno++;
			line = reader.readLine();
		}

		initialized = true;
	}
}
