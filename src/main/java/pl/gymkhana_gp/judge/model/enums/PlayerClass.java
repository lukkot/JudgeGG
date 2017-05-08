package pl.gymkhana_gp.judge.model.enums;

public enum PlayerClass {
	PRO, AMATEUR;
	
	public static PlayerClass valueOfIgnoreCase(String stringValue) {
		return PlayerClass.valueOf(stringValue.toUpperCase());
	}
}
