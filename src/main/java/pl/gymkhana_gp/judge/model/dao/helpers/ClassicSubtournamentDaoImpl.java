package pl.gymkhana_gp.judge.model.dao.helpers;

import java.util.List;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

public class ClassicSubtournamentDaoImpl extends AbstractSubtournamentDao {

	private final TournamentType tournamentType;

	public ClassicSubtournamentDaoImpl(TournamentType tournamentType) {
		super();
		this.tournamentType = tournamentType;
	}

	@Override
	protected List<FullMeasurementDto> getPlayerDtoMeasurements(PlayerDto playerDto) {
		return playerDto.getClassicMeasurements();
	}

	@Override
	protected boolean belongsToTournament(PlayerDto playerDto) {
		return (playerDto.getPlayerClass() == PlayerClass.AMATEUR && tournamentType == TournamentType.CLASSIC_AMATEUR)
				|| (playerDto.getPlayerClass() == PlayerClass.PRO && tournamentType == TournamentType.CLASSIC_PRO);
	}
}
