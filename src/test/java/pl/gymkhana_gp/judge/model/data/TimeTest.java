package pl.gymkhana_gp.judge.model.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class TimeTest {

	@Test
	public void shouldSetTimeFromString() {
		TimeDto timeDto = new TimeDto();

		timeDto.setTime("123:45.678");
		long result = timeDto.getTimeMilliseconds();

		assertEquals(7425678L, result);
	}

	@Test
	public void shouldReturnTimeFormattedAsString() {
		TimeDto timeDto = new TimeDto();
		timeDto.setTime(1*60*1000 + 1*1000 + 1);

		String result = timeDto.getTimeFormatted();
		assertEquals("01:01,001", result);
	}

	@Test
	public void shouldReturnLongTimeFormattedAsString() {
		TimeDto timeDto = new TimeDto();
		timeDto.setTime(101*60*1000 + 1*1000 + 1);

		String result = timeDto.getTimeFormatted();

		assertEquals("101:01,001", result);
	}

	@Test
	public void shouldReturnDNSAsStringValueOfDNS() {
		//given
		final TimeDto timeDto = new TimeDto();
		final String expected = "DNS";
		timeDto.setTime(expected);

		//when
		final String actual = timeDto.getTimeFormatted();

		//then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnDSQAsStringValueOfDSQ() {
		//given
		final TimeDto timeDto = new TimeDto();
		final String expected = "DSQ";
		timeDto.setTime(expected);

		//when
		final String actual = timeDto.getTimeFormatted();

		//then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnDNFAsStringValueOfDNF() {
		//given
		final TimeDto timeDto = new TimeDto();
		final String expected = "DNF";
		timeDto.setTime(expected);

		//when
		final String actual = timeDto.getTimeFormatted();

		//then
		assertEquals(expected, actual);
	}

	@Test
	public void shouldReturnTimeAsStringValueOfTime() {
		//given
		final TimeDto timeDto = new TimeDto();
		final String given = "03:45,23";

		timeDto.setTime(given);

		final String expected = given + 0;

		//when
		final String actual = timeDto.getTimeFormatted();

		//then
		assertEquals(expected, actual);
	}
}
