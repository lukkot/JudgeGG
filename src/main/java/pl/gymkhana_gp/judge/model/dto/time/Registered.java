package pl.gymkhana_gp.judge.model.dto.time;

import org.apache.commons.lang3.ObjectUtils;

import java.time.Duration;

/**
 * Created by filus on 04.07.17.
 */
public class Registered implements TimeResult<Registered>, Comparable<TimeResult> {
	private final TimeResultType type = TimeResultType.REGISTERED;
	private final Duration time;

	Registered(final long minutes, final long seconds, final long millis, final long penalties) {
		final String timeString = String.format("PT%dM%d.%03dS", minutes, seconds, millis);
		this.time = Duration.parse(timeString).plusSeconds(penalties);
	}

	Registered(final Duration timeNetto, final Duration penalties) {
		this.time = timeNetto.plus(penalties);
	}

	Registered(final Duration timeNetto) {
		this.time = timeNetto;
	}

	@Override
	public String getTime() {
		return formatDuration(this.time);
	}

	private String formatDuration(final Duration duration) {
		final long minutes = duration.toMinutes();
		final long seconds = duration.getSeconds() % 60;
		final long millis = duration.toMillis() % 1000;

		return String.format(type.getTextValue(), minutes, seconds, millis);
	}

	@Override
	public long getTimeAsMillis() {
		return time.toMillis();
	}

	@Override
	public Registered addPenalty(final long penalty) {
		return addPenalty(Duration.ofSeconds(penalty));
	}

	Registered addPenalty(final Duration penalties) {
		final Registered newRegistered = new Registered(time, penalties);

		return newRegistered;
	}

	@Override
	public int compareTo(final TimeResult second) {
		final int result;

		if (second instanceof Registered) {
			final Duration firstDuration = this.time;
			final Duration secondDuration = ((Registered) second).time;

			result = ObjectUtils.compare(firstDuration, secondDuration);
		} else {
			result = -1;
		}

		return result;
	}
}
