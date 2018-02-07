package hsi.menu;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import hsi.items.Mazo;
import hsi.menu.crearMazo.CrearMazoController;
import hsi.menu.eliminarMazo.EliminarMazoController;
import hsi.menu.verMazo.VerMazoController;
import hsi.panelDerecho.PanelDerechoController;
import hsi.panelDerecho.Busqueda.PanelDerechoBusquedaController;
import hsi.panelDerecho.Favorito.PanelDerechoFavoritoController;
import hsi.panelDerecho.Mazos.PanelDerechoMazosController;
import hsi.sql.FuncionesSQL;
import hsi.app.HsiApp;
import hsi.items.Carta;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
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
	
	//controller
	private PanelDerechoController panelDerechoController;
	private PanelDerechoMazosController panelDerechoMazosController;
	private PanelDerechoBusquedaController panelDerechoBusquedaController;
	private PanelDerechoFavoritoController panelDerechoFavoritoController;

	// model
	private StringProperty usuario;
	private ListProperty<Mazo> mazos;
	private ListProperty<String> favoritas;
	private ObjectProperty<Carta> cartaSeleccionada;
	private ObjectProperty<Mazo> mazoSeleccionado;

	// view
	@FXML
	private BorderPane view;
	
	@FXML
	private BorderPane borderPaneDerecho;

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
		panelDerechoController = new PanelDerechoController();
		panelDerechoMazosController = new PanelDerechoMazosController();
		panelDerechoBusquedaController = new PanelDerechoBusquedaController();
		panelDerechoFavoritoController = new PanelDerechoFavoritoController();
		
		usuario = new SimpleStringProperty(this, "usuario");
		mazos = new SimpleListProperty<>(this, "barajas", FXCollections.observableArrayList());
		favoritas = new SimpleListProperty<>(this, "favoritas", FXCollections.observableArrayList());
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada");
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");

		//mazoController = new CrearMazoController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//TODO Bindeos con atributos de PanelDerechoMazosController
		
		//bindeos
		panelDerechoController.getMazosComboBox().itemsProperty().bind(mazos);
		panelDerechoController.cartaSeleccionadaProperty().bind(cartaSeleccionada);
		mazoSeleccionado.bind(panelDerechoController.mazoSeleccionadoProperty());
		Bindings.bindBidirectional(mazos, panelDerechoController.mazosProperty());
		panelDerechoController.usuarioProperty().bind(usuario);
		
		panelDerechoBusquedaController.cartaSeleccionadaProperty().bind(cartaSeleccionada);
		panelDerechoBusquedaController.mazoSeleccionadoProperty().bind(mazoSeleccionado);
		panelDerechoBusquedaController.usuarioProperty().bind(usuario);
		Bindings.bindBidirectional(favoritas, panelDerechoBusquedaController.favoritosProperty());
		
		panelDerechoFavoritoController.usuarioProperty().bind(usuario);
		panelDerechoFavoritoController.mazoSeleccionadoProperty().bind(mazoSeleccionado);
		panelDerechoFavoritoController.cartaSelecionadaProperty().bind(cartaSeleccionada);
		Bindings.bindBidirectional(favoritas, panelDerechoFavoritoController.favoritosProperty());

		// Eventos
		crearMazoMenu.setOnAction(e -> onCrearMazoMenuAction(e));
		verMazoMenu.setOnAction(e -> onVerMazoMenuAction(e));
		eliminarMazoMenu.setOnAction(e -> onEliminarMazoMenuAction(e));
		favoritosMenu.setOnAction(e -> onFavoritosMenuAction(e));

		informacionMenu.setOnAction(e -> onInformacionMenuAction(e));
		acercaDeMenu.setOnAction(e -> onAcercaDeMenuAction(e));
		
		//Colocación de paneles
		borderPaneDerecho.setCenter(panelDerechoController.getView());
		borderPaneDerecho.setBottom(panelDerechoBusquedaController.getView());
		
		
	}

	private void onAcercaDeMenuAction(ActionEvent e) {
		informacionMenu.getItems();
	}

	private void onInformacionMenuAction(ActionEvent e) {
		//TODO informacion hacerlo
	}

	private void onFavoritosMenuAction(ActionEvent e) {
		//TODO Favorito hacerlo
	}

	private void onEliminarMazoMenuAction(ActionEvent e) {
		try {
			EliminarMazoController controller = new EliminarMazoController();
			controller.getUsuario().bind(usuario);
			controller.getMazos().bind(mazos);
			controller.crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			llenarMazos();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
	}

	private void onVerMazoMenuAction(ActionEvent e) {
		try {
			VerMazoController controller = new VerMazoController();
			controller.getUsuario().bind(usuario);
			controller.getMazos().bind(mazos);
			controller.crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void onCrearMazoMenuAction(ActionEvent e) {
		try {
			CrearMazoController mazoController = new CrearMazoController();
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
			llenarFavoritos();
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
	
	private void llenarFavoritos() {
		favoritas.clear();
		try {
			//TODO la consulta del sql da error comprobar
			favoritas.set(new SimpleListProperty<>(this, "favoritosSQL", FXCollections.observableArrayList(FuncionesSQL.consultaFavoritos(usuario.get()))));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void llenarMazos() throws ClassNotFoundException, SQLException {
		mazos.clear();
		List<hsi.sql.Mazo> mazos = FuncionesSQL.consultaMazos(usuario.get());
		for (hsi.sql.Mazo mazo : mazos) {
			Mazo mazoNuevo = new Mazo();
			mazoNuevo.setId(mazo.getID());
			mazoNuevo.setNombre(mazo.getNombre());
			
			this.mazos.add(mazoNuevo);
		}
	}
	
	public BorderPane getView() {
		return view;
	}

	public StringProperty getUsuario() {
		return usuario;
	}
}
