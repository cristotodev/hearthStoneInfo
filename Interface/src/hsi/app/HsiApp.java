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
		
		primaryStage.setTitle("HearthStone Info");
		primaryStage.setScene(new Scene(VentanaArranqueController.getCopiaView(), 640, 480));
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}

}
