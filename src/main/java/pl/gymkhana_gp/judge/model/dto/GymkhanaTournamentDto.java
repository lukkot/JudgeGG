package pl.gymkhana_gp.judge.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "gymkhanaTournament")
public class GymkhanaTournamentDto {
	private List<PlayerDto> players = new ArrayList<>();

	@XmlElementWrapper
	@XmlElement(name = "player")
	public List<PlayerDto> getPlayersData() {
		return players;
	}

	public void setPlayers(List<PlayerDto> players) {
		this.players = players;
	}
}
