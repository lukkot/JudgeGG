package pl.gymkhana_gp.judge.presentation.views.boards;

import pl.gymkhana_gp.judge.converter.PlayerViewDataToPlayerDtoClassicConverterHelper;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

public class ClassicAmateurBoardControllerHelper extends AbstractClassicBoardControllerHelper {
	public ClassicAmateurBoardControllerHelper() {
		super(TournamentType.CLASSIC_AMATEUR, new PlayerViewDataToPlayerDtoClassicConverterHelper());
	}
}
