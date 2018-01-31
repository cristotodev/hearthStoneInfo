package hsi.menu.eliminarMazo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.items.Mazo;
import hsi.sql.FuncionesSQL;
import javafx.beans.property.BooleanProperty;
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

public class EliminarMazoController implements Initializable {

	private Stage stage;
	
	//model
	private ListProperty<Mazo> mazos;
	private ObjectProperty<Mazo> mazoSeleccionado;
	private StringProperty usuario;
	
	// View
	@FXML
	private GridPane view;

	@FXML
	private ComboBox<Mazo> mazosComboBox;

	@FXML
	private Button eliminarBtn;

	@FXML
	private Button cancelarBtn;

	public EliminarMazoController() throws IOException {
		mazos = new SimpleListProperty<>(this, "mazos", FXCollections.observableArrayList());
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		usuario = new SimpleStringProperty(this, "usuario");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EliminarMazoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//bindeos
		mazosComboBox.itemsProperty().bind(mazos);
		mazoSeleccionado.bind(mazosComboBox.getSelectionModel().selectedItemProperty());
		eliminarBtn.disableProperty().bind(mazosComboBox.getSelectionModel().selectedIndexProperty().lessThan(0));

		//Eventos
		cancelarBtn.setOnAction(e -> onCancelarBtnAction(e));
		eliminarBtn.setOnAction(e -> onEliminarBtnAction(e));
	}
	
	private void onEliminarBtnAction(ActionEvent e) {
		try {
			FuncionesSQL.eliminarMazo(usuario.get(), mazoSeleccionado.get().getNombre());
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
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
		stage.setTitle("Eliminar mazo");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}
	
	public ListProperty<Mazo> getMazos() {
		return mazos;
	}
	
	public StringProperty getUsuario() {
		return usuario;
	}

}
