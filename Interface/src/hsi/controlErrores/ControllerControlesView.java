package hsi.controlErrores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Clase encargada de crear las ventanas cuando surge algún error, datos erroneos, etc.
 * 
 * @author Cristo
 *
 */
public class ControllerControlesView implements Initializable {

	private Stage stage;
	
	//model
	private StringProperty mensaje, urlImage;
	
	// view
	@FXML
	private GridPane view;

	@FXML
	private ImageView imgView;

	@FXML
	private Label mensajeLabel;

	@FXML
	private Button aceptarBtn;

	/**
	 * 
	 * @param mensaje Mensaje que va a mostrar en la ventana del error.
	 * @param urlImage Dirección de la imagen para sustituirla depende del tipo de error.
	 * @throws IOException
	 */
	public ControllerControlesView(String mensaje, String urlImage) throws IOException {
		this.mensaje = new SimpleStringProperty(this, "mensaje", mensaje);
		this.urlImage = new SimpleStringProperty(this, "urlImage", urlImage);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ControlesView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mensajeLabel.textProperty().bind(mensaje);
		//TODO Arreglar problema imagen
		//imgView.setImage(new Image(urlImage.get()));
		
		aceptarBtn.setOnAction(e -> onAceptarButtonAction(e));
	}

	/**
	 * Evento asociado al botón Aceptar. 
	 * Cierra la ventan del error.
	 * @param e
	 */
	private void onAceptarButtonAction(ActionEvent e) {
		stage.close();
	}
	
	/**
	 * Método para crear y mostrar la ventana del error.
	 */
	public void crearVentana() {
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		// stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Hubo un problema");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}

}
