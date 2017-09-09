package pl.gymkhana_gp.judge.presentation.views.printihelpersawt;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class WaitingListPrintableTest {
	private AbstractListPrintable abstractListPrintable = new WaitingListPrintable(null);

	@Test
	public void shouldReturnRightAlignedStringForInteger() {
		// given

		// when
		String result = abstractListPrintable.format(12, 4);

		// then
		assertThat(result, is("  12"));
	}

	@Test
	public void shouldReturnCutStringForInteger() {
		// given

		// when
		String result = abstractListPrintable.format(12345, 4);

		// then
		assertThat(result, is("1234"));
	}

	@Test
	public void shouldReturnRightAlignedStringForString() {
		// given

		// when
		String result = abstractListPrintable.format("AlaMaKota", 20);

		// then
		assertThat(result, is("           AlaMaKota"));
	}

	@Test
	public void shouldReturnCutStringForString() {
		// given

		// when
		String result = abstractListPrintable.format("AlaMaKotaAKotMaAleAleJakToTak", 20);

		// then
		assertThat(result, is("AlaMaKotaAKotMaAleAl"));
	}
}