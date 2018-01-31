package hsi.sql;

public class Mazo {

	private Integer ID;
	private String Nombre;
	
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
