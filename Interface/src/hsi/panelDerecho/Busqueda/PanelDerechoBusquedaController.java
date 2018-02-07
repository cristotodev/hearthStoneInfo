package hsi.panelDerecho.Busqueda;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import hsi.items.Carta;
import hsi.items.Mazo;
import hsi.sql.FuncionesSQL;
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
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class PanelDerechoBusquedaController implements Initializable {
	
	//Model
	private StringProperty usuario;
	private ListProperty<String> favoritos;
	private ObjectProperty<Mazo> mazoSeleccionado;
	private ObjectProperty<Carta> cartaSeleccionada;
	
	//View
    @FXML
    private VBox view;

    @FXML
    private Button insertarEnFavoritoButton;
	
	public PanelDerechoBusquedaController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		favoritos = new SimpleListProperty<>(this, "favoritos", FXCollections.observableArrayList());
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionados");
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoBusquedaView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//TODO Modelo para los bindeos
		insertarEnFavoritoButton.disableProperty().bind(cartaSeleccionada.isNull());
		
		//Eventos
		insertarEnFavoritoButton.setOnAction(e -> onInsertarEnFavoritoButtonAction(e));
	}
	
	private void onInsertarEnFavoritoButtonAction(ActionEvent e) {
		//TODO Hilo
		try {
			FuncionesSQL.insertarFavorito(usuario.get(), cartaSeleccionada.get().getId());
			llenarFavoritos();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private void llenarFavoritos() {
		favoritos.clear();
		try {
			//TODO la consulta del sql da error comprobar
			favoritos.set(new SimpleListProperty<>(this, "favoritosSQL", FXCollections.observableArrayList(FuncionesSQL.consultaFavoritos(usuario.get()))));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public VBox getView() {
		return view;
	}

	public final StringProperty usuarioProperty() {
		return this.usuario;
	}
	

	public final String getUsuario() {
		return this.usuarioProperty().get();
	}
	

	public final void setUsuario(final String usuario) {
		this.usuarioProperty().set(usuario);
	}

	public final ListProperty<String> favoritosProperty() {
		return this.favoritos;
	}
	

	public final ObservableList<String> getFavoritos() {
		return this.favoritosProperty().get();
	}
	

	public final void setFavoritos(final ObservableList<String> favoritos) {
		this.favoritosProperty().set(favoritos);
	}
	

	public final ObjectProperty<Mazo> mazoSeleccionadoProperty() {
		return this.mazoSeleccionado;
	}
	

	public final Mazo getMazoSeleccionado() {
		return this.mazoSeleccionadoProperty().get();
	}
	

	public final void setMazoSeleccionado(final Mazo mazoSeleccionado) {
		this.mazoSeleccionadoProperty().set(mazoSeleccionado);
	}
	

	public final ObjectProperty<Carta> cartaSeleccionadaProperty() {
		return this.cartaSeleccionada;
	}
	

	public final Carta getCartaSeleccionada() {
		return this.cartaSeleccionadaProperty().get();
	}
	

	public final void setCartaSeleccionada(final Carta cartaSeleccionada) {
		this.cartaSeleccionadaProperty().set(cartaSeleccionada);
	}

}
