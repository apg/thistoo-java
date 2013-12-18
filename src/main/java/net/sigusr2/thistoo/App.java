package net.sigusr2.thistoo;

import net.sigusr2.thistoo.generate.Diceware;
import net.sigusr2.thistoo.generate.DicewareWordList;
import net.sigusr2.thistoo.random.D6;

import java.security.SecureRandom;
import java.util.Random;

public class App {
	public static void main( String[] args ) {
		Random rand = new SecureRandom();

		Diceware dw = new Diceware(DicewareWordList.getLanguage(DicewareWordList.Lang.EN), new D6(rand));

		for (String word : dw.generate(5)) {
			System.out.print(word + " ");
		}
		System.out.println("");
	}
}
