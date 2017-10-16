package pl.gymkhana_gp.judge.presentation.views;

import org.springframework.stereotype.Component;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

@Component
public class AboutInfoController {
	public void presentAboutInfo() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("O programie");
		alert.setHeaderText("O programie.");
		alert.setContentText("Autorzy:\n" + " - Łukasz Kotyński\n" + " - Filip Pająk\n" + "Dla:\n"
				+ " - Klub Motocyklistów Politechniki Łódzkiej\n" + " - Gymkhana GP\n" + "Informacje o grafikach:\n"
				+ " - Designed by Dave Gandy, Freepik, Gregor Cresnar from Flaticon.\n");

		alert.showAndWait();
	}
}
