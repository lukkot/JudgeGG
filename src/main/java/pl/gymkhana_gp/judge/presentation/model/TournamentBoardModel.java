package pl.gymkhana_gp.judge.presentation.model;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.gymkhana_gp.judge.model.dao.IClockObserver;
import pl.gymkhana_gp.judge.model.dto.TimeDto;

@Component
@Scope("prototype")
public class TournamentBoardModel implements IClockObserver {

	private final ObservableList<PlayerViewData> waitingPlayersList = FXCollections.observableArrayList();

	private final ObservableList<PlayerViewData> donePlayersList = FXCollections.observableArrayList();

	private PlayerViewDataObservable currentPlayer = new PlayerViewDataObservable(PlayerViewData.NULL_PLAYER_VIEW_DATA);

	private final StringProperty timeCurrentAutomaticMeasurement = new SimpleStringProperty();
	
	private final StringProperty time1 = new SimpleStringProperty();

	private final IntegerProperty penalty1 = new SimpleIntegerProperty();

	private final StringProperty time2 = new SimpleStringProperty();

	private final IntegerProperty penalty2 = new SimpleIntegerProperty();

	public ObservableList<PlayerViewData> getWaitingPlayersList() {
		return waitingPlayersList;
	}

	public void setWaitingPlayersList(List<PlayerViewData> waitingPlayersList) {
		this.waitingPlayersList.clear();
		this.waitingPlayersList.addAll(waitingPlayersList);
	}

	public ObservableList<PlayerViewData> getDonePlayersList() {
		return donePlayersList;
	}

	public void setDonePlayersList(List<PlayerViewData> donePlayersList) {
		this.donePlayersList.clear();
		this.donePlayersList.addAll(donePlayersList);
	}

	public void setCurrentPlayer(PlayerViewData playerViewData) {
		if (playerViewData == null) {
			playerViewData = PlayerViewData.NULL_PLAYER_VIEW_DATA;
		}

		currentPlayer.setPlayerViewData(playerViewData);

		time1.bindBidirectional(playerViewData.getTime1());
		penalty1.bindBidirectional(playerViewData.getPenalty1());
		time2.bindBidirectional(playerViewData.getTime2());
		penalty2.bindBidirectional(playerViewData.getPenalty2());
	}

	public PlayerViewDataObservable getCurrentPlayer() {
		return currentPlayer;
	}

	public StringProperty getTimeCurrentAutomaticMeasurement() {
		return timeCurrentAutomaticMeasurement;
	}

	public StringProperty getTime1() {
		return time1;
	}

	public IntegerProperty getPenalty1() {
		return penalty1;
	}

	public StringProperty getTime2() {
		return time2;
	}

	public IntegerProperty getPenalty2() {
		return penalty2;
	}

	@Override
	public void onClockUpdate(TimeDto time) {
		timeCurrentAutomaticMeasurement.set(time.getTimeFormatted());
	}
}
