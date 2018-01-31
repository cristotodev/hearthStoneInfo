package hsi.menu.crearMazo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.items.Mazo;
import hsi.sql.FuncionesSQL;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CrearMazoController implements Initializable {

	private Stage stage;
	
	//Lógica
	private StringProperty nombreMazo;
	
	//Modelo
	private StringProperty usuario;
	
	// View
	@FXML
	private GridPane view;

	@FXML
	private TextField nombreText;

	@FXML
	private Button crearBtn;

	@FXML
	private Button cancelarBtn;

	public CrearMazoController() throws IOException {
		nombreMazo = new SimpleStringProperty(this, "nombreMazo");
		usuario = new SimpleStringProperty(this, "usuario");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearMazoView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//bindeo
		nombreMazo.bind(nombreText.textProperty());
		
		
		//Eventos
		cancelarBtn.setOnAction(e -> onCancelarBtnAction(e));
		crearBtn.setOnAction(e -> onCrearBtnAction(e));

	}
	
	//TODO Completar función consultando en base de datos
	private void onCrearBtnAction(ActionEvent e) {
		if(!nombreMazo.get().equals("")) {
			try {
				FuncionesSQL.insertarMazo(usuario.get(), nombreMazo.get());
				stage.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			//TODO Crear alerta para campo vacio
			System.out.println("Crear alerta");
		}
		
	}

	private void onCancelarBtnAction(ActionEvent e) {
		stage.close();
	}

	public void crearVentana() {
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		//stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Crear mazo");
		stage.setScene(new Scene(view));
		stage.setResizable(false);
		stage.showAndWait();
	}
	
	public StringProperty getUsuario() {
		return usuario;
	}

}
