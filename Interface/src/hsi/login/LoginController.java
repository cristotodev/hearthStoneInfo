package hsi.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.menu.MenuController;
import hsi.register.RegisterController;
import hsi.sql.FuncionesSQL;
import hsi.ventanaArranque.VentanaArranqueController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LoginController implements Initializable {

	//Model
	private MenuController menuController;
	private StringProperty usuario;
	private StringProperty password;
	
	//View
	@FXML
    private GridPane view;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField contraPassField;

    @FXML
    private Button entrarButton;

    @FXML
    private Hyperlink crearLink;

	
	public LoginController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		password = new SimpleStringProperty(this, "password");
		
		menuController = new MenuController();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Bindeo
		usuario.bind(usuarioTextField.textProperty());
		password.bind(contraPassField.textProperty());
		menuController.getUsuario().bind(usuario);
		
		//Evento
		crearLink.setOnAction(e -> onCrearCuentaLinkAction(e));
		entrarButton.setOnAction(e -> onEntrarButtonAction(e));

	}
	
	private void onCrearCuentaLinkAction(ActionEvent e) {
		try {
			RegisterController registerController = new RegisterController();
			VentanaArranqueController.getCopiaView().setCenter(registerController.getView());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	private void onEntrarButtonAction(ActionEvent e) {
		// TODO Dentro de task comprobrar en el servidor y si todo va bien cambiar de stage.
		if(!usuario.get().equals("") && !password.get().equals("")) {
			try {
				if(FuncionesSQL.consultaInicioSesion(usuario.get(), password.get()))
						menuController.crearMenu();
				else {
					//TODO datos incorrectos
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}else {
			//TODO Mostrar error
		}
	}

	public GridPane getView() {
		return view;
	}

}
