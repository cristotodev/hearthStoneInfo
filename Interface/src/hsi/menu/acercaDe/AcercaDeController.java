package hsi.menu.acercaDe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.controlErrores.ControllerControlesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase encargada de crear la ventana que contendrá la información básica de la
 * aplicación.
 * 
 * @author Cristo
 *
 */
public class AcercaDeController implements Initializable {

	private Stage stage;

	// view
	@FXML
	private VBox view;

	@FXML
	private Hyperlink cristoGithub;

	@FXML
	private Hyperlink carlosGithub;

	public AcercaDeController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AcercaDeView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		cristoGithub.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				IniciarNavegador("https://github.com/Cristoto");

			}
		});

		carlosGithub.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				IniciarNavegador("https://github.com/Xibhu");

			}
		});

	}

	private void IniciarNavegador(String parametro) {
		String firefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		String chrome = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
		String opera = "C:\\Program Files\\Opera\\launcher.exe";
		String explorer = "C:\\Program Files\\internet explorer\\iexplore.exe";
		
		

		try {
			if (new File(firefox).exists()) {
				new ProcessBuilder(firefox, parametro).start();
			} else if (new File(chrome).exists()) {
				new ProcessBuilder(chrome, parametro).start();
			} else if (new File(opera).exists()) {
				new ProcessBuilder(opera, parametro).start();
			} else if (new File(explorer).exists()) {
				new ProcessBuilder(explorer, parametro).start();
			} else {
				new ControllerControlesView("No tienes ningún navegador, vete a plantar papas.", null).crearVentana();
			}
		} catch (IOException e) {
			try {
				new ControllerControlesView("Error al utilizar el navegador.", null).crearVentana();
			} catch (IOException e1) {
				
			}
		}

	}

	public void crearVentana() {
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("acercaDeEstilos.css").toExternalForm());

		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Acerca de");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.showAndWait();
	}

}
