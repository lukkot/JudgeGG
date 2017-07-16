package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 04.07.17.
 */
class Disqualified extends NotRegistered<Disqualified> implements Comparable<TimeResult<?>> {

	protected Disqualified() {
		super(TimeResultType.DISQUALIFIED);
	}

	@Override
	public int compareTo(TimeResult<?> second) {
		final int result;

		if (second instanceof Registered) {
			result = 1;
		} else if (second instanceof Disqualified) {
			result = 0;
		} else {
			result = -1;
		}

		return result;
	}
}
