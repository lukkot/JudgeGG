package pl.gymkhana_gp.judge.services;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public interface IClockObserver {
	void onClockUpdate(TimeDto time);
}
