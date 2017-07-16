package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 04.07.17.
 */
class DoNotFinish extends NotRegistered<DoNotFinish> implements Comparable<TimeResult<?>> {

	protected DoNotFinish() {
		super(TimeResultType.DO_NOT_FINISH);
	}

	@Override
	public int compareTo(final TimeResult<?> second) {
		final int result;

		if ((second == null) || (second instanceof NotYetStarted) || (second instanceof DoNotStart)) {
			result = -1;
		} else if (second instanceof DoNotFinish) {
			result = 0;
		} else {
			result = 1;
		}

		return result;

	}
}
