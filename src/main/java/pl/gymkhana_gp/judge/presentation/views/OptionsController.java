package pl.gymkhana_gp.judge.presentation.views;

import java.util.List;

import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fazecast.jSerialComm.SerialPort;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import pl.gymkhana_gp.judge.controllers.TournamentsControllerBean;
import pl.gymkhana_gp.judge.model.dao.ExternalClockSettings;
import pl.gymkhana_gp.judge.model.enums.TournamentType;
import pl.gymkhana_gp.judge.services.ClockService;

@Component
public class OptionsController {
	@Autowired
	private
	ClockService clockService;
	
	@Autowired
	private TournamentsControllerBean tournamentsControllerBean;

	// Ustawienie portu zegara
	@FXML
	ComboBox<String> comboBoxPortName;
	@FXML
	ComboBox<Integer> comboBoxBaudrate;
	@FXML
	ComboBox<Integer> comboBoxParity;
	@FXML
	ComboBox<Integer> comboBoxDataBits;
	@FXML
	ComboBox<Integer> comboBoxStopBits;
	@FXML
	Button buttonStart;
	@FXML
	Button buttonStop;

	// Wyniki HTML
	@FXML
	TextArea textAreaScoreBoardHtmlGp8;
	@FXML
	TextArea textAreaScoreBoardHtmlClassicAmateur;
	@FXML
	TextArea textAreaScoreBoardHtmlClassicPro;

	@FXML
	public void initialize() {
		List<String> portsNames = clockService.getSerialPorts();
		if(portsNames != null && !portsNames.isEmpty()) {
			comboBoxPortName.getItems().addAll(portsNames);
			comboBoxPortName.getSelectionModel().select(0);
		}
		
		comboBoxBaudrate.getItems().addAll(FXCollections.observableArrayList(110, 150, 300, 1200, 2400, 4800, 9600, 19200, 38400, 57600));
		comboBoxBaudrate.setValue(57600);

		comboBoxParity.getItems().addAll(FXCollections.observableArrayList(SerialPort.NO_PARITY));
		comboBoxParity.setValue(SerialPort.NO_PARITY);

		comboBoxDataBits.getItems().addAll(FXCollections.observableArrayList(5, 6, 7, 8));
		comboBoxDataBits.setValue(8);

		comboBoxStopBits.getItems().addAll(FXCollections.observableArrayList(1, 2));
		comboBoxStopBits.setValue(1);
	}
	
	@FXML
	public void onButtonStart() {
		buttonStart.setDisable(true);
		buttonStop.setDisable(false);
		
		clockService.setExternalClockSettings(createSettings());
		clockService.startReading();
	}
	
	private ExternalClockSettings createSettings() {
		return new ExternalClockSettings(
				comboBoxPortName.getValue(),
				comboBoxBaudrate.getValue(),
				comboBoxParity.getValue(),
				comboBoxDataBits.getValue(),
				comboBoxStopBits.getValue()
				);
	}
	
	@FXML
	public void onButtonStop() {
		buttonStart.setDisable(false);
		buttonStop.setDisable(true);
		
		clockService.stopReading();
		
	}

	@FXML
	public void onButtonScoreBoardHtmlGenerate() {
		textAreaScoreBoardHtmlGp8.setText(
				tournamentsControllerBean.generateScoreBoardHtml(TournamentType.GP8)
		);
		textAreaScoreBoardHtmlClassicAmateur.setText(
				tournamentsControllerBean.generateScoreBoardHtml(TournamentType.CLASSIC_AMATEUR)
		);
		textAreaScoreBoardHtmlClassicPro.setText(
				tournamentsControllerBean.generateScoreBoardHtml(TournamentType.CLASSIC_PRO)
		);
	}
}
