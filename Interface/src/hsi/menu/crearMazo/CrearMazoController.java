package hsi.menu.crearMazo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.controlErrores.ControllerControlesView;
import hsi.sql.FuncionesSQL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CrearMazoController implements Initializable {

	private Stage stage;

	// Lógica
	private StringProperty nombreMazo;

	// Modelo
	private StringProperty usuario;

	// View
	@FXML
	private GridPane view;

	@FXML
	private TextField nombreText;

	@FXML
	private Button crearBtn;

	@FXML
	private Button cancelarBtn;

	public CrearMazoController() throws IOException {
		nombreMazo = new SimpleStringProperty(this, "nombreMazo");
		usuario = new SimpleStringProperty(this, "usuario");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearMazoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// bindeo
		nombreMazo.bind(nombreText.textProperty());
		crearBtn.disableProperty().bind(nombreMazo.isEqualTo(""));

		// Eventos
		cancelarBtn.setOnAction(e -> onCancelarBtnAction(e));
		crearBtn.setOnAction(e -> onCrearBtnAction(e));

	}

	private void onCrearBtnAction(ActionEvent e) {
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				FuncionesSQL.insertarMazo(usuario.get(), nombreMazo.get());
				return null;
			}
		};
		
		task.setOnFailed(e1 -> falloInsertarMazoBDTarea(e1));
		task.setOnSucceeded(e1 -> stage.close());
		
		new Thread(task).start();
	}

	private void falloInsertarMazoBDTarea(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("Hubo un problema al insertar el mazo en la BD",
					"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onCancelarBtnAction(ActionEvent e) {
		stage.close();
	}

	public void crearVentana() {
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		// stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Crear mazo");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}

	public StringProperty getUsuario() {
		return usuario;
	}

}
