package pl.gymkhana_gp.judge.presentation.views.utils;

import org.springframework.stereotype.Component;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import pl.gymkhana_gp.judge.model.enums.PlayerClass;
import pl.gymkhana_gp.judge.model.enums.Sex;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;

@Component
public class PlayerViewDataTableDecorator {
	private final static int COLOR_ROUND_CURRENT = 0;
	private final static int COLOR_ROUND_OTHER = 1;
	private final static int COLOR_ROUND_SIZE = 2;

	private final static int COLOR_ROW_FIRST = 0;
	private final static int COLOR_ROW_SECOND = 1;
	private final static int COLOR_ROW_SIZE = 2;

	private final static int COLOR_WOMAN = 0;
	private final static int COLOR_AMATEUR = 1;
	private final static int COLOR_PRO = 2;
	private final static int COLOR_SIZE = 3;

	private static final String COLOR_LAST_RIDING = "#ffff66";

	private final String backgroundColor[][][];

	public PlayerViewDataTableDecorator() {
		backgroundColor = new String[COLOR_SIZE][COLOR_ROUND_SIZE][COLOR_ROW_SIZE];

		backgroundColor[COLOR_WOMAN][COLOR_ROUND_CURRENT][COLOR_ROW_FIRST] = "#009933"; //"#f05274";
		backgroundColor[COLOR_WOMAN][COLOR_ROUND_CURRENT][COLOR_ROW_SECOND] = "#00cc00"; //"#f37c95";
		backgroundColor[COLOR_WOMAN][COLOR_ROUND_OTHER][COLOR_ROW_FIRST] = "#ffe4e1";// "#f2bde7";
		backgroundColor[COLOR_WOMAN][COLOR_ROUND_OTHER][COLOR_ROW_SECOND] = "#ffc0cb";// "#ff8fe7";

		backgroundColor[COLOR_AMATEUR][COLOR_ROUND_CURRENT][COLOR_ROW_FIRST] = "#CCCCDD";
		backgroundColor[COLOR_AMATEUR][COLOR_ROUND_CURRENT][COLOR_ROW_SECOND] = "#BBBBCC";
		backgroundColor[COLOR_AMATEUR][COLOR_ROUND_OTHER][COLOR_ROW_FIRST] = "#f8f8ff";
		backgroundColor[COLOR_AMATEUR][COLOR_ROUND_OTHER][COLOR_ROW_SECOND] = "#e9e9ff";

		backgroundColor[COLOR_PRO][COLOR_ROUND_CURRENT][COLOR_ROW_FIRST] = "#f05274"; //"#ffff00";
		backgroundColor[COLOR_PRO][COLOR_ROUND_CURRENT][COLOR_ROW_SECOND] = "#f37c95"; //"#ffcc00";
		backgroundColor[COLOR_PRO][COLOR_ROUND_OTHER][COLOR_ROW_FIRST] = "#f8f8ff";
		backgroundColor[COLOR_PRO][COLOR_ROUND_OTHER][COLOR_ROW_SECOND] = "#e9e9ff";
	}

	public TableRow<PlayerViewData> getRowDecorated(int roundNumber, long lastPlayerDataId) {
		return new TableRow<PlayerViewData>() {
			@Override
			public void updateItem(PlayerViewData item, boolean empty) {
				super.updateItem(item, empty);

				if (item == null) {
					setStyle("");
				} else {
					setStyle(item, roundNumber);
				}
			}

			private void setStyle(PlayerViewData item, int roundNumber) {

				int playerRound = getPlayerRound(item, roundNumber);
				int rowType = getRowType(getIndex());

				if(item.getPlayerDataId() == lastPlayerDataId) {
					setStyle("-fx-background-color: " + COLOR_LAST_RIDING + ";");
				} else if (playerRound != COLOR_ROUND_OTHER) {
					setStyle("-fx-background-color: " + backgroundColor[COLOR_AMATEUR][playerRound][rowType] + ";");
				} else {
					setStyle("");
				}
			}
		};
	}

	public TableCell<PlayerViewData, String> getCellDecoratedSex() {
		return new TableCell<PlayerViewData, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				PlayerViewData playerViewData = (PlayerViewData) getTableRow().getItem();
				
				if(item == null || playerViewData == null) {
					setStyle("");
					setText(null);
				} else {
					setText(item);
					
					int rowType = getRowType(getIndex());
					
					if(getPlayerType(playerViewData) == COLOR_WOMAN) {
						setStyle("-fx-background-color:" + backgroundColor[COLOR_WOMAN][COLOR_ROUND_CURRENT][rowType] + ";");
					} else {
						setStyle("");
					}
				}
			}
		};
	}

	public TableCell<PlayerViewData, String> getCellDecoratedPlayerClass() {
		return new TableCell<PlayerViewData, String>() {
			@Override
			public void updateItem(String item, boolean empty) {
				super.updateItem(item, empty);

				PlayerViewData playerViewData = (PlayerViewData) getTableRow().getItem();
				
				if(item == null || playerViewData == null) {
					setStyle("");
					setText(null);
				} else {
					setText(item);
					
					int rowType = getRowType(getIndex());
					
					if(getPlayerType(playerViewData) == COLOR_PRO) {
						setStyle("-fx-background-color:" + backgroundColor[COLOR_PRO][COLOR_ROUND_CURRENT][rowType] + ";");
					} else {
						setStyle("");
					}
				}
			}
		};
	}

	private int getPlayerType(PlayerViewData item) {
		if (Sex.FEMALE.toString().equals(item.getSex().get())) {
			return COLOR_WOMAN;
		} else if (PlayerClass.PRO.toString().equals(item.getPlayerClass().get())) {
			return COLOR_PRO;
		} else {
			return COLOR_AMATEUR;
		}
	}

	private int getPlayerRound(PlayerViewData item, int roundNumber) {
		if ("".equals(item.getTime(roundNumber).get())) {
			return COLOR_ROUND_OTHER;
		} else {
			return COLOR_ROUND_CURRENT;
		}
	}

	private int getRowType(int index) {
		return index % 2;
	}
}
