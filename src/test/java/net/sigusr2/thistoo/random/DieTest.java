package net.sigusr2.thistoo.random;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DieTest {
	private Random source;

	@Before
	public void setUp() {
		source = mock(Random.class);
	}

	@Test
	public void testNSidedDie() {
		// Ensure that there are no 0's returned, since
		// 0 is an invalid die roll on sane dice.
		NSidedDie underTest = new NSidedDie(10, source);
		for (int i = 0; i < 10; i++) {
			when(source.nextInt(10)).thenReturn(i);
			assertThat(underTest.roll(), is(i+1));
		}
	}

	@Test
	public void testD6() {
		D6 underTest = new D6(source);
		for (int i = 0; i < 6; i++) {
			when(source.nextInt(6)).thenReturn(i);
			assertThat(underTest.roll(), is(i+1));
		}
	}
}
