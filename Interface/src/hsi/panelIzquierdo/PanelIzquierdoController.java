package hsi.panelIzquierdo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import hsi.controlErrores.ControllerControlesView;
import hsi.items.Carta;
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
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class PanelIzquierdoController implements Initializable {

	// Lógica de negocio
	private ServicioAPI servicioApi;

	// model
	private static final Integer POSDESHABILITAR = -1;
	private PanelIzquierdoModel panelIzquierdoModelo;
	private ObjectProperty<Info> info;
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
		this.info = new SimpleObjectProperty<>(this, "info", new Info());
		idiomaCarta = new SimpleStringProperty(this, "idiomaCarta");
		cartasBusqueda = new SimpleListProperty<>(this, "cartasBusqueda", FXCollections.observableArrayList());

		expansionSeleccionada = new SimpleStringProperty(this, "expansionSeleccionada");
		claseSeleccionada = new SimpleStringProperty(this, "claseSeleccionada");
		faccionSeleccionada = new SimpleStringProperty(this, "faccionSeleccionada");
		rarezaSeleccionada = new SimpleStringProperty(this, "rarezaSeleccionada");
		tipoSeleccionada = new SimpleStringProperty(this, "tipoSeleccionada");
		razaSeleccionada = new SimpleStringProperty(this, "razaSeleccionada");
		
		tareaInfo();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelIzquierdoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	private void tareaInfo() {
		Task<Info> task = new Task<Info>() {
			@Override
			protected Info call() throws Exception {
				return servicioApi.getInfo("esES");
			}
		};
		
		task.setOnFailed(e -> falloCargarInfoAPITarea(e));
		task.setOnSucceeded(e -> correctoCargarInfoAPITarea(e));
		task.run();
	}
	
	private void correctoCargarInfoAPITarea(WorkerStateEvent e) {
		info.set((Info) e.getSource().getValue());

		// Llenar modelo
		panelIzquierdoModelo.getExpansion().setAll(info.get().getSets());
		panelIzquierdoModelo.getClase().setAll(info.get().getClasses());
		panelIzquierdoModelo.getFaccion().setAll(info.get().getFactions());
		panelIzquierdoModelo.getRareza().setAll(info.get().getQualities());
		panelIzquierdoModelo.getTipo().setAll(info.get().getTypes());
		panelIzquierdoModelo.getRaza().setAll(info.get().getRaces());
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
		//TODO Negar para que lo haga bien
		buscarButton.disableProperty().bind(busquedaCamposVbox.disabledProperty().
				and(nombreTextField.disabledProperty()));
		
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

	private void onBuscarButtonAction(ActionEvent e) {
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
			task.run();

		} else {
			try {
				busquedasPorCampos();
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (UnirestException e1) {
				e1.printStackTrace();
			}
		}

		// MOstrar listado
		for (Carta carta : cartasBusqueda) {
			System.out.println(carta);
		}
	}

	private void correctoObtenerCartasBDTarea(WorkerStateEvent e1) {
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
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasExpansion(expansionSeleccionada.get(),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			task.run();
		}else if(!claseCombo.isDisable()) {
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasClases(claseSeleccionada.get(),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			task.run();
		}else if(!faccionCombo.isDisable()) {
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasFacciones(faccionSeleccionada.get(),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			task.run();
		}else if(!rarezaCombo.isDisable()) {
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasRareza(rarezaSeleccionada.get(),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			task.run();
		}else if(!tipoCombo.isDisable()) {
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasTipo(tipoSeleccionada.get(),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			task.run();
		}else {
			Task<ListaDeCartas> task = new Task<ListaDeCartas>() {
				@Override
				protected ListaDeCartas call() throws Exception {
					return servicioApi.getCartasRaza(razaSeleccionada.get(),
							Integer.parseInt(panelIzquierdoModelo.getAtaque()),
							Integer.parseInt(panelIzquierdoModelo.getCoste()), Integer.parseInt(panelIzquierdoModelo.getVida()),
							idiomaCarta.get());
				}
			};
			
			task.setOnFailed(e1 -> falloObtenerCartasBDTarea(e1));
			task.setOnSucceeded(e1 -> correctoObtenerCartasBDTarea(e1));
			task.run();
		}
	}

	private void pasarCartas(List<hsi.unirest.mapeo.Carta> cartasServicio, List<Carta> cartasPrograma) {
		for (hsi.unirest.mapeo.Carta cartaServicio : cartasServicio) {
			Carta carta = new Carta();
			carta.setId(cartaServicio.getCardId());
			carta.setNombre(cartaServicio.getName());
			carta.setExpansion(cartaServicio.getCardSet());
			carta.setTipo(cartaServicio.getType());
			carta.setFaccion(cartaServicio.getFaction());
			carta.setRareza(cartaServicio.getRarity());

			if (cartaServicio.getCost() != null)
				carta.setCoste(cartaServicio.getCost());

			if (cartaServicio.getAttack() != null)
				carta.setAtaque(cartaServicio.getAttack());

			if (cartaServicio.getHealth() != null)
				carta.setSalud(cartaServicio.getHealth());

			carta.setAccion(cartaServicio.getFlavor());
			carta.setDescripcion(cartaServicio.getText());
			carta.setRaza(cartaServicio.getRace());
			carta.setArtista(cartaServicio.getArtist());
			carta.setClase(cartaServicio.getPlayerClass());
			// TODO Se está cogiendo de la URL. Hay que coger de caché
			if (cartaServicio.getImg() != null)
				carta.setImg(new Image(cartaServicio.getImg()));
			else
				System.out.println("No imagen, coger una estandar");

			if (cartaServicio.getImgGold() != null)
				carta.setImgDorada(new Image(cartaServicio.getImgGold()));
			else
				System.out.println("No imagen dorada, coger una estandar");

			carta.setMecanismo(cartaServicio.getMechanics());

			cartasPrograma.add(carta);
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
		return this.info;
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
}
