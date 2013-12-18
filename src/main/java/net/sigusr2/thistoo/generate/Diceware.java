package net.sigusr2.thistoo.generate;

import net.sigusr2.thistoo.random.D6;

import java.util.ArrayList;
import java.util.List;

public class Diceware implements Generator {
	private WordList wordList;
	private D6 dice;

	public Diceware(DicewareWordList wordList, D6 dice) {
		this.wordList = wordList;
		this.dice = dice;
	}

	public List<String> generate(int size) {
		List<String> words = new ArrayList<String>(size);

		for (int i = 0; i < size; i++) {
			words.add(nextWord());
		}

		return words;
	}

	private String nextWord() {
		byte rolls[] = new byte[5];
		// Securely roll the dice, then lookup the word
		for (int i = 0; i < 5; i++) {
			rolls[i] = (byte)(dice.roll() + '0');
		}

		final String key = new String(rolls);
		return wordList.get(key);
	}
}
