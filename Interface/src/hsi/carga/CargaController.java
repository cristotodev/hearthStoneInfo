package hsi.carga;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.mashape.unirest.http.exceptions.UnirestException;

import hsi.login.LoginController;
import hsi.unirest.herramientas.ServicioAPI;
import hsi.unirest.mapeo.Carta;
import hsi.unirest.mapeo.ListaDeCartas;
import hsi.ventanaArranque.VentanaArranqueController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CargaController implements Initializable {

	// Lógica de negocio
	private ServicioAPI servicio;
	
	// view
	@FXML
	private VBox view;

	@FXML
	private ImageView loadImgView;

	@FXML
	private Label frasesLabel;

	public CargaController() throws IOException {
		servicio = new ServicioAPI();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("CargaView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
	private void descargarImagenes() throws UnirestException {		
		ListaDeCartas cartas = servicio.getTodasLasCartas(null, null, null, "esES");
		
		for (Carta carta : cartas.getCartas()) {
			if(carta.getImg() != null) {
				try {
					descargarImagen(carta.getImg());
				} catch (IOException e) {
					System.out.println("fallo normal");
				}
			}
			
			if(carta.getImgGold() != null) {
				try {
					descargarImagen(carta.getImgGold());
				} catch (IOException e) {
					System.out.println("fallo dorada");
				}
			}
		}
	}
	
	private void descargarImagen(String url) throws IOException {
		String destinationFile;
		URL enlace = new URL(url);
		
		File file = new File(enlace.getFile());
		destinationFile = file.getName();
		
		InputStream is = enlace.openStream();
		OutputStream os = new FileOutputStream(new File("10.0.0.22611/images", destinationFile));

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	private void cambiarALogin() throws IOException {
		LoginController loginController = new LoginController();
		VentanaArranqueController.getCopiaView().setCenter(loginController.getView());
	}

	public VBox getView() {
		return view;
	}

}
