package net.sigusr2.thistoo.random;

import java.util.Random;

public class NSidedDie implements Die {
	private final int sides;
	private final Random source;

	public NSidedDie(int sides, Random source) {
		this.sides = sides;
		this.source = source;
	}

	/**
	 * Fairly rolls an n-sided die
	 */
	public int roll() {
		return source.nextInt(sides) + 1;
	}

	public String toString() {
		return String.format("D%d", sides);
	}
}
