package pl.gymkhana_gp.judge.converter;

import java.util.List;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

public class PlayerViewDataToPlayerDtoGp8ConverterHelper implements IPlayerViewDataToPlayerDtoConverterHelper {

	@Override
	public void helpConvertingToPlayerDto(PlayerViewData playerViewData, PlayerDto playerDto) {
		FullMeasurementDto measurement1 = FullMeasurementDto.getInstanceOrNull(playerViewData.getTime1().get(),
				playerViewData.getPenalty1().get());

		FullMeasurementDto measurement2 = FullMeasurementDto.getInstanceOrNull(playerViewData.getTime2().get(),
				playerViewData.getPenalty2().get());

		playerDto.getGp8Measurements().set(0, measurement1);
		playerDto.getGp8Measurements().set(1, measurement2);
	}

	@Override
	public List<FullMeasurementDto> getMeasurements(PlayerDto playerDto) {
		return playerDto.getGp8Measurements();
	}

}
