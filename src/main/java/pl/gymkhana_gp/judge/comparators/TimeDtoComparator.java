package pl.gymkhana_gp.judge.comparators;

import java.util.Comparator;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public class TimeDtoComparator implements Comparator<TimeDto> {

	@Override
	public int compare(TimeDto timeDto1, TimeDto timeDto2) {
		// TODO Auto-generated method stub
		return Long.compare(timeDto1.getTimeMilliseconds(), timeDto2.getTimeMilliseconds());
	}

}
