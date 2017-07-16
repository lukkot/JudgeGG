package pl.gymkhana_gp.judge.model.dto.time;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by filus on 08.07.17.
 */
public class TimeResultComparatorTest {

	@Test
	public void shouldTwoDNSBeEqual() {
		//given
		final TimeResult<?> first = TimeResultFactory.obtainTimeResult("DNS");
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("DNS");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertEquals(0, result);
	}

	@Test
	public void shouldDNSBeLessThanNull() {
		//given
		final TimeResult<?> first = null;
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("DNS");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertTrue(result < 0);
	}

	@Test
	public void shouldDNSBeGreaterThanDNF() {
		//given
		final TimeResult<?> first = TimeResultFactory.obtainTimeResult("DNS");
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("DNF");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertTrue(result > 0);
	}

	@Test
	public void shouldDisqualifiedBeLessThanDNS() {
		//given
		final TimeResult<?> first = TimeResultFactory.obtainTimeResult("DSQ");
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("DNS");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertTrue(result < 0);
	}

	@Test
	public void shouldTwoDNFBeEqual() {
		//given
		final TimeResult<?> first = TimeResultFactory.obtainTimeResult("DNF");
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("DNF");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertEquals(0, result);
	}

	@Test
	public void shouldRegisteredBeLessThanDisqualified() {
		//given
		final TimeResult<?> first = TimeResultFactory.obtainTimeResult("DSQ");
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("01:23,45");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertTrue(result > 0);
	}

	@Test
	public void shouldTwoRegisteredBeCompare() {
		//given
		final TimeResult<?> first = TimeResultFactory.obtainTimeResult("01:23,45");
		final TimeResult<?> second = TimeResultFactory.obtainTimeResult("01:23,44");
		final TimeResultComparator comparator = new TimeResultComparator();

		//when
		int result = comparator.compare(first, second);

		//then
		assertTrue(result > 0);
	}

}