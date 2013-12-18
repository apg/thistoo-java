package net.sigusr2.thistoo.generate;

import net.sigusr2.thistoo.random.D6;


import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static net.sigusr2.thistoo.generate.DicewareWordList.Lang;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
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

	@Test
	public void testWordLists() {
		for (Lang l : Lang.values()) {
			DicewareWordList wl = DicewareWordList.getLanguage(l);
			for (int i = 0; i < DicewareWordList.DICEWARE_WORD_LIST_SIZE; i++) {
				String diceroll = toDice(i);
				assertNotNull(diceroll);
			}
		}
	}

	@Test
	public void testToDice() {
		assertThat(toDice(0), is("11111"));
		assertThat(toDice(7775), is("66666"));
	}

	public static String toDice(int n) {
		byte digits[] = new byte[] {
			'1', '1', '1', '1', '1'
		};

		int i = 4;
		int r;
		while (n > 0) {
			r = n % 6;
			n = n / 6;
			digits[i--] = (byte)('1' + r);
		}

		return new String(digits);
	}

}
