package hsi.panelDerecho.Mazos;

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

public class PanelDerechoMazosController implements Initializable {

	//model
	private StringProperty usuario;
	private ObjectProperty<Mazo> mazoSeleccionado;
	private ObjectProperty<Carta> cartaSeleccionada;
	private ListProperty<Mazo> mazos;
	private ListProperty<String> favoritas;
	
	//view
    @FXML
    private VBox view;

    @FXML
    private Button eliminarButton;

    @FXML
    private Button insertarEnFavoritoButton;
    
	public PanelDerechoMazosController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada");
		mazos = new SimpleListProperty<>(this,"mazos", FXCollections.observableArrayList());
		favoritas = new SimpleListProperty<>(this, "favoritas", FXCollections.observableArrayList());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoMazosView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		//bindeos
		eliminarButton.disableProperty().bind(cartaSeleccionada.isNull());
		insertarEnFavoritoButton.disableProperty().bind(cartaSeleccionada.isNull());
		
		//Eventos
		eliminarButton.setOnAction(e -> onEliminarButtonAction(e));
		insertarEnFavoritoButton.setOnAction(e -> onInsertarEnFavoritonButtonAction(e));

	}

	private void onEliminarButtonAction(ActionEvent e) {
		try {
			FuncionesSQL.eliminarMazoCarta(mazoSeleccionado.get().getId(), cartaSeleccionada.get().getId());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	private void onInsertarEnFavoritonButtonAction(ActionEvent e) {
		try {
			FuncionesSQL.insertarFavorito(getUsuario(), cartaSeleccionada.get().getId());
			favoritas.add(cartaSeleccionada.get().getId());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	public VBox getView() {
		return view;
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

	public final ListProperty<Mazo> mazosProperty() {
		return this.mazos;
	}
	

	public final ObservableList<Mazo> getMazos() {
		return this.mazosProperty().get();
	}
	

	public final void setMazos(final ObservableList<Mazo> mazos) {
		this.mazosProperty().set(mazos);
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

	public final ListProperty<String> favoritasProperty() {
		return this.favoritas;
	}
	

	public final ObservableList<String> getFavoritas() {
		return this.favoritasProperty().get();
	}
	

	public final void setFavoritas(final ObservableList<String> favoritas) {
		this.favoritasProperty().set(favoritas);
	}

}
