package hsi.sql;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mazo {

	private IntegerProperty ID;
	private StringProperty Nombre;
	
	public Mazo(int id, String nombre) {
		ID = new SimpleIntegerProperty(this, "ID", id);
		Nombre = new SimpleStringProperty(this,"Nombre", nombre);
	}

	public final IntegerProperty IDProperty() {
		return this.ID;
	}
	

	public final int getID() {
		return this.IDProperty().get();
	}
	

	public final void setID(final int ID) {
		this.IDProperty().set(ID);
	}
	

	public final StringProperty NombreProperty() {
		return this.Nombre;
	}
	

	public final String getNombre() {
		return this.NombreProperty().get();
	}
	

	public final void setNombre(final String Nombre) {
		this.NombreProperty().set(Nombre);
	}
	
	
	
	
}
