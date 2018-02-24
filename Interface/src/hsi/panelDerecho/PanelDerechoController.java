package hsi.panelDerecho;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import hsi.controlErrores.ControllerControlesView;
import hsi.items.Carta;
import hsi.items.Mazo;
import hsi.sql.FuncionesSQL;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

/**
 * Clase encargada de crear la parte superior del panel derecho.<br>
 * El encargado de mostrar los datos de una cartam, el desplegable de los mazos y el botón "Copiar a Mazo".
 * @author Cristo
 *
 */
public class PanelDerechoController implements Initializable {
	
	private final static Integer CANTCARTAS = 30;
	
	//model
	private ListProperty<Mazo> mazos;
	private StringProperty usuario;
	private ObjectProperty<Carta> cartaSeleccionada;
	private ObjectProperty<Mazo> mazoSeleccionado;
	
	//view
	@FXML
    private VBox view;

    @FXML
    private ImageView imgGif;

    @FXML
    private ImageView imgNormal;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label expansionLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Label faccionLabel;

    @FXML
    private Label rarezaLabel;

    @FXML
    private Label costeLabel;

    @FXML
    private Label ataqueLabel;

    @FXML
    private Label vidaLabel;

    @FXML
    private Label textoLabel;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label razaLabel;

    @FXML
    private Label artistaLabel;

    @FXML
    private Label eliteLabel;

    @FXML
    private Label claseLabel;

    @FXML
    private Label mecanismoLabel;

    @FXML
    private ComboBox<Mazo> mazosComboBox;
    
    @FXML
    private Button copiarButton;
	
	public PanelDerechoController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		mazos = new SimpleListProperty<>(this, "mazos", FXCollections.observableArrayList());
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada", new Carta());
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// bindeo
		mazosComboBox.itemsProperty().bind(mazos);	
		mazosComboBox.valueProperty().addListener(new ChangeListener<Mazo>() {
			@Override
			public void changed(ObservableValue<? extends Mazo> observable, Mazo oldValue, Mazo newValue) {
				mazoSeleccionado.set(newValue);
			}
		});
		
		copiarButton.disableProperty().bind(mazoSeleccionado.isNull().or(cartaSeleccionada.isNull()));
		
		//Eventos
		copiarButton.setOnAction(e -> onCopiarButtonAction(e));
		
		//Listener
		cartaSeleccionada.addListener(new ChangeListener<Carta>() {
			@Override
			public void changed(ObservableValue<? extends Carta> observable, Carta oldValue, Carta newValue) {
				if(newValue != null) {
					imgGif.imageProperty().bind(newValue.imgDoradaProperty());
					imgNormal.imageProperty().bind(newValue.imgProperty());
					nombreLabel.textProperty().bind(newValue.nombreProperty());
					expansionLabel.textProperty().bind(newValue.expansionProperty());
					tipoLabel.textProperty().bind(newValue.tipoProperty());
					faccionLabel.textProperty().bind(newValue.faccionProperty());
					rarezaLabel.textProperty().bind(newValue.rarezaProperty());
					Bindings.bindBidirectional(costeLabel.textProperty(), newValue.costeProperty(), new NumberStringConverter());
					Bindings.bindBidirectional(ataqueLabel.textProperty(), newValue.ataqueProperty(), new NumberStringConverter());
					Bindings.bindBidirectional(vidaLabel.textProperty(), newValue.saludProperty(), new NumberStringConverter());
					textoLabel.textProperty().bind(newValue.accionProperty());
					descripcionLabel.textProperty().bind(newValue.descripcionProperty());
					rarezaLabel.textProperty().bind(newValue.rarezaProperty());
					artistaLabel.textProperty().bind(newValue.artistaProperty());
					eliteLabel.textProperty().bind(newValue.eliteProperty().asString());
					claseLabel.textProperty().bind(newValue.claseProperty());
					mecanismoLabel.textProperty().bind(newValue.mecanismoProperty());
				}
			}
		});
		
		mazoSeleccionado.addListener(new ChangeListener<Mazo>() {
			@Override
			public void changed(ObservableValue<? extends Mazo> observable, Mazo oldValue, Mazo newValue) {
				mazosComboBox.getSelectionModel().select(getMazoSeleccionado());
			}
		});
	}
	
	/**
	 * Evento asociado al botón "Copiar a Mazo".<br>
	 * Comprueba la cantidad de cartas que tiene un mazo y si no es superior a 30 inserta esa carta en el mazo.
	 * @param e
	 */
	private void onCopiarButtonAction(ActionEvent e) {
		
		Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				return FuncionesSQL.consultaNumeroMazoCarta(mazoSeleccionado.get().getId());
			}
		};
		
		task.setOnFailed(e1 -> falloConsultarMazosBDTarea(e1));
		task.setOnSucceeded(e1 -> correctoConsultarMazosBDTarea(e1));
		new Thread(task).start();
	}
	
	private void correctoConsultarMazosBDTarea(WorkerStateEvent e1) {
		if((Integer)e1.getSource().getValue() <= CANTCARTAS) {
			Task<Void> task = new Task<Void>() {
				protected Void call() throws Exception {
					FuncionesSQL.insertarMazoCarta(mazoSeleccionado.get().getId(), cartaSeleccionada.get().getId());
					return null;
				};
			};
			
			task.setOnFailed(e2 -> falloConsultarMazosBDTarea(e2));
			task.setOnSucceeded(e2 -> {
				try {
					llenarMazos();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			new Thread(task).start();
		}
	}

	private void falloConsultarMazosBDTarea(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("No se pudo conectarse con la base de datos.", "..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void llenarMazos() throws ClassNotFoundException, SQLException {
		
		Task<List<hsi.sql.Mazo>> task = new Task<List<hsi.sql.Mazo>>() {
			@Override
			protected List<hsi.sql.Mazo> call() throws Exception {
				return FuncionesSQL.consultaMazos(usuario.get());
			}
		};
		
		task.setOnSucceeded(e -> correctoLlenarMazoBDTarea(e));
		task.setOnFailed(e ->  falloConsultarMazosBDTarea(e));
		new Thread(task).start();
	}

	private void correctoLlenarMazoBDTarea(WorkerStateEvent e) {
		@SuppressWarnings("unchecked")
		List<hsi.sql.Mazo> mazos = (List<hsi.sql.Mazo>) e.getSource().getValue();
		mazos.clear();
		for (hsi.sql.Mazo mazo : mazos) {
			Mazo mazoNuevo = new Mazo();
			mazoNuevo.setId(mazo.getID());
			mazoNuevo.setNombre(mazo.getNombre());
			
			this.mazos.add(mazoNuevo);
		}
	}

	public VBox getView() {
		return view;
	}
	
	public ComboBox<Mazo> getMazosComboBox() {
		return mazosComboBox;
	}

	public final ObjectProperty<Carta> cartaSeleccionadaProperty() {
		return this.cartaSeleccionada;
	}
	

	public final Carta getCartaSeleccionada() {
		return this.cartaSeleccionadaProperty().get();
	}
	

	public final void setCartaSeleccionada(final Carta cartaSeleccionada) {
		this.cartaSeleccionadaProperty().set(cartaSeleccionada);
	}
	

	public final ObjectProperty<Mazo> mazoSeleccionadoProperty() {
		return this.mazoSeleccionado;
	}
	

	public final Mazo getMazoSeleccionado() {
		return this.mazoSeleccionadoProperty().get();
	}
	

	public final void setMazoSeleccionado(final Mazo mazoSeleccionado) {
		this.mazoSeleccionadoProperty().set(mazoSeleccionado);
	}

	public final ListProperty<Mazo> mazosProperty() {
		return this.mazos;
	}
	

	public final ObservableList<Mazo> getMazos() {
		return this.mazosProperty().get();
	}
	

	public final void setMazos(final ObservableList<Mazo> mazos) {
		this.mazosProperty().set(mazos);
	}

	public final StringProperty usuarioProperty() {
		return this.usuario;
	}
	

	public final String getUsuario() {
		return this.usuarioProperty().get();
	}
	

	public final void setUsuario(final String usuario) {
		this.usuarioProperty().set(usuario);
	}

}
