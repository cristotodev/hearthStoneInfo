package hsi.panelDerecho.Mazos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.controlErrores.ControllerControlesView;
import hsi.items.Carta;
import hsi.items.Mazo;
import hsi.sql.FuncionesSQL;
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
import javafx.scene.layout.VBox;

/**
 * Clase encargada del crear el panel derecho inferior de Mazos. El que aparece en la opción "Ver mazos" del menú.
 * @author Cristo
 *
 */
public class PanelDerechoMazosController implements Initializable {

	//model
	private StringProperty usuario;
	private ObjectProperty<Mazo> mazoSeleccionado;
	private ObjectProperty<Carta> cartaSeleccionada;
	private ListProperty<Mazo> mazos;
	private ListProperty<String> favoritas;
	
	//view
    @FXML
    private VBox view;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button insertarEnFavoritoButton;
    
	public PanelDerechoMazosController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada");
		mazos = new SimpleListProperty<>(this,"mazos", FXCollections.observableArrayList());
		favoritas = new SimpleListProperty<>(this, "favoritas", FXCollections.observableArrayList());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoMazosView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//bindeos
		eliminarButton.disableProperty().bind(cartaSeleccionada.isNull());
		insertarEnFavoritoButton.disableProperty().bind(cartaSeleccionada.isNull());
		
		//Eventos
		eliminarButton.setOnAction(e -> onEliminarButtonAction(e));
		insertarEnFavoritoButton.setOnAction(e -> onInsertarEnFavoritonButtonAction(e));
		
		mazoSeleccionado.addListener(new ChangeListener<Mazo>() {
			@Override
			public void changed(ObservableValue<? extends Mazo> observable, Mazo oldValue, Mazo newValue) {
				System.out.println("mazo cambiado");
			}
		});
		
		cartaSeleccionada.addListener(new ChangeListener<Carta>() {
			public void changed(javafx.beans.value.ObservableValue<? extends Carta> observable, Carta oldValue, Carta newValue) {
				System.out.println("carta seleccionada cambiada");	
			};
		});

	}

	/**
	 * Evento asociado al botón "Eliminar".<br>
	 * Elimina de la base de datos la carta seleccionada.
	 * @param e
	 */
	private void onEliminarButtonAction(ActionEvent e) {
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				FuncionesSQL.eliminarMazoCarta(mazoSeleccionado.get().getId(), cartaSeleccionada.get().getId());
				return null;
			}
		};
		
		task.setOnFailed(e1 -> falloElimnarCartaBDTarea(e1));
		new Thread(task).start();
	}

	private void falloElimnarCartaBDTarea(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("No se pudo conectarse con la base de datos.", "..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Evento asociado al botón "Insertar en favorito".<br>
	 * Inserta en la base de datos y en la lista "favoritas" la carta seleccionda.
	 * @param e
	 */
	private void onInsertarEnFavoritonButtonAction(ActionEvent e) {
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				FuncionesSQL.insertarFavorito(getUsuario(), cartaSeleccionada.get().getId());
				return null;
			}
		};
		
		task.setOnSucceeded(e1 -> correctoInsertarFavoritoBDTarea(e1));
		task.setOnFailed(e1 -> falloElimnarCartaBDTarea(e1));
		new Thread(task).start();
	}

	private void correctoInsertarFavoritoBDTarea(WorkerStateEvent e1) {
		favoritas.add(cartaSeleccionada.get().getId());
	}

	public VBox getView() {
		return view;
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

	public final ObjectProperty<Carta> cartaSeleccionadaProperty() {
		return this.cartaSeleccionada;
	}
	

	public final Carta getCartaSeleccionada() {
		return this.cartaSeleccionadaProperty().get();
	}
	

	public final void setCartaSeleccionada(final Carta cartaSeleccionada) {
		this.cartaSeleccionadaProperty().set(cartaSeleccionada);
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

	public final ListProperty<String> favoritasProperty() {
		return this.favoritas;
	}
	

	public final ObservableList<String> getFavoritas() {
		return this.favoritasProperty().get();
	}
	

	public final void setFavoritas(final ObservableList<String> favoritas) {
		this.favoritasProperty().set(favoritas);
	}

}
