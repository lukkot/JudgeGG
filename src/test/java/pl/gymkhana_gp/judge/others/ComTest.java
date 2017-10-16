package pl.gymkhana_gp.judge.others;

import org.junit.Test;

import com.fazecast.jSerialComm.SerialPort;

public class ComTest {

	@Test
	public void test() {
		SerialPort[] ports = SerialPort.getCommPorts();
		for(SerialPort port : ports) {
			System.out.println(port.getDescriptivePortName());
		}
	}
}
