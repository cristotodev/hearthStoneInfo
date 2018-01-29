package hsi.items;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Carta {
	
	private StringProperty id;
	private StringProperty nombre;
	private StringProperty expansion;
	private StringProperty tipo;
	private StringProperty faccion;
	private StringProperty rareza;
	private IntegerProperty coste;
	private IntegerProperty ataque;
	private IntegerProperty salud;
	private StringProperty accion;
	private StringProperty descripcion;
	private BooleanProperty elite;
	private StringProperty raza;
	private StringProperty artista;
	private StringProperty clase;
	private StringProperty img;
	private StringProperty imgDorada;
	private StringProperty mecanismo;
	
	public Carta() {
		id = new SimpleStringProperty(this, "id");
		nombre = new SimpleStringProperty(this, "nombre");
		expansion = new SimpleStringProperty(this, "expansion");
		tipo = new SimpleStringProperty(this, "tipo");
		faccion = new SimpleStringProperty(this, "faccion");
		rareza = new SimpleStringProperty(this, "rareza");
		coste = new SimpleIntegerProperty(this, "coste");
		ataque = new SimpleIntegerProperty(this, "ataque");
		salud = new SimpleIntegerProperty(this, "salud");
		accion = new SimpleStringProperty(this, "accion");
		descripcion = new SimpleStringProperty(this, "descripcion");
		elite = new SimpleBooleanProperty(this, "elite");
		raza = new SimpleStringProperty(this, "raza");
		artista = new SimpleStringProperty(this, "artista");
		clase = new SimpleStringProperty(this, "clase");
		img = new SimpleStringProperty(this, "img");
		imgDorada = new SimpleStringProperty(this, "imgDorada");
		mecanismo = new SimpleStringProperty(this, "mecanismo");
	}

	public final StringProperty idProperty() {
		return this.id;
	}
	

	public final String getId() {
		return this.idProperty().get();
	}
	

	public final void setId(final String id) {
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
	

	public final StringProperty expansionProperty() {
		return this.expansion;
	}
	

	public final String getExpansion() {
		return this.expansionProperty().get();
	}
	

	public final void setExpansion(final String expansion) {
		this.expansionProperty().set(expansion);
	}
	

	public final StringProperty tipoProperty() {
		return this.tipo;
	}
	

	public final String getTipo() {
		return this.tipoProperty().get();
	}
	

	public final void setTipo(final String tipo) {
		this.tipoProperty().set(tipo);
	}
	

	public final StringProperty faccionProperty() {
		return this.faccion;
	}
	

	public final String getFaccion() {
		return this.faccionProperty().get();
	}
	

	public final void setFaccion(final String faccion) {
		this.faccionProperty().set(faccion);
	}
	

	public final StringProperty rarezaProperty() {
		return this.rareza;
	}
	

	public final String getRareza() {
		return this.rarezaProperty().get();
	}
	

	public final void setRareza(final String rareza) {
		this.rarezaProperty().set(rareza);
	}
	

	public final IntegerProperty costeProperty() {
		return this.coste;
	}
	

	public final int getCoste() {
		return this.costeProperty().get();
	}
	

	public final void setCoste(final int coste) {
		this.costeProperty().set(coste);
	}
	

	public final IntegerProperty ataqueProperty() {
		return this.ataque;
	}
	

	public final int getAtaque() {
		return this.ataqueProperty().get();
	}
	

	public final void setAtaque(final int ataque) {
		this.ataqueProperty().set(ataque);
	}
	

	public final IntegerProperty saludProperty() {
		return this.salud;
	}
	

	public final int getSalud() {
		return this.saludProperty().get();
	}
	

	public final void setSalud(final int salud) {
		this.saludProperty().set(salud);
	}
	

	public final StringProperty accionProperty() {
		return this.accion;
	}
	

	public final String getAccion() {
		return this.accionProperty().get();
	}
	

	public final void setAccion(final String accion) {
		this.accionProperty().set(accion);
	}
	

	public final StringProperty descripcionProperty() {
		return this.descripcion;
	}
	

	public final String getDescripcion() {
		return this.descripcionProperty().get();
	}
	

	public final void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}
	

	public final BooleanProperty eliteProperty() {
		return this.elite;
	}
	

	public final boolean isElite() {
		return this.eliteProperty().get();
	}
	

	public final void setElite(final boolean elite) {
		this.eliteProperty().set(elite);
	}
	

	public final StringProperty razaProperty() {
		return this.raza;
	}
	

	public final String getRaza() {
		return this.razaProperty().get();
	}
	

	public final void setRaza(final String raza) {
		this.razaProperty().set(raza);
	}
	

	public final StringProperty artistaProperty() {
		return this.artista;
	}
	

	public final String getArtista() {
		return this.artistaProperty().get();
	}
	

	public final void setArtista(final String artista) {
		this.artistaProperty().set(artista);
	}
	

	public final StringProperty claseProperty() {
		return this.clase;
	}
	

	public final String getClase() {
		return this.claseProperty().get();
	}
	

	public final void setClase(final String clase) {
		this.claseProperty().set(clase);
	}
	

	public final StringProperty imgProperty() {
		return this.img;
	}
	

	public final String getImg() {
		return this.imgProperty().get();
	}
	

	public final void setImg(final String img) {
		this.imgProperty().set(img);
	}
	

	public final StringProperty imgDoradaProperty() {
		return this.imgDorada;
	}
	

	public final String getImgDorada() {
		return this.imgDoradaProperty().get();
	}
	

	public final void setImgDorada(final String imgDorada) {
		this.imgDoradaProperty().set(imgDorada);
	}
	

	public final StringProperty mecanismoProperty() {
		return this.mecanismo;
	}
	

	public final String getMecanismo() {
		return this.mecanismoProperty().get();
	}
	

	public final void setMecanismo(final String mecanismo) {
		this.mecanismoProperty().set(mecanismo);
	}
}
