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
public class WaitingListPrintController extends AbstractPlayerListController {
	
	@FXML
	protected AnchorPane rootPane;
	
	@FXML
	private TableView<PlayerViewData> tablePlayersWaiting;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersWaitingColumnRowNumber;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersWaitingColumnStartNumber;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersWaitingColumnFirstName;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersWaitingColumnLastName;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersWaitingColumnNick;
	
	@FXML
	private void initialize() {
		// Podłączenie kolumn tabeli tablePlayersWaiting
		tablePlayersWaitingColumnRowNumber.setCellFactory(tableColumn -> new AutoNumberedCell());
		tablePlayersWaitingColumnStartNumber
				.setCellValueFactory(cellData -> cellData.getValue().getStartNumber().asObject());
		tablePlayersWaitingColumnFirstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		tablePlayersWaitingColumnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
		tablePlayersWaitingColumnNick.setCellValueFactory(cellData -> cellData.getValue().getNick());
		
		tablePlayersWaiting.setFixedCellSize(getRowHeight());
	}
	
	@Override
	protected AnchorPane getRootPane() {
		return rootPane;
	}

	@Override
	protected TableView<PlayerViewData> getTableView() {
		return tablePlayersWaiting;
	}
}
