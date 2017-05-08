package pl.gymkhana_gp.judge.model.dao.helpers;

import java.util.List;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;

public class Gp8SubtournamentDaoImpl extends AbstractSubtournamentDao {

	@Override
	protected List<FullMeasurementDto> getPlayerDtoMeasurements(PlayerDto playerDto) {
		return playerDto.getGp8Measurements();
	}

	@Override
	protected boolean belongsToTournament(PlayerDto playerDto) {
		return true;
	}

}
