package hsi.register;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hsi.login.LoginController;
import hsi.sql.FuncionesSQL;
import hsi.ventanaArranque.VentanaArranqueController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterController implements Initializable {

	//model
	private StringProperty usuario;
	private StringProperty password;
	private StringProperty repetirPassword;
	
	// view
	@FXML
	private GridPane view;

	@FXML
	private TextField usuarioTextField;

	@FXML
	private PasswordField contraPassField;

	@FXML
	private PasswordField repetirPassField;
	
	@FXML
    private Button crearButton;

    @FXML
    private Button cancelarButton;

	public RegisterController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		password = new SimpleStringProperty(this, "password");
		repetirPassword = new SimpleStringProperty(this, "repetirPassword");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Bindeos
		usuario.bind(usuarioTextField.textProperty());
		password.bind(contraPassField.textProperty());
		repetirPassword.bind(repetirPassField.textProperty());
		
		//Eventos
		cancelarButton.setOnAction(e -> onCancelarButtonAction(e));
		crearButton.setOnAction(e -> onCrearButtonAction(e));
	}
	
	private void onCrearButtonAction(ActionEvent e) {
		// TODO Dentro de task mandar al servidor y cuando lo haga y vaya bien sustituir borderPane en el centro del padre el login
		if(!usuario.get().equals("")) {
			if(password.get().equals(repetirPassword.get())) {
				try {
					if(!FuncionesSQL.consultaExisteUsuario(usuario.get())) {
						FuncionesSQL.insertarUsuario(usuario.get(), password.get());
						cambiarALogin();
					}else {
						//TODO Indicar que usuario ya existe
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}else {
				//TODO Alerta para contraseña erronea
				System.out.println("Indicar fallo");
			}
		}else {
			//TODO Alerta para usuario vacio
		}
	}

	private void onCancelarButtonAction(ActionEvent e) {
		cambiarALogin();
	}
	
	private void cambiarALogin() {
		LoginController loginController;
		try {
			loginController = new LoginController();
			VentanaArranqueController.getCopiaView().setCenter(loginController.getView());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public GridPane getView() {
		return view;
	}

}
