package pl.gymkhana_gp.judge.model.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;

import pl.gymkhana_gp.judge.model.dto.time.TimeResult;

@XmlRootElement
public class FullMeasurementDto {
	
	public static FullMeasurementDto getInstanceOrNull(final String time, final long penalty) {
		if(StringUtils.isBlank(time)) {
			return null;
		} else {
			return new FullMeasurementDto(new TimeDto(time), penalty);
		}
	}
	
	private TimeDto time = new TimeDto();
	private long penalty;
	
	public FullMeasurementDto() { }
	
	public FullMeasurementDto(TimeDto time, long penalty) {
		this.time = time;
		this.penalty = penalty;
	}

	@XmlElement
	public TimeDto getTime() {
		return time;
	}

	public void setTime(TimeDto time) {
		this.time = time;
	}

	@XmlElement
	public long getPenalty() {
		return penalty;
	}

	public void setPenalty(long penalty) {
		this.penalty = penalty;
	}
	
	@XmlTransient
	public TimeDto getTimeWithPenalty() {
		final TimeResult<?> time = this.time.getTime();
		final TimeResult<?> fullTime = time.addPenalty(penalty);
		return new TimeDto(fullTime);
	}
	
}
