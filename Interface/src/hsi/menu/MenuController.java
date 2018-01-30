package hsi.menu;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import hsi.items.Baraja;
import hsi.items.Carta;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

public class MenuController implements Initializable {

	//model
	private ListProperty<Baraja> barajas;
	private ListProperty<Carta> favoritas;
	
	//view
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
    
    //TODO Coger el listado para cargar y mirar como coger el seleccionado
    //TODO ¿Rellenar desde modelo con listado de string y coger el seleccionado?
  
    
	public MenuController() throws IOException {
		barajas = new SimpleListProperty<>(this, "barajas", FXCollections.observableArrayList());
		favoritas = new SimpleListProperty<>(this, "favoritas", FXCollections.observableArrayList());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//Eventos
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
		//TODO Nuevo stage
	}
	
	public BorderPane getView() {
		return view;
	}

}
