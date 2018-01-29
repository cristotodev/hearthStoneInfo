package hsi.items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Baraja {
	
	private IntegerProperty id;
	private StringProperty nombre;
	private ListProperty<Carta> cartas;
	
	public Baraja() {
		id = new SimpleIntegerProperty(this, "id");
		nombre = new SimpleStringProperty(this, "nombre");
		cartas = new SimpleListProperty<>(this, "cartas", FXCollections.observableArrayList());
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}
	

	public final int getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
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
