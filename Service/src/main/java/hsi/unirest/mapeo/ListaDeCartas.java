package hsi.unirest.mapeo;

import java.util.ArrayList;
import java.util.List;

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
