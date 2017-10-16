package pl.gymkhana_gp.judge.utils;

import pl.gymkhana_gp.judge.comparators.FullMeasurementDtoComparator;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;

import java.util.ArrayList;
import java.util.List;

public class TimeMath {
	public static FullMeasurementDto getBestMeasurement(List<FullMeasurementDto> measurements) {
		List<FullMeasurementDto> sortedMeasurements = new ArrayList<>(measurements);
		sortedMeasurements.sort(new FullMeasurementDtoComparator());

		return sortedMeasurements.get(0);
	}

	public static long getDeltaInSeconds(FullMeasurementDto measurement1, FullMeasurementDto measurement2) {
		if(measurement1 == null || measurement2 == null) {
			return 0;
		} else {
			return (measurement1.getTimeWithPenalty().getTimeMilliseconds() - measurement2.getTimeWithPenalty().getTimeMilliseconds()) / 1000;
		}
	}
}
