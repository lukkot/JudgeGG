package pl.gymkhana_gp.judge.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

@Component
@Scope("prototype")
public class PlayerViewDataUtils {

	private IPlayerViewDataToPlayerDtoConverterHelper playerViewDataToPlayerDtoConverterHelper;

	public void convert(ObservableList<PlayerViewData> playersViewData, List<PlayerDto> playersDto) {
		playersViewData.clear();

		for (PlayerDto playerDto : playersDto) {
			playersViewData.add(convert(playerDto));
		}
	}

	public PlayerViewData convert(PlayerDto playerDto) {
		if (playerDto == null) {
			return PlayerViewData.NULL_PLAYER_VIEW_DATA;
		}

		PlayerViewData playerViewData = new PlayerViewData((int) playerDto.getId());

		playerViewData.setStartNumber(playerDto.getStartNumber());
		playerViewData.setFirstName(playerDto.getFirstName());
		playerViewData.setLastName(playerDto.getLastName());
		playerViewData.setNick(playerDto.getNick());
		playerViewData.setSex(playerDto.getSex().toString());
		playerViewData.setPlayerClass(playerDto.getPlayerClass().toString());

		if(playerViewDataToPlayerDtoConverterHelper != null) {
			FullMeasurementDto fullMeasurementDto1 = playerViewDataToPlayerDtoConverterHelper.getMeasurements(playerDto)
					.get(0);
	
			if (fullMeasurementDto1 != null) {
				playerViewData.setTime1(fullMeasurementDto1.getTime().getTimeFormatted());
				playerViewData.setPenalty1((int) fullMeasurementDto1.getPenalty());
			}
	
			FullMeasurementDto fullMeasurementDto2 = playerViewDataToPlayerDtoConverterHelper.getMeasurements(playerDto)
					.get(1);
	
			if (fullMeasurementDto2 != null) {
				playerViewData.setTime2(fullMeasurementDto2.getTime().getTimeFormatted());
				playerViewData.setPenalty2((int) fullMeasurementDto2.getPenalty());
			}
		}

		return playerViewData;
	}

	public ObservableList<PlayerViewData> convert(List<PlayerDto> playersDto) {
		ObservableList<PlayerViewData> playersViewData = FXCollections.observableArrayList();

		convert(playersViewData, playersDto);

		return playersViewData;
	}

	public List<PlayerDto> convert(ObservableList<PlayerViewData> playersViewData) {
		List<PlayerDto> players = new ArrayList<>();

		for (PlayerViewData playerViewData : playersViewData) {
			players.add(convert(playerViewData));
		}

		return players;
	}

	public PlayerDto convert(PlayerViewData playerViewData) {
		PlayerDto playerDto = new PlayerDto(playerViewData.getPlayerDataId());

		playerDto.setStartNumber(playerViewData.getStartNumber().get());
		playerDto.setFirstName(playerViewData.getFirstName().get());
		playerDto.setLastName(playerViewData.getLastName().get());
		playerDto.setNick(playerViewData.getNick().get());
		playerDto.setPlayerClass(PlayerClass.valueOf(playerViewData.getPlayerClass().get()));
		playerDto.setSex(Sex.valueOf(playerViewData.getSex().get()));

		if (playerViewDataToPlayerDtoConverterHelper != null) {
			playerViewDataToPlayerDtoConverterHelper.helpConvertingToPlayerDto(playerViewData, playerDto);
		}

		return playerDto;
	}

	public IPlayerViewDataToPlayerDtoConverterHelper getPlayerViewDataToPlayerDtoConverterHelper() {
		return playerViewDataToPlayerDtoConverterHelper;
	}

	public void setPlayerViewDataToPlayerDtoConverterHelper(
			IPlayerViewDataToPlayerDtoConverterHelper playerViewDataToPlayerDtoConverterHelper) {
		this.playerViewDataToPlayerDtoConverterHelper = playerViewDataToPlayerDtoConverterHelper;
	}

}
