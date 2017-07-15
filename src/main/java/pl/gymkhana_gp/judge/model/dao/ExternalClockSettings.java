package pl.gymkhana_gp.judge.model.dao;

import com.fazecast.jSerialComm.SerialPort;

public class ExternalClockSettings {
	private String portName;
	private int baudrate;
	private int parity;
	private int dataBits;
	private int stopBits;
	
	public ExternalClockSettings() {
		portName = "";
		baudrate = 57600;
		parity = SerialPort.NO_PARITY;
		dataBits = 8;
		stopBits = 1;
	}

	public ExternalClockSettings(String portName, int baudrate, int parity, int dataBits, int stopBits) {
		this.portName = portName;
		this.baudrate = baudrate;
		this.parity = parity;
		this.dataBits = dataBits;
		this.stopBits = stopBits;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public int getBaudrate() {
		return baudrate;
	}

	public void setBaudrate(int baudrate) {
		this.baudrate = baudrate;
	}

	public int getParity() {
		return parity;
	}

	public void setParity(int parity) {
		this.parity = parity;
	}

	public int getDataBits() {
		return dataBits;
	}

	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	public int getStopBits() {
		return stopBits;
	}

	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}
}
