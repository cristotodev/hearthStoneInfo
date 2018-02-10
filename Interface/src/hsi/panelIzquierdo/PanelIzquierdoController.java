package hsi.panelIzquierdo;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import hsi.unirest.herramientas.ServicioAPI;
import hsi.unirest.mapeo.Info;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PanelIzquierdoController implements Initializable {
	
	//Lógica de negocio
	private ServicioAPI servicioApi;
	
	// model
	private static final Integer POSDESHABILITAR = -1;
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
		servicioApi = new ServicioAPI();
		panelIzquierdoModelo = new PanelIzquierdoModel();
		this.info = new SimpleObjectProperty<>(this, "info", new Info());
		
		try {
			info.set(servicioApi.getInfo("esES"));
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
		
		busquedaCamposVbox.disableProperty().bind(panelIzquierdoModelo.nombreProperty().isNotEqualTo(""));
		bindeosDeshabilitarNombreText();
		bindeosDeshabilitarExpansionCombo();
		bindeosDeshabilitarClaseCombo();
		bindeosDeshabilitarFaccionCombo();
		bindeosDeshabilitarRarezaCombo();
		bindeosDeshabilitarTipoCombo();
		bindeosDeshabilitarRazaCombo();
		
		//Llenar modelo
		panelIzquierdoModelo.getExpansion().setAll(info.get().getSets());
		panelIzquierdoModelo.getClase().setAll(info.get().getClasses());
		panelIzquierdoModelo.getFaccion().setAll(info.get().getFactions());
		panelIzquierdoModelo.getRareza().setAll(info.get().getQualities());
		panelIzquierdoModelo.getTipo().setAll(info.get().getTypes());
		panelIzquierdoModelo.getRaza().setAll(info.get().getRaces());
		
		//Evento
		buscarButton.setOnAction(e -> onBuscarButtonAction(e));
	}

	private void onBuscarButtonAction(ActionEvent e) {
		//TODO Buscar
	}
	
	private void bindeosDeshabilitarRazaCombo() {
		razaCombo.disableProperty().bind(
				expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}
	
	private void bindeosDeshabilitarTipoCombo() {
		tipoCombo.disableProperty().bind(
				expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}
	
	private void bindeosDeshabilitarRarezaCombo() {
		rarezaCombo.disableProperty().bind(
				expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}
	
	private void bindeosDeshabilitarFaccionCombo() {
		faccionCombo.disableProperty().bind(
				expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}
	
	private void bindeosDeshabilitarClaseCombo() {
		claseCombo.disableProperty().bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}
	
	private void bindeosDeshabilitarExpansionCombo() {
		expansionCombo.disableProperty().bind(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}
	
	private void bindeosDeshabilitarNombreText() {
		nombreTextField.disableProperty().bind(
				expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR).
				or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)).
				or(panelIzquierdoModelo.ataqueProperty().isNotEqualTo("")).
				or(panelIzquierdoModelo.vidaProperty().isNotEqualTo("")).
				or(panelIzquierdoModelo.costeProperty().isNotEqualTo("")));
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
