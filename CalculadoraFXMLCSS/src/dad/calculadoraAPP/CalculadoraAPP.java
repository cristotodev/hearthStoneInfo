package dad.calculadoraAPP;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CalculadoraAPP extends Application {

	private CalculadoraController calculadoraController;
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		calculadoraController = new CalculadoraController();
		CalculadoraAPP.primaryStage = primaryStage;
		
		Scene scene = new Scene(calculadoraController.getView(), 600, 500);
		scene.getStylesheets().add(getClass().getResource("moderno.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Calculadora");
		primaryStage.show();
		
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
