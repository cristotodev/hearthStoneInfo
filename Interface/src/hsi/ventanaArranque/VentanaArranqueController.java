package hsi.ventanaArranque;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.carga.CargaController;
import hsi.login.LoginController;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class VentanaArranqueController implements Initializable {

	// Controllers
	private CargaController cargaController;

	// View
	private static BorderPane copiaView;

	@FXML
	private BorderPane view;

	@FXML
	private ImageView imgView;

	public VentanaArranqueController() throws IOException {
		cargaController = new CargaController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("VentanaArranque.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		copiaView = view;
		view.setCenter(cargaController.getView());
		efectosImagen();

	}

	private void efectosImagen() {
		SequentialTransition paralela = new SequentialTransition();
		paralela.setAutoReverse(false);
		paralela.setCycleCount(1);
		paralela.setNode(imgView);
		paralela.getChildren().addAll(crearEfectoEmergente(), crearEfectoMovimiento());
		paralela.play();

	}

	private FadeTransition crearEfectoEmergente() {
		FadeTransition fade = new FadeTransition();
		fade.setDelay(Duration.seconds(3));
		fade.setDuration(Duration.seconds(3));
		fade.setFromValue(0.0);
		fade.setToValue(1.0);
		return fade;
	}

	private TranslateTransition crearEfectoMovimiento() {
		TranslateTransition translate = new TranslateTransition();
		translate.setDuration(Duration.seconds(1));
		translate.setFromY(10.0);
		translate.setToY(-10.0);
		translate.setAutoReverse(true);
		translate.setCycleCount(TranslateTransition.INDEFINITE);
		translate.setNode(imgView);
		translate.setInterpolator(Interpolator.EASE_BOTH);
		return translate;
	}

	public static BorderPane getCopiaView() {
		return copiaView;
	}

}
