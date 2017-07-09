package pl.gymkhana_gp.judge.model.dto.time;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by filus on 07.07.17.
 */
public class TimeResultFactory {
	private final static String TIME_REGEXP = "(\\d+):(\\d{2})[.,](\\d{2,3})";
	private static final Pattern PATTERN = Pattern.compile(TIME_REGEXP);


	public static TimeResult obtainTimeResult(final String timeString) {
		final TimeResult result;

		if (timeStringIsNotYetStart(timeString)) {
			result = new NotYetStarted();
		} else if (timeStringIsDoNotStart(timeString)) {
			result = new DoNotStart();
		} else if (timeStringIsDoNotFinish(timeString)) {
			result = new DoNotFinish();
		} else if (timeStringIsDisqaulified(timeString)) {
			result = new Disqualified();
		} else {
			result = getTimeFromString(timeString);
		}

		return result;
	}

	private static boolean timeStringIsNotYetStart(final String timeString) {
		return StringUtils.isBlank(timeString);
	}

	public static TimeResult obtainTimeResult(final long timeInMillis) {
		final Duration time = Duration.ofMillis(timeInMillis);

		return new Registered(time);
	}

	private static boolean timeStringIsDisqaulified(String timeString) {
		return TimeResultType.DISQUALIFIED.getTextValue().equalsIgnoreCase(timeString);
	}

	private static boolean timeStringIsDoNotFinish(final String timeString) {
		return TimeResultType.DO_NOT_FINISH.getTextValue().equalsIgnoreCase(timeString);
	}

	private static boolean timeStringIsDoNotStart(final String timeString) {
		return TimeResultType.DO_NOT_START.getTextValue().equalsIgnoreCase(timeString);
	}

	private static TimeResult getTimeFromString(final String timeString) {
		final Matcher matcher = PATTERN.matcher(timeString);
		final TimeResult result;

		if (matcher.matches()) {
			final long minutes = Long.parseLong(matcher.group(1));
			final long seconds = Long.parseLong(matcher.group(2));
			final long millis = getMillis(matcher);

			result = new Registered(minutes, seconds, millis, 0L);
		} else {
			result = new DoNotStart();
		}

		return result;
	}

	private static long getMillis(final Matcher matcher) {
		final String group = matcher.group(3);
		long millis = Long.parseLong(group);

		if (group.length() == 2) {
			millis *= 10;
		}

		return millis;
	}
}
