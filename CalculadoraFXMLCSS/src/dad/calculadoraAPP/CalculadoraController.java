package dad.calculadoraAPP;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class CalculadoraController implements Initializable {


	@FXML
    private TextField displayText;
	@FXML
	private GridPane view;

	private Calculadora calc;
	
	public CalculadoraController() throws IOException {
		calc = new Calculadora();
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CalculadoraView.fxml"));
		loader.setController(this);
		loader.load();
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displayText.textProperty().bind(calc.pantallaProperty());
	}
	
	
	public GridPane getView() {
		return view;
	}


	@FXML
    void onInsertarNumeroButtonAction(ActionEvent e) {
		String ev = e.getSource().toString();
		Character c = ev.charAt(ev.length()-2);
		calc.insertar(c);
    }

	
    @FXML
    void onInsertarComaButtonAction(ActionEvent e) {
    	calc.insertarComa();
    }
    
    

    @FXML
    void onOperarButtonAction(ActionEvent e) {
    	String ev = e.getSource().toString();
		Character c = ev.charAt(ev.length()-2);
		calc.operar(c);
    }
    
    
    
    @FXML
    void onBorrarButtonAction(ActionEvent e) {
    	calc.borrar();
    }

    @FXML
    void onBorrarTodoButtonAction(ActionEvent e) {
    	calc.borrarTodo();
    }
	
}
