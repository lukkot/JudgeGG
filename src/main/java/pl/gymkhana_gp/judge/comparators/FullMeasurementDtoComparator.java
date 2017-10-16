package pl.gymkhana_gp.judge.comparators;

import java.util.Comparator;
import java.util.Objects;

import org.springframework.stereotype.Component;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;

@Component
public class FullMeasurementDtoComparator implements Comparator<FullMeasurementDto> {

	@Override
	public int compare(FullMeasurementDto fullMeasurementDto1, FullMeasurementDto fullMeasurementDto2) {
		if (fullMeasurementDto1 == null && fullMeasurementDto2 == null) {
			return 0;
		} else if (fullMeasurementDto1 == null) {
			return Integer.MAX_VALUE;
		} else if (fullMeasurementDto2 == null) {
			return Integer.MIN_VALUE;
		}

		final int result = Objects.compare(fullMeasurementDto1.getTimeWithPenalty(), fullMeasurementDto2.getTimeWithPenalty(), new TimeDtoComparator());

		return result;

	}

}
