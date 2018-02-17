package hsi.register;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hsi.controlErrores.ControllerControlesView;
import hsi.login.LoginController;
import hsi.sql.FuncionesSQL;
import hsi.ventanaArranque.VentanaArranqueController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegisterController implements Initializable {

	// model
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
		// Bindeos
		usuario.bind(usuarioTextField.textProperty());
		password.bind(contraPassField.textProperty());
		repetirPassword.bind(repetirPassField.textProperty());

		crearButton.disableProperty()
				.bind(usuario.isEqualTo("").or(password.isEqualTo("")).or(repetirPassword.isEqualTo("")));

		// Eventos
		cancelarButton.setOnAction(e -> onCancelarButtonAction(e));
		crearButton.setOnAction(e -> onCrearButtonAction(e));
	}

	private void onCrearButtonAction(ActionEvent e) {
		if (password.get().equals(repetirPassword.get())) {
			comprobarUsuarioTarea();
		} else {
			try {
				new ControllerControlesView("Las contraseñas no coinciden.",
						"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void comprobarUsuarioTarea() {
		Task<Boolean> task = new Task<Boolean>() {
			@Override
			protected Boolean call() throws Exception {
				return FuncionesSQL.consultaExisteUsuario(usuario.get());
			}
		};

		task.setOnFailed(e1 -> crearRegistroFallo(e1));
		task.setOnSucceeded(e1 -> crearRegistroCorrecto(e1));
		task.run();
	}
	
	private void crearRegistroCorrecto(WorkerStateEvent e1) {
		Boolean result = (Boolean) e1.getSource().getValue();
		if (!result) {
			insertarUsuarioBDTarea();
			cambiarALogin();
		} else {
			try {
				new ControllerControlesView("Ese usuario ya existe.", "..\\..\\..\\resources\\img\\hearthStoneLogo.png")
						.crearVentana();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void insertarUsuarioBDTarea() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				FuncionesSQL.insertarUsuario(usuario.get(), password.get());
				return null;
			}
		};

		task.setOnFailed(e2 -> insertarUsuarioFallo(e2));
		task.run();
	}

	private void insertarUsuarioFallo(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("Hubo un problema al insertar el usuario en el servidor",
					"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void crearRegistroFallo(WorkerStateEvent e1) {
		try {
			new ControllerControlesView("Hubo un problema al conectarse con el servidor",
					"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e) {
			e.printStackTrace();
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
			e1.printStackTrace();
		}
	}

	public GridPane getView() {
		return view;
	}

}
