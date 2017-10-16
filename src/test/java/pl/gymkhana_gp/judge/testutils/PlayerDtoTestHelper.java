package pl.gymkhana_gp.judge.testutils;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

import java.util.ArrayList;
import java.util.List;

public class PlayerDtoTestHelper {

	/**
	 * 	"Pozycja", "Imię", "Nazwisko", "Ksywa", "Numer", "Płeć", "Kategoria",
	 *	"Czas 1", "Kara 1", "Łącznie 1", "Czas 2", "Kara 2", "Łącznie 2", "Lepszy", "Gap", "Do Lidera"
	 */
	public static List<PlayerDto> createPlayersList(String[][] players, TournamentType tournamentType) {
		List<PlayerDto> playersList = new ArrayList<>();

		for(String[] playerRow : players) {
			playersList.add(createPlayerFromString(playerRow, tournamentType));
		}

		return playersList;
	}

	private static PlayerDto createPlayerFromString(String[] playerArray, TournamentType tournamentType) {
		PlayerDto player = new PlayerDto();
		player.setFirstName(playerArray[1]);
		player.setLastName(playerArray[2]);
		player.setNick(playerArray[3]);
		player.setStartNumber(Integer.valueOf(playerArray[4]));
		player.setSex(Sex.valueOf(playerArray[5]));
		player.setPlayerClass(PlayerClass.valueOf(playerArray[6]));

		if(TournamentType.GP8 == tournamentType) {
			player.setGp8Measurements(getMeasurements(playerArray[7], Integer.valueOf(playerArray[8]), playerArray[10], Integer.valueOf(playerArray[11])));
		} else {
			player.setClassicMeasurements(getMeasurements(playerArray[7], Integer.valueOf(playerArray[8]), playerArray[10], Integer.valueOf(playerArray[11])));
		}

		return player;
	}

	private static List<FullMeasurementDto> getMeasurements(String time1, long penalty1, String time2, long penalty2) {
		List<FullMeasurementDto> list = new ArrayList<>();
		list.add(FullMeasurementDto.getInstanceOrNull(time1, penalty1));
		list.add(FullMeasurementDto.getInstanceOrNull(time2, penalty2));
		return list;
	}
}
