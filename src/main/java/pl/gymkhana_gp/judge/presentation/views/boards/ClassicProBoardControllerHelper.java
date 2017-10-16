package pl.gymkhana_gp.judge.presentation.views.boards;

import pl.gymkhana_gp.judge.converter.PlayerViewDataToPlayerDtoClassicConverterHelper;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

public class ClassicProBoardControllerHelper extends AbstractClassicBoardControllerHelper {
	public ClassicProBoardControllerHelper() {
		super(TournamentType.CLASSIC_PRO, new PlayerViewDataToPlayerDtoClassicConverterHelper());
	}
}
