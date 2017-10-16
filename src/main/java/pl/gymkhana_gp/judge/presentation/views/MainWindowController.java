package pl.gymkhana_gp.judge.presentation.views;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

@Component
public class MainWindowController {
	@FXML
	private Label testLabel;
	@FXML
	private VBox messagesPane;
	
	@Resource
    MessageSource messageSource;
	
	@Autowired
    Environment env;
	
	public void print() {
		System.out.println("Wiadomość");
		System.out.println("Test prop: " + env.getProperty("test.prop"));
		System.out.println("Message: " + messageSource.getMessage("message", null, "Default!", Locale.ROOT));
	}
	
	public void clearMessages() {
		messagesPane.getChildren().clear();
	}
	
	public void addMessage(String message) {
		Label label = new Label(message);
		label.setTextFill(Color.RED);
		messagesPane.getChildren().add(label);
	}
	
	public void setErrorsMessage(Errors errors) {
		clearMessages();

		for (ObjectError error : errors.getAllErrors()) {
			addMessage(" - " + error.getObjectName() + " - " + error.getDefaultMessage());
		}
	}
}
