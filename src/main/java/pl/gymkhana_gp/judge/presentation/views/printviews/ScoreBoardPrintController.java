package pl.gymkhana_gp.judge.presentation.views.printviews;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;
import pl.gymkhana_gp.judge.presentation.views.utils.AutoNumberedCell;

@Component
@Scope("prototype")
public class ScoreBoardPrintController extends AbstractPlayerListController {
	
	@FXML
	protected AnchorPane rootPane;
	
	@FXML
	private TableView<PlayerViewData> tablePlayersDone;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersDoneColumnRowNumber;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnPlayerClass;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnSex;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersDoneColumnStartNumber;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnFirstName;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnLastName;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnNick;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumn1Time;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersDoneColumn1Penalty;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumn1FullMeasurement;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumn2Time;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersDoneColumn2Penalty;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumn2FullMeasurement;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnBestTime;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersDoneColumnBestPenalty;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersDoneColumnBestFullMeasurement;
	
	public ScoreBoardPrintController() {
		super(20, 22);
	}
	
	@FXML
	private void initialize() {
		// Podłączenie kolumn tabeli tablePlayersWaiting
//		tablePlayersDoneColumnPlayerClass.setCellValueFactory(cellData -> cellData.getValue().getPlayerClass());
//		tablePlayersDoneColumnSex.setCellValueFactory(cellData -> cellData.getValue().getSex());
		tablePlayersDoneColumnRowNumber.setCellFactory(tableColumn -> new AutoNumberedCell());
		tablePlayersDoneColumnStartNumber
				.setCellValueFactory(cellData -> cellData.getValue().getStartNumber().asObject());
		tablePlayersDoneColumnFirstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		tablePlayersDoneColumnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
		tablePlayersDoneColumnNick.setCellValueFactory(cellData -> cellData.getValue().getNick());
		tablePlayersDoneColumn1Time.setCellValueFactory(cellData -> cellData.getValue().getTime1());
		tablePlayersDoneColumn1Penalty.setCellValueFactory(cellData -> cellData.getValue().getPenalty1().asObject());
		tablePlayersDoneColumn1FullMeasurement
				.setCellValueFactory(cellData -> cellData.getValue().getFullMeasurement1());
		tablePlayersDoneColumn2Time.setCellValueFactory(cellData -> cellData.getValue().getTime2());
		tablePlayersDoneColumn2Penalty.setCellValueFactory(cellData -> cellData.getValue().getPenalty2().asObject());
		tablePlayersDoneColumn2FullMeasurement
				.setCellValueFactory(cellData -> cellData.getValue().getFullMeasurement2());
		tablePlayersDoneColumnBestTime.setCellValueFactory(cellData -> cellData.getValue().getTimeBest());
		tablePlayersDoneColumnBestPenalty
				.setCellValueFactory(cellData -> cellData.getValue().getPenaltyBest().asObject());
		tablePlayersDoneColumnBestFullMeasurement
				.setCellValueFactory(cellData -> cellData.getValue().getFullMeasurementBest());
		
		tablePlayersDone.setFixedCellSize(getRowHeight());
	}
	
	@Override
	protected AnchorPane getRootPane() {
		return rootPane;
	}

	@Override
	protected TableView<PlayerViewData> getTableView() {
		return tablePlayersDone;
	}
}
