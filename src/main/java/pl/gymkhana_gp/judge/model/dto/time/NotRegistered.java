package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 08.07.17.
 */
abstract class NotRegistered<T extends NotRegistered<?>> implements TimeResult<T> {
	private final TimeResultType type;

	NotRegistered(final TimeResultType type) {
		this.type = type;
	}

	@Override
	public String getTime() {
		return type.getTextValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T addPenalty(long penalty) {
		//noinspection unchecked
		return (T) this;
	}

	@Override
	public long getTimeAsMillis() {
		return 0;
	}
}
