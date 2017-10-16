package pl.gymkhana_gp.judge.services;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import pl.gymkhana_gp.judge.model.dao.ExternalClockDaoImpl;
import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class ClockServiceTest {
	ClockService clockService;
	ExternalClockDaoImpl externalClockDaoImpl;

	@Before
	public void setUp() throws Exception {
		clockService = new ClockService();
		
		externalClockDaoImpl = mock(ExternalClockDaoImpl.class);
		
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldReturnDifferentTimeWhileStarted() throws InterruptedException {
		doNothing().when(externalClockDaoImpl).open(any());
		doNothing().when(externalClockDaoImpl).close();
		when(externalClockDaoImpl.readTime()).then(new Answer<TimeDto>() {
			int index;
			
			@Override
			public TimeDto answer(InvocationOnMock invocation) throws Throwable {
				return new TimeDto(index++);
			}
		});
		
		clockService.externalClockDaoImpl = externalClockDaoImpl;
		
//		clockService.stopReading();
		TimeDto time1 = clockService.getTime();
		Thread.sleep(300);
		TimeDto time2 = clockService.getTime();
		clockService.startReading();
		Thread.sleep(300);
		TimeDto time3 = clockService.getTime();
		clockService.stopReading();
		Thread.sleep(300);
		TimeDto time4 = clockService.getTime();

		assertEquals(true, time1.equals(time2));
		assertEquals(false, time2.equals(time3));
		assertEquals(true, time3.equals(time4));
	}

}
