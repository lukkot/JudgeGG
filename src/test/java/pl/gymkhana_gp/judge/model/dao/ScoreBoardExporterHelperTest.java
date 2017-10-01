package pl.gymkhana_gp.judge.model.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/Beans.xml"})
public class ScoreBoardExporterHelperTest {

	private static final String[] HEADER = {
			"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria",
			"Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
	};
	private static final String[] PLAYER_ONE = {
			"1", "First", "Last", "Nick", "123", "MALE", "AMATEUR",
			"12:34,567", "1", "12:35,567", "11:22,333", "0", "11:22,333", "11:22,333", "0", "0"};

	@Autowired
	ScoreBoardExporterHelper scoreBoardExporterHelper;

	@Test
	public void shouldExportOnePlayerData() {
		// given

		// when
		List<String[]> result = scoreBoardExporterHelper.exportScoreBoard(createOnePlayerList(PLAYER_ONE));

		// then
		List<String[]> expected = new ArrayList<>();
		expected.add(HEADER);
		expected.add(PLAYER_ONE);
		assertListOfStringArrays(expected, result);
	}

	private void assertListOfStringArrays(List<String[]> expected, List<String[]> actual) {
		assertThat("Lists sizes differs.", actual.size(), is(expected.size()));
		for(int i=0; i<expected.size(); i++) {
			assertArrayEquals(expected.get(i), actual.get(i));
		}
	}

	private List<PlayerDto> createOnePlayerList(String[] playerArray) {
		List<PlayerDto> list = new ArrayList<>();
		PlayerDto player = new PlayerDto();
		player.setFirstName(playerArray[1]);
		player.setLastName(playerArray[2]);
		player.setNick(playerArray[3]);
		player.setStartNumber(Integer.valueOf(playerArray[4]));
		player.setSex(Sex.valueOf(playerArray[5]));
		player.setPlayerClass(PlayerClass.valueOf(playerArray[6]));
		player.setGp8Measurements(getMeasurements(playerArray[7], Integer.valueOf(playerArray[8]), playerArray[10], Integer.valueOf(playerArray[11])));
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