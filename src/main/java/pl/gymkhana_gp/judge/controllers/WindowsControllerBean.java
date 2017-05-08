package pl.gymkhana_gp.judge.controllers;

import java.io.IOException;
import java.util.ResourceBundle;

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

@Component
public class WindowsControllerBean {
	@Autowired
	private AbstractApplicationContext context;

	private AnchorPane boardsChooserPane;
	private AnchorPane playersBoardPane;
	private BorderPane gp8BoardPane;
	private BorderPane classicAmateurBoardPane;
	private BorderPane classicProBoardPane;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabPlayers);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabGp8);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabClassicAmateur);
		((TabPane) boardsChooserPane.lookup("#boardsTabs")).getTabs().add(tabClassicPro);
	}

	private AnchorPane getPlayersBoard() throws IOException {
		if (playersBoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/PlayersBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			playersBoardPane = fxmlLoader.load();

			playersBoardController = fxmlLoader.getController();
		}

		return playersBoardPane;
	}

	private BorderPane getGp8Board() throws IOException {
		if (gp8BoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/TournamentBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			gp8BoardPane = fxmlLoader.load();

			gp8BoardController = fxmlLoader.getController();
			gp8BoardController.setBoardControllerHelper(new Gp8BoardControllerHelper());
		}

		return gp8BoardPane;
	}

	private BorderPane getClassicAmateurBoard() throws IOException {
		if (classicAmateurBoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/TournamentBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			classicAmateurBoardPane = fxmlLoader.load();

			classicAmateurBoardController = fxmlLoader.getController();
			classicAmateurBoardController.setBoardControllerHelper(new ClassicAmateurBoardControllerHelper());
		}

		return classicAmateurBoardPane;
	}

	private BorderPane getClassicProBoard() throws IOException {
		if (classicProBoardPane == null) {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/boards/TournamentBoardView.fxml"));
			fxmlLoader.setControllerFactory(context::getBean);
			fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

			classicProBoardPane = fxmlLoader.load();

			classicProBoardController = fxmlLoader.getController();
			classicProBoardController.setBoardControllerHelper(new ClassicProBoardControllerHelper());
		}

		return classicProBoardPane;
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
