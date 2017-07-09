package pl.gymkhana_gp.judge.presentation.views.boards;

import java.util.List;

import pl.gymkhana_gp.judge.controllers.TournamentsControllerBean;
import pl.gymkhana_gp.judge.converter.IPlayerViewDataToPlayerDtoConverterHelper;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

public abstract class AbstractBoardControllerHelper implements IBoardControllerHelper {
	protected final IPlayerViewDataToPlayerDtoConverterHelper playerViewDataToPlayerDtoConverterHelper;
	protected final TournamentType tournamentType;
	protected TournamentsControllerBean tournamentControllerBean;

	public AbstractBoardControllerHelper(TournamentType tournamentType,
			IPlayerViewDataToPlayerDtoConverterHelper playerViewDataToPlayerDtoConverterHelper) {
		this.playerViewDataToPlayerDtoConverterHelper = playerViewDataToPlayerDtoConverterHelper;
		this.tournamentType = tournamentType;
	}

	public TournamentType getTournamentType() {
		return tournamentType;
	}

	@Override
	public List<PlayerDto> getPlayersWaiting(int roundNumber) {
		return tournamentControllerBean.getPlayersWaiting(tournamentType, roundNumber);
	}

	@Override
	public List<PlayerDto> getPlayersDone(int roundNumber) {
		return tournamentControllerBean.getAllPlayers(tournamentType);
	}

	@Override
	public PlayerDto getPlayerCurrent() {
		return tournamentControllerBean.getPlayerCurrent(tournamentType);
	}

	@Override
	public void setPlayerCurrent(PlayerDto currentPlayer) {
		tournamentControllerBean.setPlayerCurrent(tournamentType, currentPlayer);
	}

	@Override
	public TournamentsControllerBean getTournamentControllerBean() {
		return tournamentControllerBean;
	}

	@Override
	public void setTournamentControllerBean(TournamentsControllerBean tournamentControllerBean) {
		this.tournamentControllerBean = tournamentControllerBean;
	}

	@Override
	public IPlayerViewDataToPlayerDtoConverterHelper getPlayerViewDataToPlayerDtoConverterHelper() {
		return playerViewDataToPlayerDtoConverterHelper;
	}
}
