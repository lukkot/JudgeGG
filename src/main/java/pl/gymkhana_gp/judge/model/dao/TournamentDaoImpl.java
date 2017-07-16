package pl.gymkhana_gp.judge.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import pl.gymkhana_gp.judge.model.dao.helpers.ClassicSubtournamentDaoImpl;
import pl.gymkhana_gp.judge.model.dao.helpers.EmptySubtournamentDaoImpl;
import pl.gymkhana_gp.judge.model.dao.helpers.Gp8SubtournamentDaoImpl;
import pl.gymkhana_gp.judge.model.dao.helpers.ISubtournamentDao;
import pl.gymkhana_gp.judge.model.dto.GymkhanaTournamentDto;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;

@Component
public class TournamentDaoImpl {
	private GymkhanaTournamentDto gymkhanaTournamentData = new GymkhanaTournamentDto();

	private ISubtournamentDao emptySubtournamentDao = new EmptySubtournamentDaoImpl();
	private ISubtournamentDao gp8TournamentDao = new Gp8SubtournamentDaoImpl();
	private ISubtournamentDao classicAmateurTournamentDao = new ClassicSubtournamentDaoImpl(TournamentType.CLASSIC_AMATEUR);
	private ISubtournamentDao classicProTournamentDao = new ClassicSubtournamentDaoImpl(TournamentType.CLASSIC_PRO);

	public GymkhanaTournamentDto getGymkhanaTournamentData() {
		return gymkhanaTournamentData;
	}

	public void setGymkhanaTournamentData(GymkhanaTournamentDto gymkhanaTournamentData) {
		this.gymkhanaTournamentData = gymkhanaTournamentData;
	}

	public List<PlayerDto> getAllPlayersData() {
		return gymkhanaTournamentData.getPlayersData();
	}

	public List<PlayerDto> getAllPlayersData(TournamentType tournamentType) {
		return getSubtournamentDao(tournamentType).getAllSubtournamentPlayers(getAllPlayersData());
	}

	public List<PlayerDto> getPlayersWaiting(TournamentType tournamentType, int roundNumber) {
		return getSubtournamentDao(tournamentType).getPlayersWaiting(getAllPlayersData(), roundNumber);
	}

	public List<PlayerDto> getPlayersDone(TournamentType tournamentType, int roundNumber) {
		return getSubtournamentDao(tournamentType).getPlayersDone(getAllPlayersData(), roundNumber);
	}

	public PlayerDto getPlayerCurrent(TournamentType tournamentType) {
		return getSubtournamentDao(tournamentType).getPlayerCurrent();
	}

	public void setPlayerCurrent(TournamentType tournamentType, PlayerDto player) {
		getSubtournamentDao(tournamentType).setPlayerCurrent(player);
	}

	public void addPlayerData(PlayerDto playerData) {
		gymkhanaTournamentData.getPlayersData().add(playerData);
	}

	public void removePlayerData(final long id) {
		gymkhanaTournamentData.getPlayersData().removeIf(playerData -> playerData.getId() == id);
	}

	public void updatePlayerData(final PlayerDto newPlayerDto, final TournamentType tournamentType) {
		for (final PlayerDto playerDto : gymkhanaTournamentData.getPlayersData()) {
			if (playerDto.getId() == newPlayerDto.getId()) {
				playerDto.update(newPlayerDto, tournamentType);
				break;
			}
		}
	}

	private ISubtournamentDao getSubtournamentDao(TournamentType tournamentType) {
		if (tournamentType == null) {
			return emptySubtournamentDao;
		}

		switch (tournamentType) {
		case GP8:
			return gp8TournamentDao;

		case CLASSIC_AMATEUR:
			return classicAmateurTournamentDao;
			
		case CLASSIC_PRO:
			return classicProTournamentDao;

		default:
			throw new UnsupportedOperationException("Not implemented yet (" + tournamentType + ")");
		}
	}
}
