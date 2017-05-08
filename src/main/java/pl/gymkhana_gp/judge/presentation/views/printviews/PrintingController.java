package pl.gymkhana_gp.judge.presentation.views.printviews;

import java.io.IOException;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.stage.Stage;
import pl.gymkhana_gp.judge.presentation.model.TournamentBoardModel;

@Component
public class PrintingController {
	@Autowired
	private AbstractApplicationContext context;

	public void printWaitingList(Stage stage, TournamentBoardModel tournamentBoardModel) throws IOException {
		printAbstractPlayerList(stage, getWaitingListController(tournamentBoardModel));
	}

	public void printScoreBoard(Stage stage, TournamentBoardModel tournamentBoardModel) throws IOException {
		printAbstractPlayerList(stage, getScoreBoardController(tournamentBoardModel));
	}

	public void printAbstractPlayerList(Stage stage, AbstractPlayerListController waitingListPrintController)
			throws IOException {
		PrinterJob job = PrinterJob.createPrinterJob();
		if (job != null) {
			if (job.showPrintDialog(stage)) {
				while (waitingListPrintController.nextPage()) {
					job.printPage(waitingListPrintController.getRootPane());
				}
			}
			job.endJob();
		}
	}

	public WaitingListPrintController getWaitingListController(TournamentBoardModel tournamentBoardModel)
			throws IOException {
		// AnchorPane waitingListPrintView;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass()
				.getResource("/pl/gymkhana_gp/judge/presentation/views/printviews/WaitingListPrintView.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);
		fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

		// waitingListPrintView =
		fxmlLoader.load();

		WaitingListPrintController controller = fxmlLoader.getController();
		controller.setPlayersToPrint(tournamentBoardModel.getWaitingPlayersList());

		return controller;
	}

	public ScoreBoardPrintController getScoreBoardController(TournamentBoardModel tournamentBoardModel)
			throws IOException {
		// AnchorPane waitingListPrintView;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass()
				.getResource("/pl/gymkhana_gp/judge/presentation/views/printviews/ScoreBoardPrintView.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);
		fxmlLoader.setResources(ResourceBundle.getBundle("text-resources"));

		// waitingListPrintView =
		fxmlLoader.load();

		ScoreBoardPrintController controller = fxmlLoader.getController();
		controller.setPlayersToPrint(tournamentBoardModel.getDonePlayersList());

		return controller;
	}
}
