package pl.gymkhana_gp.judge.model.dto.time;

import java.util.Comparator;

/**
 * Created by filus on 07.07.17.
 */
public class TimeResultComparator implements Comparator<TimeResult<?>> {

	@Override
	public int compare(final TimeResult<?> first, final TimeResult<?> second) {
		final int result;

		if (first == null) {
			result = ((second == null) || (second instanceof NotYetStarted)) ? 0 : -1;
		} else if (first instanceof NotYetStarted) {
			result = compareNotYetStarted((NotYetStarted) first, second);
		} else 	if (first instanceof DoNotStart) {
			result = compareDoNotStart((DoNotStart) first, second);
		} else if (first instanceof DoNotFinish) {
			result = compareDoNotFinish((DoNotFinish) first, second);
		} else if (first instanceof Disqualified) {
			result = compareDisqualified((Disqualified) first, second);
		} else {
			result = compareRegistered((Registered)first, second);
		}

		return result;
	}

	private int compareNotYetStarted(final NotYetStarted first, final TimeResult<?> second) {
		return first.compareTo(second);
	}

	private int compareRegistered(final Registered first, final TimeResult<?> second) {
		return first.compareTo(second);
	}

	private int compareDisqualified(final Disqualified first, final TimeResult<?> second) {
		return first.compareTo(second);
	}

	private int compareDoNotFinish(final DoNotFinish first, final TimeResult<?> second) {
		return first.compareTo(second);
	}

	private int compareDoNotStart(final DoNotStart first, final TimeResult<?> second) {
		return first.compareTo(second);
	}
}
