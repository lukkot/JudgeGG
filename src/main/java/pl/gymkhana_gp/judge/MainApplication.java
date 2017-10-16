package pl.gymkhana_gp.judge;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.gymkhana_gp.judge.controllers.WindowsControllerBean;
import pl.gymkhana_gp.judge.presentation.views.FileLoaderController;

@Configuration
@PropertySource("classpath:application.properties")
public class MainApplication extends Application {

	private static final Logger LOG = LogManager.getLogger(MainApplication.class);

	private AbstractApplicationContext context;
	private WindowsControllerBean windowsControllerBean;

	public static void main(String[] args) {
		LOG.info("********************************************************************************");
		LOG.info("Starting app...");
		LOG.info("********************************************************************************");

		launch(args);

		LOG.info("********************************************************************************");
		LOG.info("App stopped.");
		LOG.info("********************************************************************************");
	}

	@Override
	public void init() throws Exception {

		context = new ClassPathXmlApplicationContext(new String[] { "Beans.xml" });
		context.registerShutdownHook();

		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/pl/gymkhana_gp/judge/presentation/views/MainWindowView.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);
		fxmlLoader.setResources(ResourceBundle.getBundle("text-resources", new Locale("pl", "PL")));

		BorderPane rootNode = fxmlLoader.load();

		windowsControllerBean = context.getBean("windowsControllerBean", WindowsControllerBean.class);
		windowsControllerBean.setRootNode(rootNode);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Gymkhana GP - aplikacja sędziowska.");

		selectDataFile(stage);

		stage.setScene(new Scene(windowsControllerBean.getRootNode()));
		stage.show();

		windowsControllerBean.showBoardsChooser();
	}

	private void selectDataFile(Stage stage) {
		FileLoaderController fileLoaderController = context.getBean("fileLoaderController", FileLoaderController.class);
		fileLoaderController.selectDataFile(stage, true);
	}

	@Override
	public void stop() {
		windowsControllerBean.close();
		context.close();
	}
}
