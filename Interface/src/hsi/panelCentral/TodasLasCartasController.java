package hsi.panelCentral;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;

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
		// TODO Auto-generated method stub

	}

	public ScrollPane getView() {
		return view;
	}
}
