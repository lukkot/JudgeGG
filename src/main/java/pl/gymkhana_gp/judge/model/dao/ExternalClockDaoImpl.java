package pl.gymkhana_gp.judge.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

@Component
public class ExternalClockDaoImpl {
	@Autowired
	protected SerialPort serialPort;

	protected StringBuffer buffer = new StringBuffer();

	public List<String> getPorts() {
		List<String> ports = new ArrayList<>();

		for (SerialPort port : getCommPorts()) {
			ports.add(port.getSystemPortName());
		}

		return ports;
	}

	public void open(ExternalClockSettings externalClockSettings) {
		serialPort = SerialPort.getCommPort(externalClockSettings.getPortName());
		
		serialPort.setBaudRate(externalClockSettings.getBaudrate());
		serialPort.setParity(externalClockSettings.getParity());
		serialPort.setNumDataBits(externalClockSettings.getDataBits());
		serialPort.setNumStopBits(externalClockSettings.getStopBits());
		
		serialPort.openPort();
		serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
	}

	public void close() {
		serialPort.closePort();
		serialPort = null;
	}

	public TimeDto readTime() {
		TimeDto time = null;
		byte[] byteBuffer = new byte[1];
		boolean read = true;
		
		try {
			while(read) {
				int readBytes = serialPort.readBytes(byteBuffer, 1);
				
				if(readBytes > 0) {
					System.out.println(readBytes + ": " + (char)byteBuffer[0] + "(" + (int)byteBuffer[0] + ")" + "|");
				}
				
				if(readBytes <= 0) {
					read = false;
				} if(byteBuffer[0] != '\n') {
					buffer.append((char)byteBuffer[0]);
					if(buffer.length() > "MM.ss.SSS".length()) {
						buffer.deleteCharAt(0);
					}
				} else {
					System.out.print("Decoding... ");
					time = translateBuffer();
					if(time != null) {
						read = false;
						System.out.println(time.getTimeFormatted());
					} else {
						System.out.println("failed.");
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Rozmiar bufora czytającego z zegara: " + buffer.length());
			e.printStackTrace();
		}
		
		return time;
	}

	private TimeDto translateBuffer() {
		String stringTime = buffer.toString();
		buffer.setLength(0);

		if (TimeDto.isValidTime(stringTime)) {
			return new TimeDto(stringTime);
		} else {
			return null;
		}
	}

	protected SerialPort[] getCommPorts() {
		return SerialPort.getCommPorts();
	}
}
