package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 04.07.17.
 */
public interface TimeResult<T extends  TimeResult> {
	String getTime();

	long getTimeAsMillis();

	T addPenalty(long penalty);
}
