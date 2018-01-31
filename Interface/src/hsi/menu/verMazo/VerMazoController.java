package hsi.menu.verMazo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.items.Mazo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class VerMazoController implements Initializable {

	private Stage stage;
	
	//Model
	private StringProperty usuario;
	private ListProperty<Mazo> mazos;
	private ObjectProperty<Mazo> mazoSeleccionado;
	
	// View
	@FXML
	private GridPane view;

	@FXML
	private ComboBox<Mazo> mazosComboBox;

	@FXML
	private Button verBtn;

	@FXML
	private Button cancelarBtn;

	public VerMazoController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		mazos = new SimpleListProperty<>(this, "mazos", FXCollections.observableArrayList());
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("VerMazoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//bindeos
		mazosComboBox.itemsProperty().bind(mazos);
		mazoSeleccionado.bind(mazosComboBox.getSelectionModel().selectedItemProperty());
		verBtn.disableProperty().bind(mazosComboBox.getSelectionModel().selectedIndexProperty().lessThan(0));

		//Eventos
		cancelarBtn.setOnAction(e -> onCancelarBtnAction(e));
		verBtn.setOnAction(e -> verBtnAction(e));
	}
	
	private void verBtnAction(ActionEvent e) {
		// TODO Consultar funcionamiento si retorna el mazo o que
		stage.close();
	}

	private void onCancelarBtnAction(ActionEvent e) {
		stage.close();
	}

	public void crearVentana() {
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		//stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Ver mazo");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}
	
	public StringProperty getUsuario() {
		return usuario;
	}
	
	public ListProperty<Mazo> getMazos() {
		return mazos;
	}

}
