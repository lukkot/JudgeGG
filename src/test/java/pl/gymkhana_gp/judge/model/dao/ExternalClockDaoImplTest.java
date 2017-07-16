package pl.gymkhana_gp.judge.model.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fazecast.jSerialComm.SerialPort;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class ExternalClockDaoImplTest {
	ExternalClockDaoImpl externalClockDaoImpl;
	ExternalClockDaoImpl externalClockDaoImplWithSpy;

	@Before
	public void setUp() {
		externalClockDaoImpl = new ExternalClockDaoImpl();
		externalClockDaoImplWithSpy = spy(externalClockDaoImpl);
	}

	@Test
	public void shouldReturnPortList() {
		doReturn(new SerialPort[] { mockSerialPortWithName("COM0"), mockSerialPortWithName("COM1") })
				.when(externalClockDaoImplWithSpy).getCommPorts();

		 List<String> results = externalClockDaoImplWithSpy.getPorts();
		
		 assertEquals(Arrays.asList("COM0", "COM1"), results);
	}

	private SerialPort mockSerialPortWithName(String name) {
		SerialPort serialPort = mock(SerialPort.class);
		when(serialPort.getSystemPortName()).thenReturn(name);
		return serialPort;
	}

	@Test
	public void shouldReturnTwoTimesOneAfterTheOther() {
		String time1 = "12:34,567";
		String time2 = "24:58,123";
		
		externalClockDaoImpl.serialPort = mockSerialPortWithName("COM0");
		
		doAnswer(new Answer<Integer>() {
			int index = 0;
			byte[] buffer = ("1.123\n" + time1 + "\n" + time2 + "\n12").getBytes();
			
			@Override
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				byte[] response = invocation.getArgument(0);
				System.arraycopy(buffer, index++, response, 0, 1);
				return 1;
			}
		}).when(externalClockDaoImpl.serialPort).readBytes(any(), anyLong());
		
		TimeDto result1 = externalClockDaoImpl.readTime();
		TimeDto result2 = externalClockDaoImpl.readTime();
		
		assertEquals(time1, result1.getTimeFormatted());
		assertEquals(time2, result2.getTimeFormatted());
	}
}
