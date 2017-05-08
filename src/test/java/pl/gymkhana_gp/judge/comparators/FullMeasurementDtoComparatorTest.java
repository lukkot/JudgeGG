package pl.gymkhana_gp.judge.comparators;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class FullMeasurementDtoComparatorTest {

	FullMeasurementDtoComparator fullMeasurementDtoComparator;
	
	@Before
	public void before() {
		fullMeasurementDtoComparator = new FullMeasurementDtoComparator();
	}
	
	@Test
	public void shouldBeLowerTimeBeBeforeHigher() {
		FullMeasurementDto fullMeasurementDto1 = new FullMeasurementDto(new TimeDto(5000), 0);
		FullMeasurementDto fullMeasurementDto2 = new FullMeasurementDto(new TimeDto(6000), 0);
		
		int result = fullMeasurementDtoComparator.compare(fullMeasurementDto1, fullMeasurementDto2);
		
		assertTrue(result < 0);
	}
	
	@Test
	public void shouldBeLowerWithHighPenaltyTimeBeAfterHigherTimeWithLowPenalty() {
		FullMeasurementDto fullMeasurementDto1 = new FullMeasurementDto(new TimeDto(5000), 3);
		FullMeasurementDto fullMeasurementDto2 = new FullMeasurementDto(new TimeDto(6000), 1);
		
		int result = fullMeasurementDtoComparator.compare(fullMeasurementDto1, fullMeasurementDto2);
		
		assertTrue(result > 0);
	}
	
	@Test
	public void shouldBeNullBeHigherValue() {
		FullMeasurementDto fullMeasurementDto1 = new FullMeasurementDto(new TimeDto(5000), 3);
		
		int result1 = fullMeasurementDtoComparator.compare(fullMeasurementDto1, null);
		int result2 = fullMeasurementDtoComparator.compare(null, fullMeasurementDto1);
		
		assertTrue(result1 < 0);
		assertTrue(result2 > 0);
	}
	
	@Test
	public void shouldTwoNullsBeEqual() {
		
		int result1 = fullMeasurementDtoComparator.compare(null, null);
		
		assertTrue(result1 == 0);
	}

}
