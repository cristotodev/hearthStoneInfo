package hsi.menu;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import hsi.items.Mazo;
import hsi.menu.crearMazo.CrearMazoController;
import hsi.sql.FuncionesSQL;
import hsi.app.HsiApp;
import hsi.items.Carta;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	private Stage stage;

	// model
	private StringProperty usuario;
	private ListProperty<Mazo> mazos;
	private ListProperty<Carta> favoritas;

	// view
	@FXML
	private BorderPane view;

	@FXML
	private MenuItem crearMazoMenu;

	@FXML
	private MenuItem verMazoMenu;

	@FXML
	private MenuItem eliminarMazoMenu;

	@FXML
	private Menu favoritosMenu;

	@FXML
	private Menu cartasMenu;

	@FXML
	private Menu appMenu;

	@FXML
	private Menu informacionMenu;

	@FXML
	private Menu acercaDeMenu;

	// TODO Coger el listado para cargar y mirar como coger el seleccionado
	// TODO ¿Rellenar desde modelo con listado de string y coger el seleccionado?

	public MenuController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		mazos = new SimpleListProperty<>(this, "barajas", FXCollections.observableArrayList());
		favoritas = new SimpleListProperty<>(this, "favoritas", FXCollections.observableArrayList());

		//mazoController = new CrearMazoController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Eventos
		crearMazoMenu.setOnAction(e -> onCrearMazoMenuAction(e));
		verMazoMenu.setOnAction(e -> onVerMazoMenuAction(e));
		eliminarMazoMenu.setOnAction(e -> onEliminarMazoMenuAction(e));
		favoritosMenu.setOnAction(e -> onFavoritosMenuAction(e));

		informacionMenu.setOnAction(e -> onInformacionMenuAction(e));
		acercaDeMenu.setOnAction(e -> onAcercaDeMenuAction(e));
		
		
	}

	private void onAcercaDeMenuAction(ActionEvent e) {
		informacionMenu.getItems();
	}

	private void onInformacionMenuAction(ActionEvent e) {

	}

	private void onFavoritosMenuAction(ActionEvent e) {

	}

	private void onEliminarMazoMenuAction(ActionEvent e) {

	}

	private void onVerMazoMenuAction(ActionEvent e) {

	}

	private void onCrearMazoMenuAction(ActionEvent e) {
		CrearMazoController mazoController;
		try {
			mazoController = new CrearMazoController();
			mazoController.getUsuario().bind(usuario);
			mazoController.crearVentana();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			llenarMazos();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
	}

	public void crearMenu() {
		//Llenar listado mazos con los que ya tiene el usuario
		//Lo situamos en crearMenu para cargarlo una vez se introduce el usuario y es correcto
		try {
			llenarMazos();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		stage = new Stage();
		stage.initOwner(HsiApp.getPrimaryStage());
		// stage.getIcons().add(HsiApp.getPrimaryStage().getIcons().get(0));
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("HearthStoneInfo");
		stage.setScene(new Scene(view, 720, 600));
		HsiApp.getPrimaryStage().close();
		stage.showAndWait();
	}

	public BorderPane getView() {
		return view;
	}

	public StringProperty getUsuario() {
		return usuario;
	}
	
	private void llenarMazos() throws ClassNotFoundException, SQLException {
		List<hsi.sql.Mazo> mazos = FuncionesSQL.consultaMazos(usuario.get());
		for (hsi.sql.Mazo mazo : mazos) {
			Mazo mazoNuevo = new Mazo();
			mazoNuevo.setId(mazo.getID());
			mazoNuevo.setNombre(mazo.getNombre());
			
			this.mazos.add(mazoNuevo);
		}
	}

}
