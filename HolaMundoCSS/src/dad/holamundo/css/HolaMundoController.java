package dad.holamundo.css;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class HolaMundoController implements Initializable {

	private StringProperty nombre = new SimpleStringProperty(this, "nombre");
	private StringProperty saludo = new SimpleStringProperty(this, "saludo");
	
	@FXML
    private VBox view;

    @FXML
    private TextField nombreText;

    @FXML
    private Button saludarButton;

    @FXML
    private Button despedirButton;

    @FXML
    private Button salirButton;

    @FXML
    private Label saludoLabel;
    
    public HolaMundoController() throws IOException {
		
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("HolaView.fxml"));
    	loader.setController(this);
    	loader.load();
	}
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	
    	nombre.bind(nombreText.textProperty());
    	
		
	}

    @FXML
    void onDespedirButtonAction(ActionEvent event) {
    	saludoLabel.setText("Adios! "+ nombre.get());
    }

    @FXML
    void onSalirButtonAction(ActionEvent event) {
    	HolaMundoAPP.primaryStage.close();
    }

    @FXML
    void onSaludarButtonAction(ActionEvent event) {
    	saludoLabel.setText("Hola! "+ nombre.get());
    }
	
	public VBox getView() {
		return view;
	}

	


}
