package hsi.items;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase que contendrá la información de un mazo.
 * Se utilizar para mapear los objetos del servicio a los que usa la interfa.
 * 
 * @author Cristo
 *
 */
public class Mazo {
	
	private IntegerProperty id;
	private StringProperty nombre;
	
	public Mazo() {
		id = new SimpleIntegerProperty(this, "id");
		nombre = new SimpleStringProperty(this, "nombre");
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
	
	@Override
	public String toString() {
		return this.getNombre();
	}

	
}
