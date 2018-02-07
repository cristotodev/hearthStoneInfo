package hsi.panelIzquierdo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.unirest.herramientas.ServicioAPI;
import hsi.unirest.mapeo.Info;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PanelIzquierdoController implements Initializable {
	
	// model
	private PanelIzquierdoModel panelIzquierdoModelo;
	private ObjectProperty<Info> info;

	// view
	@FXML
	private VBox view;

	@FXML
	private TextField nombreTextField;

	@FXML
	private VBox busquedaCamposVbox;

	@FXML
	private ComboBox<String> expansionCombo;

	@FXML
	private ComboBox<String> claseCombo;

	@FXML
	private ComboBox<String> faccionCombo;

	@FXML
	private ComboBox<String> rarezaCombo;

	@FXML
	private ComboBox<String> tipoCombo;

	@FXML
	private ComboBox<String> razaCombo;

	@FXML
	private TextField ataqueTextField;

	@FXML
	private TextField vidaTextfield;

	@FXML
	private TextField costeTextField;

	@FXML
	private Button buscarButton;

	public PanelIzquierdoController() throws IOException {		
		panelIzquierdoModelo = new PanelIzquierdoModel();
		info = new SimpleObjectProperty<>(this, "info");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelIzquierdoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Bindeos
		panelIzquierdoModelo.nombreProperty().bind(nombreTextField.textProperty());
		expansionCombo.itemsProperty().bind(panelIzquierdoModelo.expansionProperty());
		claseCombo.itemsProperty().bind(panelIzquierdoModelo.claseProperty());
		faccionCombo.itemsProperty().bind(panelIzquierdoModelo.faccionProperty());
		rarezaCombo.itemsProperty().bind(panelIzquierdoModelo.rarezaProperty());
		tipoCombo.itemsProperty().bind(panelIzquierdoModelo.tipoProperty());
		razaCombo.itemsProperty().bind(panelIzquierdoModelo.razaProperty());
		panelIzquierdoModelo.ataqueProperty().bind(ataqueTextField.textProperty());
		panelIzquierdoModelo.vidaProperty().bind(vidaTextfield.textProperty());
		panelIzquierdoModelo.costeProperty().bind(costeTextField.textProperty());
		
		//TODO Arreglar error 
		//Llenar modelo
		/*panelIzquierdoModelo.getExpansion().setAll(info.get().getSets());
		panelIzquierdoModelo.getClase().setAll(info.get().getClasses());
		panelIzquierdoModelo.getFaccion().setAll(info.get().getFactions());
		panelIzquierdoModelo.getRareza().setAll(info.get().getQualities());
		panelIzquierdoModelo.getTipo().setAll(info.get().getTypes());
		panelIzquierdoModelo.getRaza().setAll(info.get().getRaces());*/
	}

	public VBox getView() {
		return view;
	}

	public final ObjectProperty<Info> infoProperty() {
		return this.info;
	}
	

	public final Info getInfo() {
		return this.infoProperty().get();
	}
	

	public final void setInfo(final Info info) {
		this.infoProperty().set(info);
	}
}
