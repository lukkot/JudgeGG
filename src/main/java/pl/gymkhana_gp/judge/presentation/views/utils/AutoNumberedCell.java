package pl.gymkhana_gp.judge.presentation.views.utils;

import javafx.scene.control.TableCell;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

public class AutoNumberedCell extends TableCell<PlayerViewData, Integer> {
	@Override
	protected void updateItem(Integer item, boolean empty) {
		setText(String.valueOf(getIndex() + 1));
	}
}
