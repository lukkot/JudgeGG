package pl.gymkhana_gp.judge.model.dao;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fazecast.jSerialComm.SerialPort;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class ExternalClockDaoImplTest {
	ExternalClockDaoImpl externalClockDaoImpl;
	SerialPort comPort;
	
	@Before
	public void setUp() throws Exception {
		externalClockDaoImpl = new ExternalClockDaoImpl();
//		SerialPort comPort = SerialPort.getCommPorts()[1];
//		comPort.openPort();
	}

	@After
	public void tearDown() throws Exception {
//		comPort.closePort();
	}

	@Test
	public void shouldReturnDifferentTimeWhileStarted() throws InterruptedException {
		externalClockDaoImpl.stopReading();
		TimeDto time1 = externalClockDaoImpl.getTime();
		Thread.sleep(300);
		TimeDto time2 = externalClockDaoImpl.getTime();
		externalClockDaoImpl.startReading();
		Thread.sleep(300);
		TimeDto time3 = externalClockDaoImpl.getTime();
		externalClockDaoImpl.stopReading();
		Thread.sleep(300);
		TimeDto time4 = externalClockDaoImpl.getTime();

		assertEquals(true, time1.equals(time2));
		assertEquals(false, time2.equals(time3));
		assertEquals(true, time3.equals(time4));
	}

}
