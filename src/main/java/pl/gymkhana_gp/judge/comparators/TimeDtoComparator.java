package pl.gymkhana_gp.judge.comparators;

import java.util.Comparator;
import java.util.Objects;

import pl.gymkhana_gp.judge.model.dto.TimeDto;
import pl.gymkhana_gp.judge.model.dto.time.TimeResult;
import pl.gymkhana_gp.judge.model.dto.time.TimeResultComparator;

public class TimeDtoComparator implements Comparator<TimeDto> {

	@Override
	public int compare(TimeDto timeDto1, TimeDto timeDto2) {
		final TimeResult firstTime = timeDto1.getTime();
		final TimeResult secondTime = timeDto2.getTime();

		return Objects.compare(firstTime, secondTime, new TimeResultComparator());
	}

}
