package pl.gymkhana_gp.judge.utils;

import pl.gymkhana_gp.judge.comparators.FullMeasurementDtoComparator;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;

import java.util.ArrayList;
import java.util.List;

public class TimeMath {
	public static FullMeasurementDto getBestMeasurement(List<FullMeasurementDto> measurements) {
		List<FullMeasurementDto> sortedMeasurements = new ArrayList<>(measurements);
		sortedMeasurements.sort(new FullMeasurementDtoComparator());
		FullMeasurementDto bestFullMeasurement = sortedMeasurements.get(0);

		return bestFullMeasurement;
	}
}
