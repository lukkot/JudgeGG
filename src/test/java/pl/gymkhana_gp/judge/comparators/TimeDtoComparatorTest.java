package pl.gymkhana_gp.judge.comparators;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class TimeDtoComparatorTest {
	TimeDtoComparator timeDtoComparator;

	@Before
	public void before() {
		timeDtoComparator = new TimeDtoComparator();
	}

	@Test
	public void shouldFirstBeLowerThenSecondForBothTimeInMillis() {
		TimeDto timeDto1 = new TimeDto(5);
		TimeDto timeDto2 = new TimeDto(6);

		int result = timeDtoComparator.compare(timeDto1, timeDto2);

		assertTrue(result < 0);
	}

	@Test
	public void shouldDNFBeLowerThenDNS() {
		TimeDto timeDto1 = new TimeDto("DNF");
		TimeDto timeDto2 = new TimeDto("DNS");

		int result = timeDtoComparator.compare(timeDto1, timeDto2);

		assertTrue(result < 0);
	}

	@Test
	public void shouldDSQBeLowerThenDNF() {
		TimeDto timeDto1 = new TimeDto("DNF");
		TimeDto timeDto2 = new TimeDto("DSQ");

		int result = timeDtoComparator.compare(timeDto1, timeDto2);

		assertTrue(result > 0);
	}

	@Test
	public void shouldTimeInMillisBeLowerThenDSQ() {
		TimeDto timeDto1 = new TimeDto(58690);
		TimeDto timeDto2 = new TimeDto("DSQ");

		int result = timeDtoComparator.compare(timeDto1, timeDto2);

		assertTrue(result < 0);
	}
}
