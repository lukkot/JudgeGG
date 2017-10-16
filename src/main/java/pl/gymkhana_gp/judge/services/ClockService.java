package pl.gymkhana_gp.judge.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.gymkhana_gp.judge.model.dao.ExternalClockDaoImpl;
import pl.gymkhana_gp.judge.model.dao.ExternalClockSettings;
import pl.gymkhana_gp.judge.model.dto.TimeDto;

@Component
public class ClockService implements Runnable {

	private static final Logger LOG = LogManager.getLogger(ClockService.class);

	@Autowired
	ExternalClockDaoImpl externalClockDaoImpl;
	
	private ExternalClockSettings externalClockSettings = new ExternalClockSettings();
	private volatile TimeDto currentTime = new TimeDto(0);
	private Thread readingThread;
	private volatile boolean readData;
	
	private Set<IClockObserver> observers = new HashSet<>();
	
	@Override
	public void run() {
		externalClockDaoImpl.open(externalClockSettings);
		
		while(readData) {
			TimeDto time = externalClockDaoImpl.readTime();
			if(time != null) {
				currentTime = time;
				notifyObservers();
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				LOG.warn("Waiting during reading from external clock interrupted.", e);
			}
		}

		externalClockDaoImpl.readTime();
		externalClockDaoImpl.close();
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
		if(readingThread != null) {
			try {
				readingThread.join();
			} catch (InterruptedException e) {
				LOG.warn("Error during stopping reading from external clock: error while joining reading thread.", e);
			}
			readingThread = null;
		}
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

	public void setExternalClockSettings(ExternalClockSettings externalClockSettings) {
		if(externalClockSettings == null) {
			throw new IllegalArgumentException();
		}
		
		this.externalClockSettings = externalClockSettings;
	}
	
	public List<String> getSerialPorts() {
		return externalClockDaoImpl.getPorts();
	}
}
