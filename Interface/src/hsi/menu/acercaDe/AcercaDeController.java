package hsi.menu.acercaDe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase encargada de crear la ventana que contendrá la información básica de la aplicación.
 * @author Cristo
 *
 */
public class AcercaDeController implements Initializable {

	private Stage stage;
	
	// view
	@FXML
	private VBox view;

	public AcercaDeController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AcercaDeView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	public void crearVentana() {
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Acerca de");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}

}
