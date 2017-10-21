package pl.gymkhana_gp.judge.model.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.testdata.ScoreBoardTestData;
import pl.gymkhana_gp.judge.testutils.PlayerDtoTestHelper;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/Beans.xml"})
public class CsvScoreBoardExporterTest {

	@Autowired
	private CsvScoreBoardExporter csvScoreBoardExporter;

	@Test
	public void shouldCreateStringWithHtmlTableWithOnePlayer() {
		// given
		TournamentType tournamentType = TournamentType.GP8;

		// when
		String result = csvScoreBoardExporter.createScoreBoardCsvBody(
				PlayerDtoTestHelper.createPlayersList(new String[][] { ScoreBoardTestData.PLAYER_1 }, tournamentType), tournamentType
		);

		// then
		assertEquals(ScoreBoardTestData.CSV_ONE_PLAYER, result);
	}
}