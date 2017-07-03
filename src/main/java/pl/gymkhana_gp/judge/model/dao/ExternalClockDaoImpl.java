package pl.gymkhana_gp.judge.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

@Component
public class ExternalClockDaoImpl {
	protected SerialPort serialPort;
	protected TimeDto currentTime = new TimeDto();
	protected StringBuffer buffer = new StringBuffer();
	
	public List<String> getPorts() {
		List<String> ports = new ArrayList<>();
		
		for(SerialPort port : getCommPorts()) {
			ports.add(port.getSystemPortName());
		}
		
		return ports;
	}
	
	public void open(String serialPortName) {
		serialPort = SerialPort.getCommPort(serialPortName);
		serialPort.openPort();
	}
	
	public void close() {
		serialPort.closePort();
		serialPort = null;
	}
	
	public TimeDto readTime() {
		TimeDto time = null;
		byte[] byteBuffer = new byte[1];
		while(true) {
			serialPort.readBytes(byteBuffer, 1);
			
			if(byteBuffer[0] != '\n') {
				buffer.append((char)byteBuffer[0]);
			} else {
				time = translateBuffer();
				if(time != null) {
					break;
				}
			}
		}
		return time;
	}
	
	private TimeDto translateBuffer() {
		String stringTime = buffer.toString();
		buffer.setLength(0);
		
		if(TimeDto.isValidTime(stringTime)) {
			return new TimeDto(stringTime);
		} else {
			return null;
		}
	}
	
	protected SerialPort[] getCommPorts() {
		return SerialPort.getCommPorts();
	}
}
