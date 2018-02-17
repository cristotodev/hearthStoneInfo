package hsi.menu;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import hsi.app.HsiApp;
import hsi.controlErrores.ControllerControlesView;
import hsi.items.Carta;
import hsi.items.Mazo;
import hsi.menu.acercaDe.AcercaDeController;
import hsi.menu.crearMazo.CrearMazoController;
import hsi.menu.eliminarMazo.EliminarMazoController;
import hsi.menu.idioma.cartas.IdiomaCartasController;
import hsi.menu.informacion.InformacionController;
import hsi.menu.verMazo.VerMazoController;
import hsi.panelDerecho.PanelDerechoController;
import hsi.panelDerecho.Busqueda.PanelDerechoBusquedaController;
import hsi.panelDerecho.Favorito.PanelDerechoFavoritoController;
import hsi.panelDerecho.Mazos.PanelDerechoMazosController;
import hsi.panelIzquierdo.PanelIzquierdoController;
import hsi.sql.FuncionesSQL;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
	private PanelIzquierdoController panelIzquierdoController;

	// model
	private StringProperty usuario;
	private ListProperty<Mazo> mazos;
	private ListProperty<String> favoritas;
	private ObjectProperty<Carta> cartaSeleccionada;
	private ObjectProperty<Mazo> mazoSeleccionado;
	private StringProperty idiomaCartas;

	// view
	@FXML
	private BorderPane view;
	
	@FXML
	private BorderPane borderPaneDerecho;
	
	@FXML
	private Menu inicioMenu;

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

	public MenuController() throws IOException {
		
		panelDerechoController = new PanelDerechoController();
		panelDerechoMazosController = new PanelDerechoMazosController();
		panelDerechoBusquedaController = new PanelDerechoBusquedaController();
		panelDerechoFavoritoController = new PanelDerechoFavoritoController();
		panelIzquierdoController = new PanelIzquierdoController();
		
		usuario = new SimpleStringProperty(this, "usuario");
		mazos = new SimpleListProperty<>(this, "barajas", FXCollections.observableArrayList());
		favoritas = new SimpleListProperty<>(this, "favoritas", FXCollections.observableArrayList());
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada");
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		idiomaCartas = new SimpleStringProperty(this, "idiomaCartas", "esES");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		panelDerechoMazosController.usuarioProperty().bind(usuario);
		panelDerechoMazosController.mazoSeleccionadoProperty().bind(mazoSeleccionado);
		panelDerechoMazosController.cartaSeleccionadaProperty().bind(cartaSeleccionada);
		Bindings.bindBidirectional(mazos, panelDerechoMazosController.mazosProperty());
		Bindings.bindBidirectional(favoritas, panelDerechoMazosController.favoritasProperty());
		
		//bindeos
		panelDerechoController.cartaSeleccionadaProperty().bind(cartaSeleccionada);
		Bindings.bindBidirectional(mazoSeleccionado, panelDerechoController.mazoSeleccionadoProperty());
		panelDerechoController.mazosProperty().bind(mazos);
		panelDerechoController.usuarioProperty().bind(usuario);
		
		panelDerechoBusquedaController.cartaSeleccionadaProperty().bind(cartaSeleccionada);
		panelDerechoBusquedaController.mazoSeleccionadoProperty().bind(mazoSeleccionado);
		panelDerechoBusquedaController.usuarioProperty().bind(usuario);
		Bindings.bindBidirectional(favoritas, panelDerechoBusquedaController.favoritosProperty());
		
		panelDerechoFavoritoController.usuarioProperty().bind(usuario);
		panelDerechoFavoritoController.mazoSeleccionadoProperty().bind(mazoSeleccionado);
		panelDerechoFavoritoController.cartaSelecionadaProperty().bind(cartaSeleccionada);
		Bindings.bindBidirectional(favoritas, panelDerechoFavoritoController.favoritosProperty());
		
		panelIzquierdoController.idiomaCartaProperty().bind(idiomaCartas);
		
		// Eventos
		inicioMenu.setOnAction(e -> onInicioMenuAction(e));
		crearMazoMenu.setOnAction(e -> onCrearMazoMenuAction(e));
		verMazoMenu.setOnAction(e -> onVerMazoMenuAction(e));
		eliminarMazoMenu.setOnAction(e -> onEliminarMazoMenuAction(e));
		favoritosMenu.setOnAction(e -> onFavoritosMenuAction(e));
		cartasMenu.setOnAction(e -> onCartasMenuAction(e));
		appMenu.setOnAction(e -> onAppMenuAction(e));
		informacionMenu.setOnAction(e -> onInformacionMenuAction(e));
		acercaDeMenu.setOnAction(e -> onAcercaDeMenuAction(e));
		
		//Colocación de paneles
		borderPaneDerecho.setCenter(panelDerechoController.getView());
		borderPaneDerecho.setBottom(panelDerechoBusquedaController.getView());
		view.setLeft(panelIzquierdoController.getView());
		
	}

	private void onInicioMenuAction(ActionEvent e) {
		borderPaneDerecho.setCenter(panelDerechoController.getView());
		borderPaneDerecho.setBottom(panelDerechoBusquedaController.getView());
		view.setLeft(panelIzquierdoController.getView());
	}

	private Object onAppMenuAction(ActionEvent e) {
		return null;
	}

	private void onCartasMenuAction(ActionEvent e) {
		try {
			IdiomaCartasController controller = new IdiomaCartasController();
			idiomaCartas.set(controller.crearVentana());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void onAcercaDeMenuAction(ActionEvent e) {
		try {
			AcercaDeController controller = new AcercaDeController();
			controller.crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void onInformacionMenuAction(ActionEvent e) {
		try {
			InformacionController controller = new InformacionController();
			controller.crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void onFavoritosMenuAction(ActionEvent e) {
		//TODO Favorito hacerlo
		borderPaneDerecho.setBottom(panelDerechoFavoritoController.getView());
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

			mazoSeleccionado.set(controller.crearVentana());
			borderPaneDerecho.setBottom(panelDerechoMazosController.getView());
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
		
		Task<List<String>> task = new Task<List<String>>() {
			@Override
			protected List<String> call() throws Exception {
				return FuncionesSQL.consultaFavoritos(usuario.get());
			}
		};
		
		task.setOnFailed(e -> falloCargarFavoritosBDTarea(e));
		task.setOnSucceeded(e -> correctoCargarFavoritosBDTarea(e));
		
		task.run();
	}
	
	@SuppressWarnings("unchecked")
	private void correctoCargarFavoritosBDTarea(WorkerStateEvent e) {
		favoritas.addAll((List<String>)e.getSource().getValue());
	}

	private void falloCargarFavoritosBDTarea(WorkerStateEvent e) {
		try {
			new ControllerControlesView("Hubo un problema al cargar los favoritos de la BD",
					"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void llenarMazos() throws ClassNotFoundException, SQLException {
		mazos.clear();
		
		
		Task<List<hsi.sql.Mazo>> task = new Task<List<hsi.sql.Mazo>>() {
			@Override
			protected List<hsi.sql.Mazo> call() throws Exception {
				return  FuncionesSQL.consultaMazos(usuario.get());
			}
		};
		
		task.setOnFailed(e -> falloCargarMazosBDTarea(e));
		task.setOnSucceeded(e -> correctoCargarMazosBDTarea(e));
		
		task.run();
	}
	
	private void correctoCargarMazosBDTarea(WorkerStateEvent e) {
		@SuppressWarnings("unchecked")
		List<hsi.sql.Mazo> mazos = (List<hsi.sql.Mazo>) e.getSource().getValue();
		for (hsi.sql.Mazo mazo : mazos) {
			Mazo mazoNuevo = new Mazo();
			mazoNuevo.setId(mazo.getID());
			mazoNuevo.setNombre(mazo.getNombre());
			
			this.mazos.add(mazoNuevo);
		}
	}

	private void falloCargarMazosBDTarea(WorkerStateEvent e) {
		try {
			new ControllerControlesView("Hubo un problema al cargar los mazos de la BD",
					"..\\..\\..\\resources\\img\\hearthStoneLogo.png").crearVentana();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public BorderPane getView() {
		return view;
	}

	public StringProperty getUsuario() {
		return usuario;
	}
}
