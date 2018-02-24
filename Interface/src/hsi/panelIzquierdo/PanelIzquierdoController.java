package hsi.panelIzquierdo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import hsi.controlErrores.ControllerControlesView;
import hsi.items.Carta;
import hsi.panelCentral.TodasLasCartasController;
import hsi.unirest.herramientas.ServicioAPI;
import hsi.unirest.mapeo.Info;
import hsi.unirest.mapeo.ListaDeCartas;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Clase encargada del panel Izquierdo de búsqueda.<br>
 * Encargado de realizar búsquedas por clases, facción, nombre, etc.
 * @author Cristo
 *
 */
public class PanelIzquierdoController implements Initializable {

	private Task<ListaDeCartas> task;
	
	// Lógica de negocio
	private ServicioAPI servicioApi;

	// model
	private static final Integer POSDESHABILITAR = -1;
	private PanelIzquierdoModel panelIzquierdoModelo;
	private ObjectProperty<Info> infoSpanish;
	private ObjectProperty<Info> infoEnglish;
	private StringProperty idiomaCarta;
	private ListProperty<Carta> cartasBusqueda;
	private ListaDeCartas listaCartasServicios;
	private StringProperty expansionSeleccionada;
	private StringProperty claseSeleccionada;
	private StringProperty faccionSeleccionada;
	private StringProperty rarezaSeleccionada;
	private StringProperty tipoSeleccionada;
	private StringProperty razaSeleccionada;

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
		this.infoSpanish = new SimpleObjectProperty<>(this, "info", new Info());
		infoEnglish = new SimpleObjectProperty<>(this, "infoEnglish", new Info());
		idiomaCarta = new SimpleStringProperty(this, "idiomaCarta");
		cartasBusqueda = new SimpleListProperty<>(this, "cartasBusqueda", FXCollections.observableArrayList());

		expansionSeleccionada = new SimpleStringProperty(this, "expansionSeleccionada");
		claseSeleccionada = new SimpleStringProperty(this, "claseSeleccionada");
		faccionSeleccionada = new SimpleStringProperty(this, "faccionSeleccionada");
		rarezaSeleccionada = new SimpleStringProperty(this, "rarezaSeleccionada");
		tipoSeleccionada = new SimpleStringProperty(this, "tipoSeleccionada");
		razaSeleccionada = new SimpleStringProperty(this, "razaSeleccionada");
		
		tareaInfoSpanish();
		tareaInfoEnglish();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelIzquierdoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	private void tareaInfoEnglish() {
		Task<Info> task = new Task<Info>() {
			@Override
			protected Info call() throws Exception {
				return servicioApi.getInfo("enUS");
			}
		};
		
		task.setOnFailed(e -> falloCargarInfoAPITarea(e));
		task.setOnSucceeded(e -> infoEnglish.set(((Info) e.getSource().getValue())));
		new Thread(task).start();
	}

	private void tareaInfoSpanish() {
		Task<Info> task = new Task<Info>() {
			@Override
			protected Info call() throws Exception {
				return servicioApi.getInfo("esES");
			}
		};
		
		task.setOnFailed(e -> falloCargarInfoAPITarea(e));
		task.setOnSucceeded(e -> correctoCargarInfoAPITarea(e));
		new Thread(task).start();
	}
	
	private void correctoCargarInfoAPITarea(WorkerStateEvent e) {
		infoSpanish.set((Info) e.getSource().getValue());

		// Llenar modelo
		panelIzquierdoModelo.getExpansion().setAll(infoSpanish.get().getSets());
		panelIzquierdoModelo.getClase().setAll(infoSpanish.get().getClasses());
		panelIzquierdoModelo.getFaccion().setAll(infoSpanish.get().getFactions());
		panelIzquierdoModelo.getRareza().setAll(infoSpanish.get().getQualities());
		panelIzquierdoModelo.getTipo().setAll(infoSpanish.get().getTypes());
		panelIzquierdoModelo.getRaza().setAll(infoSpanish.get().getRaces());
	}

	private void falloCargarInfoAPITarea(WorkerStateEvent e) {
		try {
			new ControllerControlesView("No se pudieron cargar los datos de la API.", "..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Bindeos
		panelIzquierdoModelo.nombreProperty().bind(nombreTextField.textProperty());
		expansionCombo.itemsProperty().bind(panelIzquierdoModelo.expansionProperty());
		expansionSeleccionada.bind(expansionCombo.getSelectionModel().selectedItemProperty());
		claseCombo.itemsProperty().bind(panelIzquierdoModelo.claseProperty());
		claseSeleccionada.bind(claseCombo.getSelectionModel().selectedItemProperty());
		faccionCombo.itemsProperty().bind(panelIzquierdoModelo.faccionProperty());
		faccionSeleccionada.bind(faccionCombo.getSelectionModel().selectedItemProperty());
		rarezaCombo.itemsProperty().bind(panelIzquierdoModelo.rarezaProperty());
		rarezaSeleccionada.bind(rarezaCombo.getSelectionModel().selectedItemProperty());
		tipoCombo.itemsProperty().bind(panelIzquierdoModelo.tipoProperty());
		tipoSeleccionada.bind(tipoCombo.getSelectionModel().selectedItemProperty());
		razaCombo.itemsProperty().bind(panelIzquierdoModelo.razaProperty());
		razaSeleccionada.bind(razaCombo.getSelectionModel().selectedItemProperty());
		panelIzquierdoModelo.ataqueProperty().bind(ataqueTextField.textProperty());
		panelIzquierdoModelo.vidaProperty().bind(vidaTextfield.textProperty());
		panelIzquierdoModelo.costeProperty().bind(costeTextField.textProperty());

		buscarButton.disableProperty().bind(busquedaCamposVbox.disabledProperty().not().
				and(nombreTextField.disabledProperty().not()));
		
		busquedaCamposVbox.disableProperty().bind(panelIzquierdoModelo.nombreProperty().isNotEqualTo(""));
		bindeosDeshabilitarNombreText();
		bindeosDeshabilitarExpansionCombo();
		bindeosDeshabilitarClaseCombo();
		bindeosDeshabilitarFaccionCombo();
		bindeosDeshabilitarRarezaCombo();
		bindeosDeshabilitarTipoCombo();
		bindeosDeshabilitarRazaCombo();


		// Evento
		buscarButton.setOnAction(e -> onBuscarButtonAction(e));
	}

	/**
	 * Evento asociado al botón "Buscar".<br>
	 * Realiza la consulta a la API dependiendo de los atributos que el usuario ha especificado.
	 * @param e
	 */
	private void onBuscarButtonAction(ActionEvent e) {
		TodasLasCartasController.limpieza = true;
		System.out.println("buscando...");
		if (busquedaCamposVbox.isDisable()) {
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasPorPalabra(panelIzquierdoModelo.getNombre(),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			new Thread(task).start();

		} else {
			try {
				busquedasPorCampos();
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void correctoObtenerCartasBDTarea(WorkerStateEvent e1) {
		System.out.println("Dentro");
		listaCartasServicios = (ListaDeCartas) e1.getSource().getValue();
		pasarCartas(listaCartasServicios.getCartas(), cartasBusqueda.get());
	}

	private void falloObtenerCartasBDTarea(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("Hubo un problema al conectarse con la API para realizar la búsqueda.", "..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void busquedasPorCampos() throws NumberFormatException, UnirestException {
		if (!expansionCombo.isDisable()) {
			task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					Integer pos = expansionCombo.getSelectionModel().getSelectedIndex();
					System.out.println(infoEnglish.get().getSets().get(pos));
					return servicioApi.getCartasExpansion(infoEnglish.get().getSets().get(pos),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
		}else if(!claseCombo.isDisable()) {
			task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					Integer pos = claseCombo.getSelectionModel().getSelectedIndex();
					return servicioApi.getCartasClases(infoEnglish.get().getClasses().get(pos),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
		}else if(!faccionCombo.isDisable()) {
			task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					Integer pos = faccionCombo.getSelectionModel().getSelectedIndex();
					return servicioApi.getCartasFacciones(infoEnglish.get().getFactions().get(pos),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
		}else if(!rarezaCombo.isDisable()) {
			task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					Integer pos = rarezaCombo.getSelectionModel().getSelectedIndex();
					return servicioApi.getCartasRareza(infoEnglish.get().getQualities().get(pos),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
		}else if(!tipoCombo.isDisable()) {
			task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					Integer pos = tipoCombo.getSelectionModel().getSelectedIndex();
					return servicioApi.getCartasTipo(infoEnglish.get().getTypes().get(pos),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
		}else {
			task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					Integer pos = razaCombo.getSelectionModel().getSelectedIndex();
					return servicioApi.getCartasRaza(infoEnglish.get().getRaces().get(pos),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
		}
		task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
		task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
		new Thread(task).start();
	}

	/**
	 * Pasa los atributos de un objeto Carta del Servicio, a un objeto Carta de la interfaz.
	 * @param cartasServicio Origen.
	 * @param cartasPrograma Destino.
	 */
	private void pasarCartas(List<hsi.unirest.mapeo.Carta> cartasServicio, List<Carta> cartasPrograma) {
		cartasBusqueda.clear();
		for (hsi.unirest.mapeo.Carta cartaServicio : cartasServicio) {
			cartasPrograma.add(Carta.fromCartaServicio(cartaServicio));
		}
	}

	private void bindeosDeshabilitarRazaCombo() {
		razaCombo.disableProperty()
				.bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}

	private void bindeosDeshabilitarTipoCombo() {
		tipoCombo.disableProperty()
				.bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}

	private void bindeosDeshabilitarRarezaCombo() {
		rarezaCombo.disableProperty()
				.bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}

	private void bindeosDeshabilitarFaccionCombo() {
		faccionCombo.disableProperty()
				.bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}

	private void bindeosDeshabilitarClaseCombo() {
		claseCombo.disableProperty()
				.bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}

	private void bindeosDeshabilitarExpansionCombo() {
		expansionCombo.disableProperty()
				.bind(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)));
	}

	private void bindeosDeshabilitarNombreText() {
		nombreTextField.disableProperty()
				.bind(expansionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR)
						.or(claseCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(faccionCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(rarezaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(tipoCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(razaCombo.getSelectionModel().selectedIndexProperty().greaterThan(POSDESHABILITAR))
						.or(panelIzquierdoModelo.ataqueProperty().isNotEqualTo(""))
						.or(panelIzquierdoModelo.vidaProperty().isNotEqualTo(""))
						.or(panelIzquierdoModelo.costeProperty().isNotEqualTo("")));
	}

	public VBox getView() {
		return view;
	}

	public final ObjectProperty<Info> infoProperty() {
		return this.infoSpanish;
	}

	public final Info getInfo() {
		return this.infoProperty().get();
	}

	public final void setInfo(final Info info) {
		this.infoProperty().set(info);
	}

	public final StringProperty idiomaCartaProperty() {
		return this.idiomaCarta;
	}

	public final String getIdiomaCarta() {
		return this.idiomaCartaProperty().get();
	}

	public final void setIdiomaCarta(final String idiomaCarta) {
		this.idiomaCartaProperty().set(idiomaCarta);
	}

	public final ListProperty<Carta> cartasBusquedaProperty() {
		return this.cartasBusqueda;
	}
	

	public final ObservableList<Carta> getCartasBusqueda() {
		return this.cartasBusquedaProperty().get();
	}
	

	public final void setCartasBusqueda(final ObservableList<Carta> cartasBusqueda) {
		this.cartasBusquedaProperty().set(cartasBusqueda);
	}
}
