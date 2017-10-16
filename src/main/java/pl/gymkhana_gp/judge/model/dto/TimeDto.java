package pl.gymkhana_gp.judge.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import pl.gymkhana_gp.judge.model.dto.time.TimeResult;
import pl.gymkhana_gp.judge.model.dto.time.TimeResultFactory;

@XmlRootElement(name = "time")
public class TimeDto {
	private TimeResult<?> timeResult = TimeResultFactory.obtainTimeResult(null);

	public TimeDto() {	}

	public TimeDto(long timeMilliseconds) {
		setTime(timeMilliseconds);
	}

	public TimeDto(String timeString) {
		setTime(timeString);
	}

	public TimeDto(final TimeResult<?> timeResult) {
		this.timeResult = timeResult;
	}

	@Override
	public int hashCode() {
		return Long.hashCode(getTimeMilliseconds());
	}

	@Override
	public boolean equals(Object obj) {
		TimeDto timeDto2 = (TimeDto) obj;
		if (timeDto2 == null) {
			return false;
		} else {
			return getTimeMilliseconds() == timeDto2.getTimeMilliseconds();
		}
	}

	@XmlElement(name = "value")
	public String getTimeFormatted() {
		return (timeResult != null) ? timeResult.getTime() : "";
	}

	public void setTimeFormatted(final String timeResult) {
		this.timeResult = TimeResultFactory.obtainTimeResult(timeResult);
	}

	public void setTime(final String timeString) {
		timeResult = TimeResultFactory.obtainTimeResult(timeString);
	}

	public TimeResult<?> getTime() {
		return timeResult;
	}

	@XmlTransient
	public long getTimeMilliseconds() {
		return (timeResult != null) ? timeResult.getTimeAsMillis() : 0L;
	}

	public void setTime(long timeMilliseconds) {
		timeResult = TimeResultFactory.obtainTimeResult(timeMilliseconds);
	}

	public static boolean isValidTime(String timeString) {
		return TimeResultFactory.isValidTime(timeString);
	}

}
