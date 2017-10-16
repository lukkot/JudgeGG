package pl.gymkhana_gp.judge.testdata;

public class ScoreBoardTestData {
	public static final String[] TABLE_HEADER = {
			"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria",
			"Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
	};
	public static final String[] PLAYER_1 = {
			"1", "First1", "Last1", "Nick1", "111", "MALE", "AMATEUR",
			"12:34,567", "1", "12:35,567", "11:22,333", "0", "11:22,333", "11:22,333", "0", "0"};
	public static final String[] PLAYER_2 = {
			"2", "First2", "Last2", "Nick2", "222", "MALE", "AMATEUR",
			"13:34,567", "1", "13:35,567", "11:27,333", "0", "11:27,333", "11:27,333", "5", "5"};
	public static final String[] PLAYER_3 = {
			"3", "First3", "Last3", "Nick3", "333", "MALE", "AMATEUR",
			"13:34,567", "1", "13:35,567", "11:28,333", "0", "11:28,333", "11:28,333", "1", "6"};

	public static final String[][] PLAYERS_ORDERED = {
			PLAYER_1,
			PLAYER_2,
			PLAYER_3,
	};

	public static final String[][] PLAYERS_RANDOM = {
			PLAYER_3,
			PLAYER_1,
			PLAYER_2,
	};

	public static final String TABLE_ONE_PLAYER = "<table>\n" +
			"  <tr><td>Pozycja</td><td>Imię</td><td>Nazwisko</td><td>Ksywa</td><td>Numer</td><td>Płeć</td><td>Kategoria</td><td>Czas 1</td><td>Kara 1</td><td>Łącznie 1</td><td>Czas 2</td><td>Kara 2</td><td>Łącznie 2</td><td>Lepszy</td><td>Gap</td><td>Do Lidera</td></tr>\n" +
			"  <tr><td>1</td><td>First1</td><td>Last1</td><td>Nick1</td><td>111</td><td>MALE</td><td>AMATEUR</td><td>12:34,567</td><td>1</td><td>12:35,567</td><td>11:22,333</td><td>0</td><td>11:22,333</td><td>11:22,333</td><td>0</td><td>0</td></tr>\n" +
			"</table>";
}
