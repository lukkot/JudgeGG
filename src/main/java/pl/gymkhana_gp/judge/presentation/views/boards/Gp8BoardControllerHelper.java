package pl.gymkhana_gp.judge.presentation.views.boards;

import pl.gymkhana_gp.judge.converter.PlayerViewDataToPlayerDtoGp8ConverterHelper;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

public class Gp8BoardControllerHelper extends AbstractBoardControllerHelper {
	public Gp8BoardControllerHelper() {
		super(TournamentType.GP8, new PlayerViewDataToPlayerDtoGp8ConverterHelper());
	}

	@Override
	public boolean shouldControlsForMeasurementBeAvailable(int controlsForRound, int currentRoundNumber) {
		return true;
	}

	@Override
	public boolean columnVisibility(ColumnType columnType) {
		return true;
	}
}
