package pl.gymkhana_gp.judge.model.dao.helpers;

import java.util.ArrayList;
import java.util.List;

import pl.gymkhana_gp.judge.model.dto.PlayerDto;

public class EmptySubtournamentDaoImpl implements ISubtournamentDao {
	public List<PlayerDto> getAllSubtournamentPlayers(List<PlayerDto> allPlayers) {
		return allPlayers;
	}
	
	@Override
	public List<PlayerDto> getPlayersWaiting(List<PlayerDto> allPlayers, int roundNumber) {
		return new ArrayList<>();
	}

	@Override
	public List<PlayerDto> getPlayersDone(List<PlayerDto> allPlayers, int roundNumber) {
		return new ArrayList<>();
	}
	
	@Override
	public void setPlayerCurrent(PlayerDto currentPlayer) {
	}

	@Override
	public PlayerDto getPlayerCurrent() {
		return null;
	}

}
