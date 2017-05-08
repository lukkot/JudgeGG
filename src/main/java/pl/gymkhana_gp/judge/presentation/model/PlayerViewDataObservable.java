package pl.gymkhana_gp.judge.presentation.model;

import javafx.beans.binding.ObjectBinding;

public class PlayerViewDataObservable extends ObjectBinding<PlayerViewData> {

	private PlayerViewData playerViewData;

	public PlayerViewDataObservable(PlayerViewData playerViewData) {
		setPlayerViewData(playerViewData);
	}

	@Override
	protected PlayerViewData computeValue() {
		return playerViewData;
	}

	public void setPlayerViewData(PlayerViewData playerViewData) {
		this.playerViewData = playerViewData;
		bind(playerViewData.getStartNumber(), playerViewData.getFirstName(), playerViewData.getLastName(),
				playerViewData.getNick(), playerViewData.getSex(), playerViewData.getPlayerClass());
		
		invalidate();
	}
}
