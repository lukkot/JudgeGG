package pl.gymkhana_gp.judge.model.dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="time")
public class TimeDto {
	private static final String TIME_REGEX = "([0-9]+):([0-9]{2}).([0-9]{3})";
	private static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX);
	
	private static final String TIME_FORMAT = "%02d:%02d.%03d";
	
	private long timeMilliseconds;
	
	public TimeDto() { }
	
	public TimeDto(long timeMilliseconds) {
		setTime(timeMilliseconds);
	}
	
	public TimeDto(String timeString) {
		setTime(timeString);
	}

	@XmlElement(name="value")
	public String getTimeFormatted() {
		long minutes = timeMilliseconds / 1000 / 60;
		long seconds = timeMilliseconds / 1000 - minutes * 60;
		long milliseconds = timeMilliseconds - seconds * 1000 - minutes * 60 * 1000;
		return String.format(TIME_FORMAT, minutes, seconds, milliseconds);
	}

	public void setTimeFormatted(String timeString) {
		setTime(timeString);
	}
	
	public void setTime(String timeString) {
		Matcher matcher = TIME_PATTERN.matcher(timeString);
		if(matcher.groupCount() != 3) {
			throw new IllegalArgumentException(timeString + " != " + TIME_REGEX);
		}
		
		if(matcher.find()) {
			long minutes = Long.parseLong(matcher.group(1));
			long seconds = Long.parseLong(matcher.group(2));
			long milliseconds = Long.parseLong(matcher.group(3));
			this.timeMilliseconds = minutes * 60 * 1000 + seconds * 1000 + milliseconds;
		}
	}

	@XmlTransient
	public long getTimeMilliseconds() {
		return timeMilliseconds;
	}

	public void setTime(long timeMilliseconds) {
		this.timeMilliseconds = timeMilliseconds;
	}
}
