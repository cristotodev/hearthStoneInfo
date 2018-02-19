package hsi.sql;

/**
 * Clase para el mapeo de los datos que nos devuelve la base de datos.
 * 
 * @author Cristo Estévez
 * @author Carlos Marrero
 *
 */
public class Mazo {

	private Integer ID;
	private String Nombre;

	/**
	 * 
	 * @param id Identificador único del mazo.
	 * @param nombre Nombre del mazo.
	 */
	public Mazo(Integer id, String nombre) {
		this.ID = id;
		this.Nombre = nombre;
	}
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}

}
