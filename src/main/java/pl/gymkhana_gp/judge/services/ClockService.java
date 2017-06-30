package pl.gymkhana_gp.judge.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

@Component
public class ClockService implements Runnable {
	
	@Autowired
	SerialPort serialPort;
	
	private volatile TimeDto currentTime = new TimeDto(0);
	private Thread readingThread;
	private volatile boolean readData;
	
	Set<IClockObserver> observers = new HashSet<>();

	public ClockService() {
		startReading();
	}
	
	@Override
	public void run() {
		while(readData) {
			currentTime.setTime(currentTime.getTimeMilliseconds() + 100);
			notifyObservers();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void startReading() {
		if (readingThread != null) {
			return;
		}

		readData = true;
		readingThread = new Thread(this);
		readingThread.start();
	}

	public synchronized void stopReading() {
		readData = false;
		try {
			readingThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		readingThread = null;
	}

	public TimeDto getTime() {
		return new TimeDto(currentTime.getTimeMilliseconds());
	}
	
	public synchronized void addClockListener(IClockObserver observer) {
		observers.add(observer);
	}
	
	public synchronized void removeClockListener(IClockObserver observer) {
		observers.remove(observer);
	}
	
	private synchronized void notifyObservers() {
		for(IClockObserver observer : observers) {
			observer.onClockUpdate(getTime());
		}
	}
}
