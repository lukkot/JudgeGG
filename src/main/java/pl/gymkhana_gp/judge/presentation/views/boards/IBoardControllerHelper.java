package pl.gymkhana_gp.judge.presentation.views.boards;

import java.util.List;

import pl.gymkhana_gp.judge.controllers.TournamentsControllerBean;
import pl.gymkhana_gp.judge.converter.IPlayerViewDataToPlayerDtoConverterHelper;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;

public interface IBoardControllerHelper {
	
	public enum ColumnType {
		PLAYER_CLASS, OTHER
	};

	List<PlayerDto> getPlayersWaiting(int roundNumber);

	List<PlayerDto> getPlayersDone(int roundNumber);
	
	PlayerDto getPlayerCurrent();
	
	void setPlayerCurrent(PlayerDto currentPlayer);

	TournamentsControllerBean getTournamentControllerBean();

	void setTournamentControllerBean(TournamentsControllerBean tournamentControllerBean);
	
	IPlayerViewDataToPlayerDtoConverterHelper getPlayerViewDataToPlayerDtoConverterHelper();
	
	boolean shouldControlsForMeasurementBeAvailable(int controlsForRound, int currentRoundNumber);
	
	boolean columnVisibility(ColumnType columnType);
}
