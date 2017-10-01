package pl.gymkhana_gp.judge.model.dao;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.utils.TimeMath;

import java.util.List;

public class HtmlScoreBoardExporter {
	private static final String INDENT = "  ";
	private static final String TAG_TABLE_OPEN = "<table>";
	private static final String TAG_TABLE_CLOSE = "</table>";
	private static final String TAG_TR_OPEN = "<tr>";
	private static final String TAG_TR_CLOSE = "</tr>";
	private static final String TAG_TD_OPEN = "<td>";
	private static final String TAG_TD_CLOSE = "</td>";

	private static final String[] COLUMNS = {
			"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria", "Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
	};

	public String createScoreBoardHtmlTable(List<PlayerDto> players) {
		StringBuilder table = new StringBuilder();
		table
				.append(TAG_TABLE_OPEN)
				.append("\n")
				.append(createHeader());

		for(int i=0; i<players.size(); i++) {
			table
					.append(INDENT)
					.append(TAG_TR_OPEN)
					.append(createPlayerRow(i, players.get(i)))
					.append(TAG_TR_CLOSE)
					.append("\n");
		}

		table.append(TAG_TABLE_CLOSE);
		return table.toString();
	}

	private StringBuilder createHeader() {
		StringBuilder header = new StringBuilder();
		header
				.append(INDENT)
				.append(TAG_TR_OPEN);
		for (String columnName : COLUMNS) {
			header
					.append(TAG_TD_OPEN)
					.append(columnName)
					.append(TAG_TD_CLOSE);
		}
		header
				.append(TAG_TR_CLOSE)
				.append("\n");
		return header;
	}

	private StringBuilder createPlayerRow(int i, PlayerDto playerDto) {
		StringBuilder playerRow = new StringBuilder();
		playerRow.append(decorateTd(String.valueOf(i + 1)));
		playerRow.append(decorateTd(playerDto.getFirstName()));
		playerRow.append(decorateTd(playerDto.getLastName()));
		playerRow.append(decorateTd(playerDto.getNick()));
		playerRow.append(decorateTd(String.valueOf(playerDto.getStartNumber())));
		playerRow.append(decorateTd(playerDto.getSex().name()));
		playerRow.append(decorateTd(playerDto.getPlayerClass().name()));
		playerRow.append(createPlayerMeasurementData(playerDto.getGp8Measurements().get(0)));
		playerRow.append(createPlayerMeasurementData(playerDto.getGp8Measurements().get(1)));
		playerRow.append(TimeMath.getBestMeasurement(playerDto.getGp8Measurements()).getTimeWithPenalty().getTimeFormatted());
		return playerRow;
	}

	private StringBuilder createPlayerMeasurementData(FullMeasurementDto fullMeasurementDto) {
		StringBuilder playerMeasurementData = new StringBuilder();
		playerMeasurementData.append(decorateTd(fullMeasurementDto.getTime().getTimeFormatted()));
		playerMeasurementData.append(decorateTd(String.valueOf(fullMeasurementDto.getPenalty())));
		playerMeasurementData.append(decorateTd(fullMeasurementDto.getTimeWithPenalty().getTimeFormatted()));
		return playerMeasurementData;
	}

	private String decorateTd(String cellValue) {
		return "<td>" + cellValue + "</td>";
	}
}
