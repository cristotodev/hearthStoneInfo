package hsi.app;

import hsi.ventanaArranque.VentanaArranqueController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HsiApp extends Application {

	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setResizable(false);
		
		VentanaArranqueController controller = new VentanaArranqueController();
		
		Scene scene = new Scene(VentanaArranqueController.getCopiaView(), 640, 480);
		scene.getStylesheets().add(getClass().getResource("hsiEstilos.css").toExternalForm());
		
		primaryStage.setTitle("HearthStone Info");
		primaryStage.setScene(scene);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

}
