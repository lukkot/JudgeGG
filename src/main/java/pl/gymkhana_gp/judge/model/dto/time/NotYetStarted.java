package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 08.07.17.
 */
public class NotYetStarted extends NotRegistered<NotYetStarted> implements Comparable<TimeResult> {

	NotYetStarted() {
		super(TimeResultType.NOT_YET_STARTED);
	}

	@Override
	public int compareTo(final TimeResult second) {
		final int result;

		if ((second == null) || (second instanceof NotYetStarted)) {
			result = 0;
		} else {
			result = 1;
		}

		return result;
	}
}
