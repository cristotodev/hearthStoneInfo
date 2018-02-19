package hsi.panelCentral;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.items.Carta;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;

public class TodasLasCartasController implements Initializable {

	//view
	@FXML
	private ScrollPane view;
	
	@FXML
	private CartasPane cartasPane;
	
	public TodasLasCartasController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TodasCartasView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO PRUEBAS borrar
		Carta c1 = new Carta();
		c1.setId("1234");
		c1.setImg(new Image("https://pics.filmaffinity.com/pulp_fiction-210382116-large.jpg"));
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
		cartasPane.getCartas().add(c1);
	}

	public ScrollPane getView() {
		return view;
	}
}
