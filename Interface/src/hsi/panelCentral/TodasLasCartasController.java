package hsi.panelCentral;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.items.Carta;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

public class TodasLasCartasController implements Initializable {

	//Model
	private ListProperty<Carta> cartasBuscadas;
	private ObjectProperty<Carta> cartaSeleccionada;
	
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
		// TODO PRUEBAS borrar
		/*Carta c1 = new Carta();
		c1.setId("EX1_572");
		c1.setImg(new Image("http://wow.zamimg.com/images/hearthstone/cards/enus/original/EX1_572.png"));
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);		
		cartasPane.getChildren().clear();
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getChildren().clear();
		cartasPane.getCartas().add(c1);*/
		
		//bindeos
		cartasPane.cartasProperty().bind(cartasBuscadas);
		//TODO Ver como limpiar la vista. Da error.
		
		
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
