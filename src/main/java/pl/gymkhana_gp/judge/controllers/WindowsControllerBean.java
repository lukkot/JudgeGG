package pl.gymkhana_gp.judge.controllers;

import java.io.IOException;
import java.util.ResourceBundle;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import pl.gymkhana_gp.judge.presentation.views.boards.ClassicAmateurBoardControllerHelper;
import pl.gymkhana_gp.judge.presentation.views.boards.ClassicProBoardControllerHelper;
import pl.gymkhana_gp.judge.presentation.views.boards.Gp8BoardControllerHelper;
import pl.gymkhana_gp.judge.presentation.views.boards.PlayersBoardController;
import pl.gymkhana_gp.judge.presentation.views.boards.TournamentBoardController;
import pl.gymkhana_gp.judge.services.ClockService;

@Component
public class WindowsControllerBean {

	private static final Logger LOG = LogManager.getLogger(WindowsControllerBean.class);

	@Autowired
	private AbstractApplicationContext context;
	
	@Autowired
	private ClockService clockService;

	private AnchorPane boardsChooserPane;
	private AnchorPane playersBoardPane;
	private BorderPane gp8BoardPane;
	private BorderPane classicAmateurBoardPane;
	private BorderPane classicProBoardPane;
	
	private AnchorPane optionsPane;

	private BorderPane rootNode;

	private PlayersBoardController playersBoardController;
	private TournamentBoardController gp8BoardController;
	private TournamentBoardController classicAmateurBoardController;
	private TournamentBoardController classicProBoardController;

	////////////////////////////////////////////////////////////////////////////////
	// PUBLICZNE
	////////////////////////////////////////////////////////////////////////////////
	public void showBoardsChooser() {
		try {
			rootNode.setCenter(getBoardsChooserPane());
		} catch (IOException e) {
			LOG.error("Problem with showing board chooser.", e);
		}
	}
	
	public void close() {
		clockService.stopReading();
	}

	////////////////////////////////////////////////////////////////////////////////
	// PRYWATNE
	////////////////////////////////////////////////////////////////////////////////
	private AnchorPane getBoardsChooserPane() throws IOException {
		if (boardsChooserPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/BoardsChooserView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			boardsChooserPane = fxmlLoader.load();

			initBoardsChooser();
		}
		return boardsChooserPane;
	}

	private void initBoardsChooser() throws IOException {
		getPlayersBoard();
		getGp8Board();
		getClassicAmateurBoard();
		getClassicProBoard();
		getOptionsPane();

		Tab tabPlayers = new Tab();
		tabPlayers.setText("Zawodnicy");
		tabPlayers.setContent(playersBoardPane);
		tabPlayers.setOnSelectionChanged(event -> playersBoardController.onTabSelected());

		Tab tabGp8 = new Tab();
		tabGp8.setText("GP 8");
		tabGp8.setContent(gp8BoardPane);
		tabGp8.setOnSelectionChanged(event -> gp8BoardController.onTabSelected());

		Tab tabClassicAmateur = new Tab();
		tabClassicAmateur.setText("Amatorzy");
		tabClassicAmateur.setContent(classicAmateurBoardPane);
		tabClassicAmateur.setOnSelectionChanged(event -> classicAmateurBoardController.onTabSelected());

		Tab tabClassicPro = new Tab();
		tabClassicPro.setText("Pro");
		tabClassicPro.setContent(classicProBoardPane);
		tabClassicPro.setOnSelectionChanged(event -> classicProBoardController.onTabSelected());

		Tab tabOptions = new Tab();
		tabOptions.setText("Opcje");
		tabOptions.setContent(optionsPane);

		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabPlayers);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabGp8);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabClassicAmateur);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabClassicPro);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabOptions);
	}

	private void getPlayersBoard() throws IOException {
		if (playersBoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/PlayersBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			playersBoardPane = fxmlLoader.load();

			playersBoardController = fxmlLoader.getController();
		}

	}

	private void getGp8Board() throws IOException {
		if (gp8BoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/TournamentBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			gp8BoardPane = fxmlLoader.load();

			gp8BoardController = fxmlLoader.getController();
			gp8BoardController.setBoardControllerHelper(new Gp8BoardControllerHelper());
		}

	}

	private void getClassicAmateurBoard() throws IOException {
		if (classicAmateurBoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/TournamentBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			classicAmateurBoardPane = fxmlLoader.load();

			classicAmateurBoardController = fxmlLoader.getController();
			classicAmateurBoardController.setBoardControllerHelper(new ClassicAmateurBoardControllerHelper());
		}

	}

	private void getClassicProBoard() throws IOException {
		if (classicProBoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/TournamentBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			classicProBoardPane = fxmlLoader.load();

			classicProBoardController = fxmlLoader.getController();
			classicProBoardController.setBoardControllerHelper(new ClassicProBoardControllerHelper());
		}

	}

	private void getOptionsPane() throws IOException {
		if (optionsPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/OptionsView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			optionsPane = fxmlLoader.load();
		}

	}

	////////////////////////////////////////////////////////////////////////////////
	// AKCESORY
	////////////////////////////////////////////////////////////////////////////////
	public Parent getRootNode() {
		return rootNode;
	}

	public void setRootNode(BorderPane rootNode) {
		this.rootNode = rootNode;
	}
}
