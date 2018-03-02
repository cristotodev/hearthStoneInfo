package hsi.panelDerecho.Busqueda;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
 * Clase encargada del crear el panel derecho inferior de búsqueda. El que aparece en la opción "Inicio" del menú.
 * @author Cristo
 *
 */
public class PanelDerechoBusquedaController implements Initializable {
	
	//Model
	private StringProperty usuario;
	private ListProperty<String> favoritos;
	private ObjectProperty<Mazo> mazoSeleccionado;
	private ObjectProperty<Carta> cartaSeleccionada;
	
	//View
    @FXML
    private VBox view;

    @FXML
    private Button insertarEnFavoritoButton;
	
	public PanelDerechoBusquedaController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		favoritos = new SimpleListProperty<>(this, "favoritos", FXCollections.observableArrayList());
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionados");
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoBusquedaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//bindeos
		insertarEnFavoritoButton.disableProperty().bind(cartaSeleccionada.isNull());
		
		//Eventos
		insertarEnFavoritoButton.setOnAction(e -> onInsertarEnFavoritoButtonAction(e));
	}
	
	/**
	 * Evento asociado al botón "Insertar en favorito".<br>
	 * Inserta en la base de datos la carta seleccionada en ese usuario.
	 * @param e
	 */
	private void onInsertarEnFavoritoButtonAction(ActionEvent e) {
		
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				FuncionesSQL.insertarFavorito(usuario.get(), cartaSeleccionada.get().getId());
				return null;
			}
		};
		
		task.setOnSucceeded(e1 -> correctoInsertarFavoritoBDTarea(e1));
		task.setOnFailed(e1 -> falloInsertarFavoritoBDTarea(e1));
		new Thread(task).start();
	}
	
	private void falloInsertarFavoritoBDTarea(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("No se pudo conectarse con la base de datos.", "..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void correctoInsertarFavoritoBDTarea(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("Agregación a favorito exitosa", "..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
		llenarFavoritos();
	}

	private void llenarFavoritos() {
		
		Task<List<String>> task = new Task<List<String>>() {
			@Override
			protected List<String> call() throws Exception {
				return FuncionesSQL.consultaFavoritos(usuario.get());
			}
		};
		
		task.setOnSucceeded(e -> correctoCogerFavoritosBDTarea(e));
		task.setOnFailed(e -> falloInsertarFavoritoBDTarea(e));
		new Thread(task).start();
	}

	@SuppressWarnings("unchecked")
	private void correctoCogerFavoritosBDTarea(WorkerStateEvent e) {
		favoritos.clear();
		favoritos.addAll((List<String>) e.getSource().getValue());
	}

	public VBox getView() {
		return view;
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

	public final ListProperty<String> favoritosProperty() {
		return this.favoritos;
	}
	

	public final ObservableList<String> getFavoritos() {
		return this.favoritosProperty().get();
	}
	

	public final void setFavoritos(final ObservableList<String> favoritos) {
		this.favoritosProperty().set(favoritos);
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

}
