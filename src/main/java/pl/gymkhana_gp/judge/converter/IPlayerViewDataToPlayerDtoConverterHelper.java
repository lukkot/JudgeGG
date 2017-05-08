package pl.gymkhana_gp.judge.converter;

import java.util.List;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

public interface IPlayerViewDataToPlayerDtoConverterHelper {
	void helpConvertingToPlayerDto(PlayerViewData playerViewData, PlayerDto playerDto);
	
	List<FullMeasurementDto> getMeasurements(PlayerDto playerDto);
}
