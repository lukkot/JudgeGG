package pl.gymkhana_gp.judge.model.dao;

import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.utils.TimeMath;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScoreBoardExporterHelper {

	private static final int COLUMN_COUNT = 16;

	private static final String[] HEADER = new String[] {
			"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria",
			"Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
	};

	public List<String[]> exportScoreBoard(List<PlayerDto> players) {
		List<String[]> result = new ArrayList<>();

		result.add(HEADER);
		for(int i=0; i<players.size(); i++) {
			result.add(createStringRow(i, players.get(i)));
		}

		return result;
	}

	private String[] createStringRow(int i, PlayerDto playerDto) {
		String[] playerRow = new String[16];
		playerRow[0] = String.valueOf(i + 1);
		playerRow[1] = playerDto.getFirstName();
		playerRow[2] = playerDto.getLastName();
		playerRow[3] = playerDto.getNick();
		playerRow[4] = String.valueOf(playerDto.getStartNumber());
		playerRow[5] = playerDto.getSex().name();
		playerRow[6] = playerDto.getPlayerClass().name();
		createStringMeasurementData(playerRow, 7, playerDto.getGp8Measurements().get(0));
		createStringMeasurementData(playerRow, 10, playerDto.getGp8Measurements().get(1));
		playerRow[13] = TimeMath.getBestMeasurement(playerDto.getGp8Measurements()).getTimeWithPenalty().getTimeFormatted();

		return playerRow;
	}

	private void createStringMeasurementData(String[] playerRow, int offset, FullMeasurementDto fullMeasurementDto) {
		playerRow[offset] = fullMeasurementDto.getTime().getTimeFormatted();
		playerRow[offset + 1] = String.valueOf(fullMeasurementDto.getPenalty());
		playerRow[offset + 2] = fullMeasurementDto.getTimeWithPenalty().getTimeFormatted();
	}
}
