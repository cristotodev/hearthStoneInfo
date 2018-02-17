package hsi.panelDerecho;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import hsi.items.Carta;
import hsi.items.Mazo;
import hsi.sql.FuncionesSQL;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;

public class PanelDerechoController implements Initializable {
	
	private final static Integer CANTCARTAS = 30;
	
	//model
	private ListProperty<Mazo> mazos;
	private StringProperty usuario;
	private ObjectProperty<Carta> cartaSeleccionada;
	private ObjectProperty<Mazo> mazoSeleccionado;
	
	//view
	@FXML
    private VBox view;

    @FXML
    private ImageView imgGif;

    @FXML
    private ImageView imgNormal;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label expansionLabel;

    @FXML
    private Label tipoLabel;

    @FXML
    private Label faccionLabel;

    @FXML
    private Label rarezaLabel;

    @FXML
    private Label costeLabel;

    @FXML
    private Label ataqueLabel;

    @FXML
    private Label vidaLabel;

    @FXML
    private Label textoLabel;

    @FXML
    private Label descripcionLabel;

    @FXML
    private Label razaLabel;

    @FXML
    private Label artistaLabel;

    @FXML
    private Label eliteLabel;

    @FXML
    private Label claseLabel;

    @FXML
    private Label mecanismoLabel;

    @FXML
    private ComboBox<Mazo> mazosComboBox;
    
    @FXML
    private Button copiarButton;
	
	public PanelDerechoController() throws IOException {
		usuario = new SimpleStringProperty(this, "usuario");
		mazos = new SimpleListProperty<>(this, "mazos", FXCollections.observableArrayList());
		cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada", new Carta());
		mazoSeleccionado = new SimpleObjectProperty<>(this, "mazoSeleccionado");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PanelDerechoView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// bindeo
		mazosComboBox.itemsProperty().bind(mazos);	
		mazosComboBox.valueProperty().addListener(new ChangeListener<Mazo>() {
			@Override
			public void changed(ObservableValue<? extends Mazo> observable, Mazo oldValue, Mazo newValue) {
				mazoSeleccionado.set(newValue);
				System.out.println(newValue);
			}
		});
		
		copiarButton.disableProperty().bind(mazoSeleccionado.isNull().or(cartaSeleccionada.isNull()));
		
		imgGif.imageProperty().bind(cartaSeleccionada.get().imgDoradaProperty());
		imgNormal.imageProperty().bind(cartaSeleccionada.get().imgProperty());
		nombreLabel.textProperty().bind(cartaSeleccionada.get().nombreProperty());
		expansionLabel.textProperty().bind(cartaSeleccionada.get().expansionProperty());
		tipoLabel.textProperty().bind(cartaSeleccionada.get().tipoProperty());
		faccionLabel.textProperty().bind(cartaSeleccionada.get().faccionProperty());
		rarezaLabel.textProperty().bind(cartaSeleccionada.get().rarezaProperty());
		Bindings.bindBidirectional(costeLabel.textProperty(), cartaSeleccionada.get().costeProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(ataqueLabel.textProperty(), cartaSeleccionada.get().ataqueProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(vidaLabel.textProperty(), cartaSeleccionada.get().saludProperty(), new NumberStringConverter());
		textoLabel.textProperty().bind(cartaSeleccionada.get().accionProperty());
		descripcionLabel.textProperty().bind(cartaSeleccionada.get().descripcionProperty());
		rarezaLabel.textProperty().bind(cartaSeleccionada.get().rarezaProperty());
		artistaLabel.textProperty().bind(cartaSeleccionada.get().artistaProperty());
		eliteLabel.textProperty().bind(cartaSeleccionada.get().eliteProperty().asString());
		claseLabel.textProperty().bind(cartaSeleccionada.get().claseProperty());
		mecanismoLabel.textProperty().bind(cartaSeleccionada.get().mecanismoProperty());
		

		cartaSeleccionada.get().setImg(new Image("http://wow.zamimg.com/images/hearthstone/cards/enus/original/EX1_572.png"));
		cartaSeleccionada.get().setImgDorada(new Image("http://wow.zamimg.com/images/hearthstone/cards/enus/animated/EX1_572_premium.gif"));
		
		//Eventos
		copiarButton.setOnAction(e -> onCopiarButtonAction(e));
	}
	
	private void onCopiarButtonAction(ActionEvent e) {
		try {
			if(FuncionesSQL.consultaNumeroMazoCarta(mazoSeleccionado.get().getId()) < CANTCARTAS)
				FuncionesSQL.insertarMazoCarta(mazoSeleccionado.get().getId(), cartaSeleccionada.get().getId());		
			llenarMazos();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
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
	
	public VBox getView() {
		return view;
	}
	
	public ComboBox<Mazo> getMazosComboBox() {
		return mazosComboBox;
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
	

	public final ObjectProperty<Mazo> mazoSeleccionadoProperty() {
		return this.mazoSeleccionado;
	}
	

	public final Mazo getMazoSeleccionado() {
		return this.mazoSeleccionadoProperty().get();
	}
	

	public final void setMazoSeleccionado(final Mazo mazoSeleccionado) {
		this.mazoSeleccionadoProperty().set(mazoSeleccionado);
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

}
