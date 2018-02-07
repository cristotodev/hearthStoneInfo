package hsi.panelDerecho.Mazos;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PanelDerechoMazosController implements Initializable {

	//view
    @FXML
    private VBox view;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button insertarEnFavoritoButton;
    
	public PanelDerechoMazosController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoMazosView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Rellenar eventos, hace falta bindear con lista de mazos de menu y con la seleccionda
		
		//Eventos
		eliminarButton.setOnAction(e -> onEliminarButtonAction(e));
		insertarEnFavoritoButton.setOnAction(e -> onInsertarEnFavoritonButtonAction(e));

	}

	private Object onEliminarButtonAction(ActionEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

	private Object onInsertarEnFavoritonButtonAction(ActionEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

	public VBox getView() {
		return view;
	}

}
