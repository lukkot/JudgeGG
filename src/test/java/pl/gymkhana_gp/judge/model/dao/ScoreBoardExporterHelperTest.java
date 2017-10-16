package pl.gymkhana_gp.judge.model.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.testdata.ScoreBoardTestData;
import pl.gymkhana_gp.judge.testutils.PlayerDtoTestHelper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/Beans.xml"})
public class ScoreBoardExporterHelperTest {



	@Autowired
	private ScoreBoardExporterHelper scoreBoardExporterHelper;

	@Test
	public void shouldExportPlayersDataForGp8() {
		// given
		TournamentType tournamentType = TournamentType.GP8;

		// when
		List<String[]> result = scoreBoardExporterHelper.exportScoreBoard(
				PlayerDtoTestHelper.createPlayersList(ScoreBoardTestData.PLAYERS_ORDERED, tournamentType), tournamentType
		);

		// then
		List<String[]> expected = new ArrayList<>();
		expected.add(ScoreBoardTestData.TABLE_HEADER);
		expected.add(ScoreBoardTestData.PLAYER_1);
		expected.add(ScoreBoardTestData.PLAYER_2);
		expected.add(ScoreBoardTestData.PLAYER_3);
		assertListOfStringArrays(expected, result);
	}

	@Test
	public void shouldExportPlayersDataForClassic() {
		// given
		TournamentType tournamentType = TournamentType.CLASSIC_AMATEUR;

		// when
		List<String[]> result = scoreBoardExporterHelper.exportScoreBoard(
				PlayerDtoTestHelper.createPlayersList(ScoreBoardTestData.PLAYERS_ORDERED, tournamentType), tournamentType
		);

		// then
		List<String[]> expected = new ArrayList<>();
		expected.add(ScoreBoardTestData.TABLE_HEADER);
		expected.add(ScoreBoardTestData.PLAYER_1);
		expected.add(ScoreBoardTestData.PLAYER_2);
		expected.add(ScoreBoardTestData.PLAYER_3);
		assertListOfStringArrays(expected, result);
	}

	@Test
	public void shouldExportPlayersDataForGp8InOrder() {
		// given
		TournamentType tournamentType = TournamentType.CLASSIC_AMATEUR;

		// when
		List<String[]> result = scoreBoardExporterHelper.exportScoreBoard(
				PlayerDtoTestHelper.createPlayersList(ScoreBoardTestData.PLAYERS_RANDOM, tournamentType), tournamentType
		);

		// then
		List<String[]> expected = new ArrayList<>();
		expected.add(ScoreBoardTestData.TABLE_HEADER);
		expected.add(ScoreBoardTestData.PLAYER_1);
		expected.add(ScoreBoardTestData.PLAYER_2);
		expected.add(ScoreBoardTestData.PLAYER_3);
		assertListOfStringArrays(expected, result);
	}

	private void assertListOfStringArrays(List<String[]> expected, List<String[]> actual) {
		assertThat("Lists sizes differs.", actual.size(), is(expected.size()));
		for(int i=0; i<expected.size(); i++) {
			assertArrayEquals("Array no. " + i, expected.get(i), actual.get(i));
		}
	}
}