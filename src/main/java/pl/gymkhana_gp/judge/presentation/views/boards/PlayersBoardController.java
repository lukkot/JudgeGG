package pl.gymkhana_gp.judge.presentation.views.boards;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import pl.gymkhana_gp.judge.controllers.TournamentsControllerBean;
import pl.gymkhana_gp.judge.converter.PlayerViewDataUtils;
import pl.gymkhana_gp.judge.model.dto.PlayerDto;
import pl.gymkhana_gp.judge.presentation.model.PlayerViewData;
import pl.gymkhana_gp.judge.presentation.model.PlayersBoardModel;
import pl.gymkhana_gp.judge.presentation.views.AboutInfoController;
import pl.gymkhana_gp.judge.presentation.views.FileLoaderController;
import pl.gymkhana_gp.judge.presentation.views.MainWindowController;
import pl.gymkhana_gp.judge.presentation.views.utils.EditCell;
import pl.gymkhana_gp.judge.utils.validation.IOnDataChangedListener;
import pl.gymkhana_gp.judge.utils.validation.IValidationErrorsListener;
import pl.gymkhana_gp.judge.utils.validation.TableViewValidationHelperBean;

import java.io.File;
import java.io.IOException;

@Component
@Scope("prototype")
public class PlayersBoardController implements IValidationErrorsListener, IOnDataChangedListener<PlayerViewData> {

	private static final Logger LOG = LogManager.getLogger(PlayersBoardController.class);

	@Autowired
	private TournamentsControllerBean tournamentControllerBean;

	@Autowired
	private PlayersBoardModel playersBoardModel;

	@Autowired
	private TableViewValidationHelperBean<PlayerViewData> tableViewValidationHelperBean;

	@Autowired
	private MainWindowController mainWindowController;

	@Autowired
	private PlayerViewDataUtils playerViewDataUtils;

	@Autowired
	private FileLoaderController fileLoaderController;

	@Autowired
	private AboutInfoController aboutInfoController;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private TableView<PlayerViewData> tablePlayers;
	@FXML
	private TableColumn<PlayerViewData, Integer> tablePlayersColumnStartNumber;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersColumnFirstName;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersColumnLastName;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersColumnNick;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersColumnSex;
	@FXML
	private TableColumn<PlayerViewData, String> tablePlayersColumnPlayerClass;

	@FXML
	private void initialize() {
		tableViewValidationHelperBean.setTableView(tablePlayers);
		tableViewValidationHelperBean.setValidationErrorsListener(this);
		tableViewValidationHelperBean.setOnDataChangedListener(this);

		tablePlayersColumnStartNumber.setCellValueFactory(cellData -> cellData.getValue().getStartNumber().asObject());
		tablePlayersColumnFirstName.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		tablePlayersColumnLastName.setCellValueFactory(cellData -> cellData.getValue().getLastName());
		tablePlayersColumnNick.setCellValueFactory(cellData -> cellData.getValue().getNick());
		tablePlayersColumnSex.setCellValueFactory(cellData -> cellData.getValue().getSex());
		tablePlayersColumnPlayerClass.setCellValueFactory(cellData -> cellData.getValue().getPlayerClass());


		tablePlayersColumnStartNumber.setCellFactory(column -> new EditCell<>(new IntegerStringConverter()));
		tablePlayersColumnFirstName.setCellFactory(column -> EditCell.createStringEditCell());
		tablePlayersColumnLastName.setCellFactory(column -> EditCell.createStringEditCell());
		tablePlayersColumnNick.setCellFactory(column -> EditCell.createStringEditCell());
		tablePlayersColumnSex.setCellFactory(ComboBoxTableCell.forTableColumn(PlayerViewData.getSexValues()));
		tablePlayersColumnPlayerClass
				.setCellFactory(ComboBoxTableCell.forTableColumn(PlayerViewData.getPlayerClassValues()));

		tablePlayersColumnStartNumber
				.setOnEditCommit(event -> tableViewValidationHelperBean.validateInput(event, "startNumber"));
		tablePlayersColumnFirstName
				.setOnEditCommit(event -> tableViewValidationHelperBean.validateInput(event, "firstName"));
		tablePlayersColumnLastName
				.setOnEditCommit(event -> tableViewValidationHelperBean.validateInput(event, "lastName"));
		tablePlayersColumnNick.setOnEditCommit(event -> tableViewValidationHelperBean.validateInput(event, "nick"));
		tablePlayersColumnSex.setOnEditCommit(event -> tableViewValidationHelperBean.validateInput(event, "sex"));
		tablePlayersColumnPlayerClass
				.setOnEditCommit(event -> tableViewValidationHelperBean.validateInput(event, "playerClass"));

		tablePlayers.setItems(playersBoardModel.getPlayersViewData());

		refreshPlayersData();
	}

	private void refreshPlayersData() {
		playersBoardModel.setPlayers(tournamentControllerBean.getAllPlayersData());
	}

	@FXML
	private void addPlayer() {
		PlayerDto playerData = new PlayerDto();
		tournamentControllerBean.addPlayerData(playerData);

		refreshPlayersData();
	}

	@FXML
	private void removePlayer() {
		PlayerViewData selectedPlayerViewData = tablePlayers.getSelectionModel().getSelectedItem();

		if (selectedPlayerViewData != null) {
			tournamentControllerBean.removePlayerData(selectedPlayerViewData.getPlayerDataId());

			refreshPlayersData();
		}
	}

	@Override
	public void onErrorMessageChanged(Errors errors) {
		mainWindowController.setErrorsMessage(errors);
	}

	@Override
	public void onDataChanged(PlayerViewData playerViewData) {
		tournamentControllerBean.updatePlayerData(playerViewDataUtils.convert(playerViewData));
	}

	public void onTabSelected() {
		refreshPlayersData();
	}

	@FXML
	private void onOpenFileAction() {
		fileLoaderController.selectDataFile((Stage) rootPane.getScene().getWindow(), false);
	}

	@FXML
	private void onAboutInfoAction() {
		aboutInfoController.presentAboutInfo();
	}

	@FXML
	private void onImportPlayers() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setTitle("Wybierz plik z wyeksportowanymi rejestracjami zawodników...");
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Plik zarejestrowanych zawodników (*.csv)", "*.csv"));

		File selectedFile = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

		if (selectedFile != null) {
			try {
				tournamentControllerBean.importPlayerDtos(selectedFile.getAbsolutePath());

				refreshPlayersData();
			} catch (IOException e) {
				LOG.error("Error while importing players.", e);
			}
		}
	}
}
