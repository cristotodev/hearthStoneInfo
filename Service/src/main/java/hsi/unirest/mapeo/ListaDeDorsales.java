package hsi.unirest.mapeo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para el mapeo de los datos de todos los dorsales del JSON.
 * 
 * @author Cristo
 *
 */
public class ListaDeDorsales {
	
	private List<Dorsal> dorsales;
	
	public ListaDeDorsales() {
		dorsales = new ArrayList<>();
	}

	public List<Dorsal> getDorsales() {
		return dorsales;
	}

	public void setDorsales(List<Dorsal> dorsales) {
		this.dorsales = dorsales;
	}
	
	

}
