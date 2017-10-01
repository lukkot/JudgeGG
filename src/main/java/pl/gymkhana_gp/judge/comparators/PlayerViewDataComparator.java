package pl.gymkhana_gp.judge.comparators;

import java.util.Comparator;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

public class PlayerViewDataComparator implements Comparator<PlayerViewData> {
	public enum ComparisonType {
		START_NUMBER, ROUND_1, ROUND_BEST
	};

	private final ComparisonType comparisonType;
	private final int direction;

	public PlayerViewDataComparator(ComparisonType comparisonType) {
		this(comparisonType, false);
	}

	public PlayerViewDataComparator(ComparisonType comparisonType, boolean reversed) {
		this.comparisonType = comparisonType;
		
		direction = reversed ? -1 : 1;
	}

	@Override
	public int compare(PlayerViewData p1, PlayerViewData p2) {
		int result;
		
		switch (comparisonType) {
		case START_NUMBER:
			result = compareStartNumber(p1, p2);
			break;

		case ROUND_1:
			result = compareRound1(p1, p2);
			break;

		case ROUND_BEST:
			result = compareRoundBest(p1, p2);
			break;

		default:
			throw new UnsupportedOperationException("Not implemented yet (" + comparisonType.toString() + ")");
		}
		
		return direction * result;
	}

	private int compareStartNumber(PlayerViewData p1, PlayerViewData p2) {
		return Integer.compare(p1.getStartNumber().get(), p2.getStartNumber().get());
	}

	private int compareRound1(PlayerViewData p1, PlayerViewData p2) {
		FullMeasurementDto fullMeasurementDto1 = FullMeasurementDto.getInstanceOrNull(p1.getTime1().get(),
				p1.getPenalty1().get());
		FullMeasurementDto fullMeasurementDto2 = FullMeasurementDto.getInstanceOrNull(p2.getTime1().get(),
				p2.getPenalty1().get());
		return (new FullMeasurementDtoComparator()).compare(fullMeasurementDto1, fullMeasurementDto2);
	}

	private int compareRoundBest(PlayerViewData p1, PlayerViewData p2) {
		FullMeasurementDto fullMeasurementDto1 = FullMeasurementDto.getInstanceOrNull(p1.getTimeBest().get(),
				p1.getPenaltyBest().get());
		FullMeasurementDto fullMeasurementDto2 = FullMeasurementDto.getInstanceOrNull(p2.getTimeBest().get(),
				p2.getPenaltyBest().get());
		return (new FullMeasurementDtoComparator()).compare(fullMeasurementDto1, fullMeasurementDto2);
	}

}
