package hsi.panelCentral;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.items.Carta;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

public class TodasLasCartasController implements Initializable {

	//Model
	private ListProperty<Carta> cartasBuscadas;
	private ObjectProperty<Carta> cartaSeleccionada;
	//TODO Crear publico estatico boolean
	public static Boolean limpieza = true;
	
	//view
	@FXML
	private ScrollPane view;
	
	@FXML
	private CartasPane cartasPane;
	
	public TodasLasCartasController() throws IOException {
		cartasBuscadas = new SimpleListProperty<>(this, "cartasBuscadas", FXCollections.observableArrayList());
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionda", new Carta());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TodasCartasView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		//bindeos
		cartasPane.cartasProperty().bind(cartasBuscadas);
		cartaSeleccionada.bind(cartasPane.cartaSeleccionadaProperty());
		
		//cartasPane.startDragAndDrop(TransferMode.ANY);
		//TODO Ver como limpiar la vista. Da error.
		//TODO Preguntarle a Francisquito
		cartasBuscadas.addListener(new ChangeListener() {
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(limpieza) {
					cartasPane.getChildren().clear();
					limpieza = false;
				}
			}
		});
	}

	public ScrollPane getView() {
		return view;
	}

	public final ListProperty<Carta> cartasBuscadasProperty() {
		return this.cartasBuscadas;
	}
	

	public final ObservableList<Carta> getCartasBuscadas() {
		return this.cartasBuscadasProperty().get();
	}
	

	public final void setCartasBuscadas(final ObservableList<Carta> cartasBuscadas) {
		this.cartasBuscadasProperty().set(cartasBuscadas);
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
