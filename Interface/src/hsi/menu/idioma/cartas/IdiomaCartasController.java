package hsi.menu.idioma.cartas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.controlErrores.ControllerControlesView;
import hsi.unirest.herramientas.ServicioAPI;
import hsi.unirest.mapeo.Info;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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

/**
 * Clase encargada de crear la ventana emergente para seleccionar el idioma de las cartas.
 * @author Cristo
 *
 */
public class IdiomaCartasController implements Initializable {

	private Stage stage;
	
	//Lógica de negocio
	private ServicioAPI servicioApi;
	
	//Model
	private ObjectProperty<Info> info;
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

	public IdiomaCartasController() throws IOException {
		servicioApi = new ServicioAPI();
		this.info = new SimpleObjectProperty<>(this, "info", new Info());
		idiomas = new SimpleListProperty<>(this, "idiomas", FXCollections.observableArrayList());
		idiomaSelecionado = new SimpleStringProperty(this, "idiomaSeleccionados");
		
		tareaInfoAPI();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("IdiomaCartasView.fxml"));
		loader.setController(this);
		loader.load();
	}

	private void tareaInfoAPI() {
		Task<Info> task = new Task<Info>() {
			@Override
			protected Info call() throws Exception {
				return servicioApi.getInfo("esES");
			}
		};
		
		task.setOnFailed(e -> falloGetInfoAPITarea(e));
		task.setOnSucceeded(e -> correctoGetInfoAPITarea(e));
		new Thread(task).start();
	}
	
	private void correctoGetInfoAPITarea(WorkerStateEvent e) {
		info.set((Info)e.getSource().getValue());
		idiomas.addAll(info.get().getLocales());
	}

	private void falloGetInfoAPITarea(WorkerStateEvent e) {
		try {
			new ControllerControlesView("Hubo un problema al cargar los datos de la API",
					"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//BINDEOS
		idiomasComboBox.itemsProperty().bind(idiomas);
		idiomaSelecionado.bind(idiomasComboBox.getSelectionModel().selectedItemProperty());
		
		idiomasComboBox.getSelectionModel().select("esES");
		
		//Eventos
		cancelarBtn.setOnAction(e -> onCancelarBtnAction(e));
		aceptarBtn.setOnAction(e -> onAceptarBtnAction(e));
		
		//llenarCombo
		idiomas.addAll(info.get().getLocales());
	}
	
	/**
	 * Evento asociado al botón "Aceptar".<br>
	 * Cierra el programa.
	 * @param e
	 */
	private void onAceptarBtnAction(ActionEvent e) {
		stage.close();
	}

	/**
	 * Evento asociado al botón "Cancelar".<br>
	 * Cierra el programa.
	 * @param e
	 */
	private void onCancelarBtnAction(ActionEvent e) {
		stage.close();
	}

	/**
	 * Método encargado de crear la ventana emergente de los idiomas de las cartas.
	 * @return Devuelve el idioma que se seleccionó.
	 */
	public String crearVentana() {
		Scene scene = new Scene(view, 300, 150);
		scene.getStylesheets().add(getClass().getResource("/hsi/menu/idiomasEstilos.css").toExternalForm());
		
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Cambiar Idioma de las Cartas");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.showAndWait();
		return idiomaSelecionado.get();
	}
	
	public ListProperty<String> getIdiomas() {
		return idiomas;
	}

}
