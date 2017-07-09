package pl.gymkhana_gp.judge.model.dto;

import pl.gymkhana_gp.judge.model.dto.time.TimeResult;
import pl.gymkhana_gp.judge.model.dto.time.TimeResultFactory;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="time")
public class TimeDto {
	private TimeResult timeResult = TimeResultFactory.obtainTimeResult(null);
	
	public TimeDto() {	}
	
	public TimeDto(long timeMilliseconds) {
		setTime(timeMilliseconds);
	}
	
	public TimeDto(String timeString) {
		setTime(timeString);
	}

	public TimeDto(final TimeResult timeResult) {
		this.timeResult = timeResult;
	}

	@XmlElement(name="value")
	public String getTimeFormatted() {
		return (timeResult != null) ? timeResult.getTime() : "";
	}

	public void setTimeFormatted(final String timeResult) {
		this.timeResult = TimeResultFactory.obtainTimeResult(timeResult);
	}

	public void setTime(final String timeString) {
		timeResult = TimeResultFactory.obtainTimeResult(timeString);
	}

	public TimeResult getTime() {
		return timeResult;
	}

	@XmlTransient
	public long getTimeMilliseconds() {
		return (timeResult != null) ? timeResult.getTimeAsMillis() : 0L;
	}

	public void setTime(long timeMilliseconds) {
		timeResult = TimeResultFactory.obtainTimeResult(timeMilliseconds);
	}

}
