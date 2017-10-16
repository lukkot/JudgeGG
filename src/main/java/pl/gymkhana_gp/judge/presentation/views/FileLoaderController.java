package pl.gymkhana_gp.judge.presentation.views;

import java.io.File;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.gymkhana_gp.judge.ConfigurationBean;
import pl.gymkhana_gp.judge.controllers.TournamentsControllerBean;

@Component
public class FileLoaderController {

	@Autowired
	AbstractApplicationContext context;
	
	public void selectDataFile(Stage stage, boolean closeAppOnFailure) {
//		// TODO: usunąć przed wydaniem
//		if(1 == new Integer(1)) {
//			setXmlFile(new File("C:\\Programowanie\\Java-Spring\\GymkhanaGP\\JudgeGG\\!_JudgeGG-test2.xml"));
//			return;
//		}
		// TODO: usunąć przed wydaniem - koniec
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setTitle("Wybierz plik z istniejącymi danymi zawodów lub wskaż nowy plik...");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Data file (*.xml)", "*.xml"));

		File selectedFile = null;
		boolean stopChoosing = false;
		while (selectedFile == null && !stopChoosing) {
			selectedFile = fileChooser.showSaveDialog(stage);

			if (selectedFile == null) {
				if(closeAppOnFailure) {
					stopChoosing = askUserIfExitApp();
					// Aplikacja zostanie zamknięta po opuszczeniu funkcji
					if (stopChoosing) {
						Platform.exit();
					}
				} else {
					stopChoosing = true;
				}
			} else {
				setXmlFile(selectedFile);
			}
		}
	}
	
	private void setXmlFile(File selectedFile) {
		ConfigurationBean configurationBean = context.getBean("configurationBean", ConfigurationBean.class);
		configurationBean.setXmlFilePath(selectedFile.getAbsolutePath());

		TournamentsControllerBean tournamentsControllerBean = context.getBean("tournamentsControllerBean",
				TournamentsControllerBean.class);
		if (selectedFile.exists()) {
			tournamentsControllerBean.loadData();
		} else {
			tournamentsControllerBean.saveData();
		}
	}

	private boolean askUserIfExitApp() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Czy chcesz wybrac plik ponownie?");
		alert.setHeaderText("Czy chcesz wybrac plik ponownie?");
		alert.setContentText(
				"Wybór pliku jest konieczny do kontynuowania. Brak wyboru oznacza zamknięcie aplikacji. Czy chcesz wybrać plik ponownie?");

		Optional<ButtonType> result = alert.showAndWait();

		return !(result.get() == ButtonType.OK);
	}
}
