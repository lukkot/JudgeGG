package pl.gymkhana_gp.judge.model.dao;

import org.junit.Before;
import org.junit.Test;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HtmlScoreBoardExporterTest {

	private static final String ONE_PLAYER_TABLE = "<table>\n" +
			"  <tr><td>Pozycja</td><td>Imię</td><td>Nazwisko</td><td>Ksywa</td><td>Numer</td><td>Płeć</td><td>Kategoria</td><td>Czas 1</td><td>Kara 1</td><td>Łącznie 1</td><td>Czas 2</td><td>Kara 2</td><td>Łącznie 2</td><td>Lepszy</td><td>Gap</td><td>Do Lidera</td></tr>\n" +
			"  <tr><td>1</td><td>First</td><td>Last</td><td>Nick</td><td>123</td><td>MALE</td><td>AMATEUR</td><td>12:34,567</td><td>1</td><td>12:35,567</td><td>11:22,333</td><td>0</td><td>11:22,333</td><td>11:22,333</td><td>0</td><td>0</td></tr>\n" +
			"</table>";

	private HtmlScoreBoardExporter htmlScoreBoardExporter;

	@Before
	public void setUp() throws Exception {
		htmlScoreBoardExporter = new HtmlScoreBoardExporter();
	}

	@Test
	public void shouldCreateStringWithHtmlTableWithOnePlayer() {
		// given

		// when
		String result = htmlScoreBoardExporter.createScoreBoardHtmlTable(createOnePlayerList());

		// then
		assertEquals(ONE_PLAYER_TABLE, result);
	}

	private List<PlayerDto> createOnePlayerList() {
		List<PlayerDto> list = new ArrayList<>();
		PlayerDto player = new PlayerDto();
		player.setFirstName("First");
		player.setLastName("Last");
		player.setNick("Nick");
		player.setSex(Sex.MALE);
		player.setPlayerClass(PlayerClass.AMATEUR);
		player.setStartNumber(123);
		player.setGp8Measurements(getMeasurements("12:34.567", 1, "11:22.333", 0));
		list.add(player);
		return list;
	}

	private List<FullMeasurementDto> getMeasurements(String time1, long penalty1, String time2, long penalty2) {
		List<FullMeasurementDto> list = new ArrayList<>();
		list.add(FullMeasurementDto.getInstanceOrNull(time1, penalty1));
		list.add(FullMeasurementDto.getInstanceOrNull(time2, penalty2));
		return list;
	}
}