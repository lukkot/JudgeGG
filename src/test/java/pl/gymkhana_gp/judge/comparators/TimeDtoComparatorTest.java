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
	public void shouldFirstBeLowerThenSecond() {
		TimeDto timeDto1 = new TimeDto(5);
		TimeDto timeDto2 = new TimeDto(6);
		
		int result = timeDtoComparator.compare(timeDto1, timeDto2);
		
		assertTrue(result < 0);
	}

}
