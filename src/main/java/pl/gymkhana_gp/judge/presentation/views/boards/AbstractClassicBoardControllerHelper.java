package pl.gymkhana_gp.judge.presentation.views.boards;

import pl.gymkhana_gp.judge.converter.IPlayerViewDataToPlayerDtoConverterHelper;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

public abstract class AbstractClassicBoardControllerHelper extends AbstractBoardControllerHelper {
	public AbstractClassicBoardControllerHelper(TournamentType tournamentType,
			IPlayerViewDataToPlayerDtoConverterHelper playerViewDataToPlayerDtoConverterHelper) {
		super(tournamentType, playerViewDataToPlayerDtoConverterHelper);
	}

	@Override
	public boolean shouldControlsForMeasurementBeAvailable(int controlsForRound, int currentRoundNumber) {
		return controlsForRound == currentRoundNumber;
	}

	@Override
	public boolean columnVisibility(ColumnType columnType) {
		switch (columnType) {
		case PLAYER_CLASS:
			return false;

		default:
			return true;
		}
	}
}
