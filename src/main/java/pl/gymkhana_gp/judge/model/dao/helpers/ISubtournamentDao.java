package pl.gymkhana_gp.judge.model.dao.helpers;

import java.util.List;

import pl.gymkhana_gp.judge.model.dto.PlayerDto;

public interface ISubtournamentDao {
	List<PlayerDto> getAllSubtournamentPlayers(List<PlayerDto> allPlayers);
	
	List<PlayerDto> getPlayersWaiting(List<PlayerDto> allPlayers, int roundNumber);
	
	List<PlayerDto> getPlayersDone(List<PlayerDto> allPlayers, int roundNumber);
	
	void setPlayerCurrent(PlayerDto currentPlayer);
	
	PlayerDto getPlayerCurrent();
}
