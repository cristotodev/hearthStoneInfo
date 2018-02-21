package hsi.panelCentral;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import hsi.items.Carta;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.layout.TilePane;
/**
 * 
 * @author Carlos Marrero Ramos - Xibhu
 *
 */
public class CartasPane extends TilePane implements Initializable {
	
	private ListProperty<Carta> cartas = new SimpleListProperty<>(this, "cartas", FXCollections.observableArrayList());
	private ObjectProperty<Carta> cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada", new Carta());
	
	//TODO Como enganchar el cartaSeleccionada de CartaView con cartaPane para seguir pasandolo
	
	public CartasPane() {
		initialize(null, null);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		cartas.addListener(new ListChangeListener<Carta>() {

			@Override
			public void onChanged(Change<? extends Carta> c) {
				while (c.next()) {
					
					if (c.wasAdded()) {
						
						@SuppressWarnings("unchecked")
						List<Carta> insertados = (List<Carta>) c.getAddedSubList();
						
						for (int i = 0; i < insertados.size(); i++) {
							CartaView cv = new CartaView();
							cv.setCarta(insertados.get(i));
							CartasPane.this.getChildren().add(cv);
							
						}
					}
					
					if (c.wasRemoved()) {
						
						@SuppressWarnings("unchecked")
						List<Carta> eliminados = (List<Carta>) c.getAddedSubList();
						
						for (int i = 0; i < eliminados.size(); i++) {
							
							CartaView cv = new CartaView();
							cv.setCarta(eliminados.get(i));
							CartasPane.this.getChildren().remove(cv);
							
						}
					}
				}
			}
		});
	}

	public final ListProperty<Carta> cartasProperty() {
		return this.cartas;
	}

	public final ObservableList<Carta> getCartas() {
		return this.cartasProperty().get();
	}

	public final void setCartas(final ObservableList<Carta> cartas) {
		this.cartasProperty().set(cartas);
	}

}
