package dad.holamundo.css;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HolaMundoAPP extends Application {

	private HolaMundoController holaMundoController;
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		HolaMundoAPP.primaryStage = primaryStage;
		holaMundoController = new HolaMundoController();
		
		
		Scene scene = new Scene(holaMundoController.getView(), 420, 300);
		scene.getStylesheets().add(getClass().getResource("holamundo.css").toExternalForm());
		
		primaryStage.setTitle("HolaMundo+CSS");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
