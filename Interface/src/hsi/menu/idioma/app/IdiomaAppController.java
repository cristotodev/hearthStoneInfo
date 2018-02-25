package hsi.menu.idioma.app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
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

public class IdiomaAppController implements Initializable {

	private Stage stage;

	// Model
	private ListProperty<String> idiomas;
	private StringProperty idiomaSelecionado;

	// View
	@FXML
	private GridPane view;

	@FXML
	private ComboBox<String> idiomasComboBox;

	@FXML
	private Button aceptarBtn;

	@FXML
	private Button cancelarBtn;

	public IdiomaAppController() throws IOException {
		idiomas = new SimpleListProperty<>(this, "idiomas", FXCollections.observableArrayList());
		idiomaSelecionado = new SimpleStringProperty(this, "idiomaSeleccionados");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("IdiomaAppView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// BINDEOS
		idiomasComboBox.itemsProperty().bind(idiomas);
		idiomaSelecionado.bind(idiomasComboBox.getSelectionModel().selectedItemProperty());

		idiomasComboBox.getSelectionModel().select("esES");

		// Eventos
		cancelarBtn.setOnAction(e -> onCancelarBtnAction(e));
		aceptarBtn.setOnAction(e -> onAceptarBtnAction(e));

	}

	private void onAceptarBtnAction(ActionEvent e) {
		stage.close();
	}

	private void onCancelarBtnAction(ActionEvent e) {
		stage.close();
	}

	public String crearVentana() {
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Cambiar Idioma de las Cartas");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
		return idiomaSelecionado.get();
	}

	public ListProperty<String> getIdiomas() {
		return idiomas;
	}

}
