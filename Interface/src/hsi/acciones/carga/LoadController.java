package hsi.acciones.carga;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadController implements Initializable {

	private Stage stage;
	private String mensaje;

	// View
	@FXML
	private VBox view;

	@FXML
	private ImageView laoadImage;

	@FXML
	private Label mensajeLabel;

	public LoadController(String mensaje) throws IOException {
		this.mensaje = mensaje;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		mensajeLabel.setText(mensaje);
	}

	public void crearVentanaLoad() {
		Scene scene = new Scene(view);
		scene.getStylesheets().add(getClass().getResource("loadEstilo.css").toExternalForm());
		scene.setFill(Color.TRANSPARENT);
		stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initOwner(HsiApp.getPrimaryStage());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(scene);
		HsiApp.getPrimaryStage().close();
		stage.showAndWait();
	}

	public void cerrarVentanaLoad() {
		stage.close();
	}

}
