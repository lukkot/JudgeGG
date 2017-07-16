package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 04.07.17.
 */
class DoNotStart extends NotRegistered<DoNotStart> implements Comparable<TimeResult<?>> {

	DoNotStart() {
		super(TimeResultType.DO_NOT_START);
	}

	@Override
	public int compareTo(final TimeResult<?> second) {
		final int result;

		if ((second == null) || (second instanceof NotYetStarted)) {
			result = -1;
		} else if (second instanceof DoNotStart) {
			result = 0;
		} else {
			result = 1;
		}

		return result;
	}
}
