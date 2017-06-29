package pl.gymkhana_gp.judge.model.dao;

import pl.gymkhana_gp.judge.model.dto.TimeDto;

public interface IClockObserver {
	void onClockUpdate(TimeDto time);
}
