package pl.gymkhana_gp.judge.model.dto.time;

/**
 * Created by filus on 04.07.17.
 */
enum TimeResultType {
	DO_NOT_START("DNS"),
	DO_NOT_FINISH("DNF"),
	DISQUALIFIED("DSQ"),
	NOT_YET_STARTED(null),
	REGISTERED("%02d:%02d,%03d");

	private final String textValue;

	TimeResultType(final String value) {
		textValue = value;
	}

	public String getTextValue() {
		return textValue;
	}
}
