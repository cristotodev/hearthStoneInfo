package hsi.acciones.carga;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadController implements Initializable {

	private Stage stage;
	
	//View
    @FXML
    private BorderPane view;

    @FXML
    private ImageView laoadImage;
	
    public LoadController() throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadView.fxml"));
		loader.setController(this);
		loader.load();
	}
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}
	
	public void crearVentanaLoad() {
		stage = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initOwner(HsiApp.getPrimaryStage());
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setScene(new Scene(view));
		HsiApp.getPrimaryStage().close();
		stage.showAndWait();
	}
	
	public void cerrarVentanaLoad() {
		stage.close();
	}

}
