package net.sigusr2.thistoo.generate;

import net.sigusr2.thistoo.random.D6;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DicewareTest {
	private D6 die;

	@Before
	public void setUp() {
		die = mock(D6.class);
	}

	@Test
	public void testDiceware() {
		DicewareWordList en = mock(DicewareWordList.class);
		Diceware underTest = new Diceware(en, die);
		when(die.roll()).thenReturn(1);

		when(en.get("11111")).thenReturn("hello");
		assertThat(underTest.generate(1).get(0), is("hello"));
	}
}
