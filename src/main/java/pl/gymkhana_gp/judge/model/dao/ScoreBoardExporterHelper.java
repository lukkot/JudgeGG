package pl.gymkhana_gp.judge.model.dao;

import org.springframework.stereotype.Component;
import pl.gymkhana_gp.judge.comparators.PlayerDtoComparator;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.utils.TimeMath;

import java.util.ArrayList;
import java.util.List;

@Component
class ScoreBoardExporterHelper {

	private static final String[] HEADER = new String[] {
			"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria",
			"Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
	};

	List<String[]> exportScoreBoard(List<PlayerDto> players, TournamentType tournamentType) {
		List<PlayerDto> playersSorted = new ArrayList<>(players);
		playersSorted.sort(new PlayerDtoComparator(tournamentType));

		List<String[]> result = new ArrayList<>();

		FullMeasurementDto firstTime = getFirstTimeOrNull(tournamentType, playersSorted);
		FullMeasurementDto previousTime = firstTime;

		result.add(HEADER);
		for(int i=0; i<playersSorted.size(); i++) {
			PlayerDto player = playersSorted.get(i);

			result.add(createStringRow(tournamentType, i, player, firstTime, previousTime));
			previousTime = TimeMath.getBestMeasurement(player.getMeasurementByTournamentType(tournamentType));
		}

		return result;
	}

	private FullMeasurementDto getFirstTimeOrNull(TournamentType tournamentType, List<PlayerDto> players) {
		return !players.isEmpty() ? TimeMath.getBestMeasurement(players.get(0).getMeasurementByTournamentType(tournamentType)) : null;
	}

	private String[] createStringRow(TournamentType tournamentType, int i, PlayerDto playerDto, FullMeasurementDto firstTime, FullMeasurementDto previousTime) {
		String[] playerRow = new String[16];
		playerRow[0] = String.valueOf(i + 1);
		playerRow[1] = playerDto.getFirstName();
		playerRow[2] = playerDto.getLastName();
		playerRow[3] = playerDto.getNick();
		playerRow[4] = String.valueOf(playerDto.getStartNumber());
		playerRow[5] = playerDto.getSex().name();
		playerRow[6] = playerDto.getPlayerClass().name();
		createStringMeasurementData(playerRow, 7, playerDto.getMeasurementByTournamentType(tournamentType).get(0));
		createStringMeasurementData(playerRow, 10, playerDto.getMeasurementByTournamentType(tournamentType).get(1));

		FullMeasurementDto currentPlayerBestTime = TimeMath.getBestMeasurement(playerDto.getMeasurementByTournamentType(tournamentType));
		playerRow[13] = (currentPlayerBestTime == null) ? "" : currentPlayerBestTime.getTimeWithPenalty().getTimeFormatted();
		playerRow[14] = String.valueOf(TimeMath.getDeltaInSeconds(currentPlayerBestTime, previousTime));
		playerRow[15] = String.valueOf(TimeMath.getDeltaInSeconds(currentPlayerBestTime, firstTime));

		return playerRow;
	}

	private void createStringMeasurementData(String[] playerRow, int offset, FullMeasurementDto fullMeasurementDto) {
		playerRow[offset] = (fullMeasurementDto == null) ? "" : fullMeasurementDto.getTime().getTimeFormatted();
		playerRow[offset + 1] = String.valueOf((fullMeasurementDto == null) ? "" : fullMeasurementDto.getPenalty());
		playerRow[offset + 2] = (fullMeasurementDto == null) ? "" : fullMeasurementDto.getTimeWithPenalty().getTimeFormatted();
	}
}
