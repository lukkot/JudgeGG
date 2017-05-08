package pl.gymkhana_gp.judge.comparators;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pl.gymkhana_gp.judge.comparators.PlayerDtoComparator.ComparisonType;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

public class PlayerDtoComparatorTest {
	
	PlayerViewData p1;
	PlayerViewData p2;
	PlayerViewData p3;
	
	@Before
	public void before() {
		p1 = new PlayerViewData(1);
		p1.setStartNumber(1);
		p1.setTime1("00:00.001");
		p1.setTime2("00:00.001");
		
		p2 = new PlayerViewData(2);
		p2.setStartNumber(2);
		p2.setTime1("00:00.002");
		p2.setTime2("00:00.000");
		
		p3 = new PlayerViewData(3);
		p3.setStartNumber(3);
		p3.setTime1("00:00.001");
		p3.setPenalty1(1);
	}
	
	@Test
	public void shouldTestPlayerStartNumberOrder() {
		PlayerDtoComparator playerDtoComparator = new PlayerDtoComparator(ComparisonType.START_NUMBER);
		
		int resultP1P2 = playerDtoComparator.compare(p1, p2);
		int resultP2P1 = playerDtoComparator.compare(p2, p1);
		int resultP1P1 = playerDtoComparator.compare(p1, p1);
		
		assertTrue(resultP1P2 < 0);
		assertTrue(resultP2P1 > 0);
		assertTrue(resultP1P1 == 0);
	}
	
	@Test
	public void shouldTestPlayerRound1Order() {
		PlayerDtoComparator playerDtoComparator = new PlayerDtoComparator(ComparisonType.ROUND_1);
		
		int resultP1P2 = playerDtoComparator.compare(p1, p2);
		int resultP2P1 = playerDtoComparator.compare(p2, p1);
		int resultP1P1 = playerDtoComparator.compare(p1, p1);
		int resultP3P2 = playerDtoComparator.compare(p3, p2);
		
		assertTrue(resultP1P2 < 0);
		assertTrue(resultP2P1 > 0);
		assertTrue(resultP1P1 == 0);
		assertTrue(resultP3P2 > 0);
	}
	
	@Test
	public void shouldTestPlayerRoundBestOrder() {
		PlayerDtoComparator playerDtoComparator = new PlayerDtoComparator(ComparisonType.ROUND_BEST);
		
		int resultP1P2 = playerDtoComparator.compare(p1, p2);
		int resultP2P1 = playerDtoComparator.compare(p2, p1);
		int resultP1P1 = playerDtoComparator.compare(p1, p1);
		
		assertTrue(resultP1P2 > 0);
		assertTrue(resultP2P1 < 0);
		assertTrue(resultP1P1 == 0);
	}

}
