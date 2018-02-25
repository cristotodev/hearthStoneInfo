package hsi.menu.informacion;

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
 * Clase encarga de crear la ventana emergente relacionada con la opción "Información" del menú.
 * @author Cristo
 *
 */
public class InformacionController implements Initializable {

	private Stage stage;
	
	//view
	@FXML
	private VBox view;
	
	public InformacionController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InformacionView.fxml"));
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
		stage.setTitle("Informacion");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}

}
