package pl.gymkhana_gp.judge.model.dao.helpers;

import java.util.ArrayList;
import java.util.List;

import pl.gymkhana_gp.judge.model.dto.FullMeasurementDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;

public abstract class AbstractSubtournamentDao implements ISubtournamentDao {
	
	PlayerDto currentPlayer;
	
	abstract protected List<FullMeasurementDto> getPlayerDtoMeasurements(PlayerDto playerDto);
	
	abstract protected boolean belongsToTournament(PlayerDto playerDto);

	@Override
	public List<PlayerDto> getAllSubtournamentPlayers(List<PlayerDto> allPlayers) {
		List<PlayerDto> players = new ArrayList<>();

		for (PlayerDto player : allPlayers) {
			if (belongsToTournament(player)) {
				players.add(player);
			}
		}

		return players;
	}
	
	@Override
	public List<PlayerDto> getPlayersWaiting(List<PlayerDto> allPlayers, int roundNumber) {
		List<PlayerDto> players = new ArrayList<>();

		for (PlayerDto player : getAllSubtournamentPlayers(allPlayers)) {
			if (getPlayerDtoMeasurements(player).get(roundNumber) == null && !player.equals(currentPlayer)) {
				players.add(player);
			}
		}

		return players;
	}

	@Override
	public List<PlayerDto> getPlayersDone(List<PlayerDto> allPlayers, int roundNumber) {
		List<PlayerDto> players = new ArrayList<>();

		for (PlayerDto player : getAllSubtournamentPlayers(allPlayers)) {
			if (getPlayerDtoMeasurements(player).get(roundNumber) != null && !player.equals(currentPlayer)) {
				players.add(player);
			}
		}

		return players;
	}
	
	@Override
	public void setPlayerCurrent(PlayerDto currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	@Override
	public PlayerDto getPlayerCurrent() {
		return currentPlayer;
	}

}
