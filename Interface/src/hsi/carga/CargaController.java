package hsi.carga;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

import hsi.login.LoginController;
import hsi.ventanaArranque.VentanaArranqueController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CargaController implements Initializable {

	// Lógica de negocio
	private File ruta;
	private File ficheroActualizacion;
	// view
	@FXML
	private VBox view;

	@FXML
	private ImageView loadImgView;

	@FXML
	private Label frasesLabel;

	public CargaController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CargaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			crearEstructuraDirectorios();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//TODO Hacerlo en la carpeta User (buscar permisos)
	private void crearEstructuraDirectorios() throws IOException {
		ruta = new File(System.getProperty("user.home"));
		//ruta = new File(ruta.getParentFile(),".hearthStoneInfo");
		ruta = new File(ruta,".hearthStoneInfo");
		ruta = new File(ruta.getPath(), "img");
		System.out.println(ruta);
		
		// Comprobación si existe el directorio para no sobreescribir las imágenes ya
		// descargadas y el fichero de actualización
		//TODO Mirar if porque no lo hace bien
		if (Files.exists(ruta.getParentFile().toPath())) {
			Files.createDirectories(ruta.toPath());
			// File ficheroActualizacion = new File(ruta, "ficheroActualizacion.hsi");
		} else {
			System.out.println("existe");
		}

	}

	private void cambiarALogin() throws IOException {
		LoginController loginController = new LoginController();
		VentanaArranqueController.getCopiaView().setCenter(loginController.getView());
	}

	public VBox getView() {
		return view;
	}

}
