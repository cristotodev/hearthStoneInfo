package hsi.unirest.mapeo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase para el mapeo de los datos de varias cartas del JSON
 * 
 * @author Cristo
 *
 */

public class ListaDeCartas {
	
	private List<Carta> cartas;
	
	public ListaDeCartas() {
		cartas = new ArrayList<>();
	}

	public List<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}
	

}
