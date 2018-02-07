package hsi.panelIzquierdo;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PanelIzquierdoModel {

	private StringProperty nombre;
	private ListProperty<String> expansion;
	private ListProperty<String> clase;
	private ListProperty<String> faccion;
	private ListProperty<String> rareza;
	private ListProperty<String> tipo;
	private ListProperty<String> raza;
	private StringProperty ataque;
	private StringProperty vida;
	private StringProperty coste;
	
	public PanelIzquierdoModel() {
		
		nombre = new SimpleStringProperty(this, "nombre");
		expansion = new SimpleListProperty<>(this, "expansion", FXCollections.observableArrayList());
		clase = new SimpleListProperty<>(this, "clase", FXCollections.observableArrayList());
		faccion = new SimpleListProperty<>(this, "faccion", FXCollections.observableArrayList());
		rareza = new SimpleListProperty<>(this, "rareza", FXCollections.observableArrayList());
		tipo = new SimpleListProperty<>(this, "tipo", FXCollections.observableArrayList());
		raza = new SimpleListProperty<>(this, "raza", FXCollections.observableArrayList());
		ataque = new SimpleStringProperty(this, "ataque");
		vida = new SimpleStringProperty(this, "vida");
		coste = new SimpleStringProperty(this, "coste");
		
		
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
	

	public final ListProperty<String> expansionProperty() {
		return this.expansion;
	}
	

	public final ObservableList<String> getExpansion() {
		return this.expansionProperty().get();
	}
	

	public final void setExpansion(final ObservableList<String> expansion) {
		this.expansionProperty().set(expansion);
	}
	

	public final ListProperty<String> claseProperty() {
		return this.clase;
	}
	

	public final ObservableList<String> getClase() {
		return this.claseProperty().get();
	}
	

	public final void setClase(final ObservableList<String> clase) {
		this.claseProperty().set(clase);
	}
	

	public final ListProperty<String> faccionProperty() {
		return this.faccion;
	}
	

	public final ObservableList<String> getFaccion() {
		return this.faccionProperty().get();
	}
	

	public final void setFaccion(final ObservableList<String> faccion) {
		this.faccionProperty().set(faccion);
	}
	

	public final ListProperty<String> rarezaProperty() {
		return this.rareza;
	}
	

	public final ObservableList<String> getRareza() {
		return this.rarezaProperty().get();
	}
	

	public final void setRareza(final ObservableList<String> rareza) {
		this.rarezaProperty().set(rareza);
	}
	

	public final ListProperty<String> tipoProperty() {
		return this.tipo;
	}
	

	public final ObservableList<String> getTipo() {
		return this.tipoProperty().get();
	}
	

	public final void setTipo(final ObservableList<String> tipo) {
		this.tipoProperty().set(tipo);
	}
	

	public final ListProperty<String> razaProperty() {
		return this.raza;
	}
	

	public final ObservableList<String> getRaza() {
		return this.razaProperty().get();
	}
	

	public final void setRaza(final ObservableList<String> raza) {
		this.razaProperty().set(raza);
	}
	

	public final StringProperty ataqueProperty() {
		return this.ataque;
	}
	

	public final String getAtaque() {
		return this.ataqueProperty().get();
	}
	

	public final void setAtaque(final String ataque) {
		this.ataqueProperty().set(ataque);
	}
	

	public final StringProperty vidaProperty() {
		return this.vida;
	}
	

	public final String getVida() {
		return this.vidaProperty().get();
	}
	

	public final void setVida(final String vida) {
		this.vidaProperty().set(vida);
	}
	

	public final StringProperty costeProperty() {
		return this.coste;
	}
	

	public final String getCoste() {
		return this.costeProperty().get();
	}
	

	public final void setCoste(final String coste) {
		this.costeProperty().set(coste);
	}
	
}
