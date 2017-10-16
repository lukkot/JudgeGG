package pl.gymkhana_gp.judge.comparators;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.utils.TimeMath;

import java.util.Comparator;
import java.util.Objects;

public class PlayerDtoComparator implements Comparator<PlayerDto> {

	private TournamentType tournamentType;
	private FullMeasurementDtoComparator fullMeasurementDtoComparator = new FullMeasurementDtoComparator();

	public PlayerDtoComparator(TournamentType tournamentType) {
		this.tournamentType = tournamentType;
	}

	@Override
	public int compare(PlayerDto player1, PlayerDto player2) {
		FullMeasurementDto player1best = TimeMath.getBestMeasurement(
				player1.getMeasurementByTournamentType(tournamentType)
		);

		FullMeasurementDto player2best = TimeMath.getBestMeasurement(
				player2.getMeasurementByTournamentType(tournamentType)
		);

		return Objects.compare(player1best, player2best, fullMeasurementDtoComparator);
	}
}
