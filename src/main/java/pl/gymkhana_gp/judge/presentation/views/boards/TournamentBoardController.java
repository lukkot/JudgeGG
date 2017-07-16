package pl.gymkhana_gp.judge.presentation.views.boards;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import pl.gymkhana_gp.judge.comparators.PlayerDtoComparator;
import pl.gymkhana_gp.judge.comparators.PlayerDtoComparator.ComparisonType;
import pl.gymkhana_gp.judge.controllers.TournamentsControllerBean;
import pl.gymkhana_gp.judge.converter.PlayerViewDataUtils;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;
import pl.gymkhana_gp.judge.presentation.views.MainWindowController;
import pl.gymkhana_gp.judge.presentation.views.boards.IBoardControllerHelper.ColumnType;
import pl.gymkhana_gp.judge.presentation.views.printviews.PrintingController;
import pl.gymkhana_gp.judge.presentation.views.utils.AutoNumberedCell;
import pl.gymkhana_gp.judge.presentation.views.utils.PlayerViewDataTableDecorator;
import pl.gymkhana_gp.judge.utils.validation.IOnDataChangedListener;
import pl.gymkhana_gp.judge.utils.validation.IValidationErrorsListener;
import pl.gymkhana_gp.judge.utils.validation.ObservableValueValidationOnFocusLostHelperBean;

@Component
@Scope("prototype")
public class TournamentBoardController
		implements IOnDataChangedListener<PlayerViewData>, IValidationErrorsListener {

	@Autowired
	private TournamentsControllerBean tournamentControllerBean;

	@Autowired
	private TournamentBoardModel tournamentBoardModel;

	@Autowired
	private PlayerViewDataUtils playerViewDataUtils;

	@Autowired
	private MainWindowController mainWindowController;

	@Autowired
	private ObservableValueValidationOnFocusLostHelperBean<PlayerViewData> tfMeasurementValidationHelper1;

	@Autowired
	private ObservableValueValidationOnFocusLostHelperBean<PlayerViewData> tfMeasurementValidationHelper2;

	@Autowired
	private PlayerViewDataTableDecorator playerViewDataTableDecorator;

	@Autowired
	private PrintingController printingController;

	private IBoardControllerHelper boardControllerHelper;

	@FXML
	private BorderPane rootPane;

	@FXML
	private SplitPane mainSplitPane;

	@FXML
	private ComboBox<String> comboRoundNumber;
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
	@FXML
	private Label labelCurrentPlayerStartNumber;
	@FXML
	private Label labelCurrentPlayerFirstName;
	@FXML
	private Label labelCurrentPlayerLastName;
	@FXML
	private Label labelCurrentPlayerNick;
	@FXML
	private TextField textFieldTimeCurrentAutomaticMeasurement;
	@FXML
	private Button buttonReadFromClock1;
	@FXML
	private TextField textFieldTime1;
	@FXML
	private TextField textFieldPenalty1;
	@FXML
	private Button buttonReadFromClock2;
	@FXML
	private TextField textFieldTime2;
	@FXML
	private TextField textFieldPenalty2;
	@FXML
	private ToggleButton toggleButtonClockListener;

	@FXML
	private void initialize() {
		initOtherObjects();

		initFxElements();
	}

	private void initOtherObjects() {
		tfMeasurementValidationHelper1.setValidationErrorsListener(this);
		tfMeasurementValidationHelper2.setValidationErrorsListener(this);
	}

	private void initFxElements() {
		// Podłączenie kolumn tabeli tablePlayersWaiting
		tablePlayersWaitingColumnRowNumber.setCellFactory(tableColumn -> new AutoNumberedCell());
		tablePlayersWaitingColumnStartNumber
				.setCellValueFactory(cellData -> cellData.getValue().getStartNumber().asObject());
		tablePlayersWaitingColumnFirstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		tablePlayersWaitingColumnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
		tablePlayersWaitingColumnNick.setCellValueFactory(cellData -> cellData.getValue().getNick());
		tablePlayersWaiting.setItems(tournamentBoardModel.getWaitingPlayersList());

		// Podłączenie kolumn tabeli tablePlayersDone
		tablePlayersDoneColumnRowNumber.setCellFactory(tableColumn -> new AutoNumberedCell());
		tablePlayersDoneColumnPlayerClass.setCellValueFactory(cellData -> cellData.getValue().getPlayerClass());
		tablePlayersDoneColumnSex.setCellValueFactory(cellData -> cellData.getValue().getSex());
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
		tablePlayersDone.setItems(tournamentBoardModel.getDonePlayersList());

		// Pola tekstowe
		textFieldTimeCurrentAutomaticMeasurement.textProperty()
				.bindBidirectional(tournamentBoardModel.getTimeCurrentAutomaticMeasurement());
		textFieldTime1.textProperty().bindBidirectional(tournamentBoardModel.getTime1());
		textFieldPenalty1.textProperty().bindBidirectional(tournamentBoardModel.getPenalty1(),
				new NumberStringConverter());
		textFieldTime2.textProperty().bindBidirectional(tournamentBoardModel.getTime2());
		textFieldPenalty2.textProperty().bindBidirectional(tournamentBoardModel.getPenalty2(),
				new NumberStringConverter());

		// Combobox - wybór rundy
		comboRoundNumber.getItems().addAll(FXCollections.observableArrayList("Runda 1", "Runda 2"));
		comboRoundNumber.getSelectionModel().select(0);
		comboRoundNumber.getSelectionModel().selectedIndexProperty();

		// Podpięcie elementów modelu
		// tournamentBoardModel.bindRoundNumberProperty(comboRoundNumber.getSelectionModel().selectedIndexProperty());
	}

	private void setEmphasizationToScoreTable() {
		int roundNumber = getRoundNumberSelected();

		tablePlayersDone.setRowFactory(tv -> playerViewDataTableDecorator.getRowDecorated(roundNumber));

		tablePlayersDoneColumnPlayerClass
				.setCellFactory(tc -> playerViewDataTableDecorator.getCellDecoratedPlayerClass(roundNumber));
		tablePlayersDoneColumnSex.setCellFactory(tc -> playerViewDataTableDecorator.getCellDecoratedSex(roundNumber));
	}

	public void onTabSelected() {
		refreshPaneData();
	}

	private void refreshPaneData() {
		int roundNumber = getRoundNumberSelected();

		setColumnsVisibility();

		refreshPlayersData(roundNumber);

		setCurrentPlayerData(tournamentBoardModel.getCurrentPlayer().get());

		setCurrentPlayerControlsAvailability(roundNumber);

		bindCurrentPlayerValidation();

		setEmphasizationToScoreTable();

		updateClockListenerState();
	}

	private void setColumnsVisibility() {
		tablePlayersDoneColumnPlayerClass.setVisible(boardControllerHelper.columnVisibility(ColumnType.PLAYER_CLASS));
	}

	private void refreshPlayersData(int roundNumber) {
		List<PlayerViewData> waitingPlayers = playerViewDataUtils
				.convert(boardControllerHelper.getPlayersWaiting(roundNumber));
		sortWaitingPlayersList(waitingPlayers, roundNumber);
		tournamentBoardModel.setWaitingPlayersList(waitingPlayers);

		List<PlayerViewData> donePlayers = playerViewDataUtils
				.convert(boardControllerHelper.getPlayersDone(roundNumber));
		donePlayers.sort(new PlayerDtoComparator(ComparisonType.ROUND_BEST));
		tournamentBoardModel.setDonePlayersList(donePlayers);

		PlayerViewData currentPlayer = playerViewDataUtils.convert(boardControllerHelper.getPlayerCurrent());
		tournamentBoardModel.setCurrentPlayer(currentPlayer);
	}

	private List<PlayerViewData> sortWaitingPlayersList(List<PlayerViewData> waitingPlayersList, int roundNumber) {
		if (roundNumber == 0) {
			waitingPlayersList.sort(new PlayerDtoComparator(ComparisonType.START_NUMBER));
		} else if (roundNumber == 1) {
			waitingPlayersList.sort(new PlayerDtoComparator(ComparisonType.ROUND_1, true));
		}

		return waitingPlayersList;
	}

	// @SuppressWarnings("unchecked")
	private void bindCurrentPlayerValidation() {
		tfMeasurementValidationHelper1.setParentObject(tournamentBoardModel.getCurrentPlayer().get());
		tfMeasurementValidationHelper1.setValidatedObject(textFieldTime1);
		textFieldTime1.focusedProperty().addListener(tfMeasurementValidationHelper1);

		tfMeasurementValidationHelper2.setParentObject(tournamentBoardModel.getCurrentPlayer().get());
		tfMeasurementValidationHelper2.setValidatedObject(textFieldTime2);
		textFieldTime2.focusedProperty().addListener(tfMeasurementValidationHelper2);
	}

	private void setCurrentPlayerControlsAvailability(int roundNumber) {
		boolean currentPlayerControlsDisabled = false;
		if (boardControllerHelper.getPlayerCurrent() == null) {
			currentPlayerControlsDisabled = true;
		}

		if (currentPlayerControlsDisabled
				|| boardControllerHelper.shouldControlsForMeasurementBeAvailable(0, roundNumber)) {
			textFieldTime1.setDisable(currentPlayerControlsDisabled);
			textFieldPenalty1.setDisable(currentPlayerControlsDisabled);
			buttonReadFromClock1.setDisable(currentPlayerControlsDisabled);
		}
		if (currentPlayerControlsDisabled
				|| boardControllerHelper.shouldControlsForMeasurementBeAvailable(1, roundNumber)) {
			textFieldTime2.setDisable(currentPlayerControlsDisabled);
			textFieldPenalty2.setDisable(currentPlayerControlsDisabled);
			buttonReadFromClock2.setDisable(currentPlayerControlsDisabled);
		}
	}

	private void setCurrentPlayerData(PlayerViewData player) {
		if (player == null) {
			labelCurrentPlayerStartNumber.setText("");
			labelCurrentPlayerFirstName.setText("");
			labelCurrentPlayerLastName.setText("");
			labelCurrentPlayerNick.setText("");
		} else {
			labelCurrentPlayerStartNumber.setText(player.getStartNumber().asString().get());
			labelCurrentPlayerFirstName.setText(player.getFirstName().get());
			labelCurrentPlayerLastName.setText(player.getLastName().get());
			labelCurrentPlayerNick.setText(player.getNick().get());
		}
	}

	public IBoardControllerHelper getBoardControllerHelper() {
		return boardControllerHelper;
	}

	public void setBoardControllerHelper(IBoardControllerHelper boardControllerHelper) {
		this.boardControllerHelper = boardControllerHelper;
		this.boardControllerHelper.setTournamentControllerBean(tournamentControllerBean);

		playerViewDataUtils.setPlayerViewDataToPlayerDtoConverterHelper(
				boardControllerHelper.getPlayerViewDataToPlayerDtoConverterHelper());
	}

	private int getRoundNumberSelected() {
		return comboRoundNumber.getSelectionModel().getSelectedIndex();
	}

	private void selectCurrentPlayer(TableView<PlayerViewData> tableView, int index) {
		if (index < 0) {
			return;
		}

		PlayerDto currentPlayer = playerViewDataUtils.convert(tableView.getItems().get(index));
		boardControllerHelper.setPlayerCurrent(currentPlayer);

		refreshPaneData();
	}

	@FXML
	private void onRemoveCurrentPlayer() {
		PlayerViewData playerViewDataCurrent = tournamentBoardModel.getCurrentPlayer().get();
		if (playerViewDataCurrent == PlayerViewData.NULL_PLAYER_VIEW_DATA) {
			return;
		}

		playerViewDataCurrent.getTime(getRoundNumberSelected()).set("");
		final TournamentType tournamentType = getTournamentType();

		tournamentControllerBean.updatePlayerData(playerViewDataUtils.convert(playerViewDataCurrent), tournamentType);
		boardControllerHelper.setPlayerCurrent(null);

		refreshPaneData();
	}

	@FXML
	private void onAcceptCurrentPlayer() {
		PlayerViewData playerViewDataAccepted = tournamentBoardModel.getCurrentPlayer().get();
		if (playerViewDataAccepted == PlayerViewData.NULL_PLAYER_VIEW_DATA) {
			return;
		}

		changeEmptyTimeInToSomeTime(playerViewDataAccepted, getRoundNumberSelected());

		final TournamentType tournamentType = getTournamentType();

		tournamentControllerBean.updatePlayerData(playerViewDataUtils.convert(playerViewDataAccepted), tournamentType);
		boardControllerHelper.setPlayerCurrent(null);
		refreshPaneData();
	}

	private TournamentType getTournamentType() {
		final TournamentType tournamentType;

		if (this.boardControllerHelper instanceof AbstractBoardControllerHelper) {
			tournamentType = ((AbstractBoardControllerHelper) this.boardControllerHelper).getTournamentType();
		} else {
			tournamentType = null;
		}
		return tournamentType;
	}

	private void changeEmptyTimeInToSomeTime(PlayerViewData playerViewDataAccepted, int roundNumber) {
		if ("".equals(playerViewDataAccepted.getTime(roundNumber).get())) {
			playerViewDataAccepted.getTime(roundNumber).set("00:00.000");
		}
	}

	@FXML
	private void onWithdrawSelectedPlayer() {
		PlayerViewData playerViewDataDoneSelected = tablePlayersDone.getSelectionModel().getSelectedItem();
		if (playerViewDataDoneSelected == null) {
			return;
		}

		boardControllerHelper.setPlayerCurrent(playerViewDataUtils.convert(playerViewDataDoneSelected));

		refreshPaneData();
	}

	@FXML
	private void onRoundChanged() {
		refreshPaneData();
	}

	@Override
	public void onErrorMessageChanged(Errors errors) {
		mainWindowController.setErrorsMessage(errors);
	}

	@Override
	public void onDataChanged(PlayerViewData data) {
		// TODO Auto-generated method stub
	}

	@FXML
	private void onPrintStartingList() {
		try {
			mainWindowController.clearMessages();
			mainWindowController.addMessage("Drukuję...");
			printingController.printWaitingList((Stage) rootPane.getScene().getWindow(), tournamentBoardModel);
			mainWindowController.clearMessages();
			mainWindowController.addMessage("Koniec drukowania!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void onPrintScoreBoard() {
		try {
			mainWindowController.clearMessages();
			mainWindowController.addMessage("Drukuję...");
			printingController.printScoreBoard((Stage) rootPane.getScene().getWindow(), tournamentBoardModel);
			mainWindowController.clearMessages();
			mainWindowController.addMessage("Koniec drukowania!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void onTimeAccepted(javafx.event.ActionEvent actionEvent) {
		if (actionEvent.getSource() == textFieldTime1) {
			textFieldPenalty1.requestFocus();
		} else if (actionEvent.getSource() == textFieldTime2) {
			textFieldPenalty2.requestFocus();
		}
	}

	@FXML
	private void onPenaltyAccepted(javafx.event.ActionEvent actionEvent) {
		rootPane.requestFocus();
	}

	@FXML
	private void doubleClickOnTable(MouseEvent event) {
		@SuppressWarnings("unchecked")
		TableView<PlayerViewData> tableView = (TableView<PlayerViewData>) event.getSource();

		if (tableView != null && event.getClickCount() == 2) {
			selectCurrentPlayer(tableView, tableView.getSelectionModel().getSelectedIndex());
		}
	}

	@FXML
	private void onFreezClockClicked(javafx.event.ActionEvent actionEvent) {
		updateClockListenerState();
	}

	private void updateClockListenerState() {
		boolean shouldIListenToTheClock = toggleButtonClockListener.isSelected();

		if (!shouldIListenToTheClock) {
			tournamentControllerBean.addClockListener(tournamentBoardModel);
		} else {
			tournamentControllerBean.removeClockListener(tournamentBoardModel);
		}
	}
	
	@FXML
	private void onButtonReadClock1() {
		readClockTo(textFieldTime1);
	}
	
	@FXML
	private void onButtonReadClock2() {
		readClockTo(textFieldTime2);
	}
	
	private void readClockTo(TextField textField) {
		textField.setText(textFieldTimeCurrentAutomaticMeasurement.getText());
	}
}
