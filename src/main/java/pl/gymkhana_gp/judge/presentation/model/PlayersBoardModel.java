package pl.gymkhana_gp.judge.presentation.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.gymkhana_gp.judge.converter.PlayerViewDataUtils;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;

@Component
@Scope("prototype")
public class PlayersBoardModel {
	@Autowired
	private PlayerViewDataUtils playerViewDataUtils;
	
	private ObservableList<PlayerViewData> playersViewData = FXCollections.observableArrayList();
	
	public void setPlayers(List<PlayerDto> playersDto) {
		playerViewDataUtils.convert(playersViewData, playersDto);
	}
	
	public List<PlayerDto> getPlayers() {
		return playerViewDataUtils.convert(playersViewData);
	}

	public ObservableList<PlayerViewData> getPlayersViewData() {
		return playersViewData;
	}
}
