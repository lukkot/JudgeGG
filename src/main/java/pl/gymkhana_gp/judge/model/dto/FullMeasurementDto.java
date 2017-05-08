package pl.gymkhana_gp.judge.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class FullMeasurementDto {
	
	public static FullMeasurementDto getInstanceOrNull(String time, long penalty) {
		if(time == null || "".equals(time)) {
			return null;
		} else {
			return new FullMeasurementDto(new TimeDto(time), penalty);
		}
	}
	
	private TimeDto time = new TimeDto();
	
	private long penaltyInSeconds;
	
	public FullMeasurementDto() { }
	
	public FullMeasurementDto(TimeDto time, long penalty) {
		this.time = time;
		this.penaltyInSeconds = penalty;
	}

	@XmlElement
	public TimeDto getTime() {
		return time;
	}

	public void setTime(TimeDto time) {
		this.time = time;
	}

	@XmlElement
	public long getPenaltyInSeconds() {
		return penaltyInSeconds;
	}

	public void setPenaltyInSeconds(long penalty) {
		this.penaltyInSeconds = penalty;
	}
	
	@XmlTransient
	public TimeDto getTimeWithPenalty() {
		return new TimeDto(time.getTimeMilliseconds() + penaltyInSeconds * 1000);
	}
	
}
