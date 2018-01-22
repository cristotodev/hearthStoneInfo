package hsi.unirest.mapeo;

import java.util.ArrayList;
import java.util.List;

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
