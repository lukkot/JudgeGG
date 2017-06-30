package pl.gymkhana_gp.judge.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fazecast.jSerialComm.SerialPort;

import pl.gymkhana_gp.judge.model.dto.TimeDto;
import pl.gymkhana_gp.judge.services.ClockService;

public class ExternalClockDaoImplTest {
	ClockService externalClockDaoImpl;
	
	@Before
	public void setUp() throws Exception {
		
		externalClockDaoImpl = new ClockService();
//		SerialPort comPort = SerialPort.getCommPorts()[1];
//		comPort.openPort();
//		externalClockDaoImpl.serialPort = mock(SerialPort.class);
//		when(serialPort.)
	}
	
//	private SerialPort mockSerialPort() {
//		SerialPort serialPort;
//	}
//	
//	private void mockSerialPortStatic() {
//		PowerMockito.mockStatic(SerialPort.class);
//		when(SerialPort.getCommPorts()).thenReturn();
//	}

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
