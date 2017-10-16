package pl.gymkhana_gp.judge.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

import java.util.List;

@Component
public class HtmlScoreBoardExporter {

	@Autowired
	private ScoreBoardExporterHelper scoreBoardExporterHelper;

	private static final String INDENT = "  ";
	private static final String TAG_TABLE_OPEN = "<table>";
	private static final String TAG_TABLE_CLOSE = "</table>";
	private static final String TAG_TR_OPEN = "<tr>";
	private static final String TAG_TR_CLOSE = "</tr>";

//	private static final String[] COLUMNS = {
//			"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria", "Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
//	};

	public String createScoreBoardHtmlTable(List<PlayerDto> players, TournamentType tournamentType) {
		List<String[]> playersTable = scoreBoardExporterHelper.exportScoreBoard(players, tournamentType);

		StringBuilder table = new StringBuilder();
		table
				.append(TAG_TABLE_OPEN)
				.append("\n");

		for (String[] playerRow : playersTable) {
			table
					.append(INDENT)
					.append(TAG_TR_OPEN)
					.append(createRow(playerRow))
					.append(TAG_TR_CLOSE)
					.append("\n");
		}

		table.append(TAG_TABLE_CLOSE);
		return table.toString();
	}

	private StringBuilder createRow(String[] playerRow) {
		StringBuilder row = new StringBuilder();

		for (String playerRowCell : playerRow) {
			row.append(decorateCell(playerRowCell));
	}

		return row;
	}

	private String decorateCell(String cellValue) {
		return "<td>" + cellValue + "</td>";
	}
}
