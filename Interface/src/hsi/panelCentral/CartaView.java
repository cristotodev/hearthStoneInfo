package hsi.panelCentral;

import java.net.URL;
import java.util.ResourceBundle;

import hsi.items.Carta;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * 
 * @author Carlos Marrero Ramos - Xibhu
 *
 */
public class CartaView extends BorderPane implements Initializable{

	/**
	 * Carta que se utiliza de forma individual para ser mostrada en la vista
	 */
	private ObjectProperty<Carta> carta = new SimpleObjectProperty<>(this, "carta");
	private ObjectProperty<Carta> cartaSeleccionada = new SimpleObjectProperty<>(this, "cartaSeleccionada", new Carta());
	
    @FXML
    private ImageView imagenView;

    @FXML
    private Label nombreView;
	/**
	 * Constructor para instanciar la vista de este componentes customizado
	 */
    public CartaView() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CartaView.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.carta.addListener((o, ov, nv) -> {
			if(nv != null) {
				imagenView.imageProperty().bind(nv.imgProperty());
				nombreView.textProperty().bind(nv.nombreProperty());
			}
		});
		
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	cartaSeleccionada.set(carta.get());
		    }
		});
		
	}

	public final ObjectProperty<Carta> cartaProperty() {
		return this.carta;
	}
	

	public final Carta getCarta() {
		return this.cartaProperty().get();
	}
	

	public final void setCarta(final Carta carta) {
		this.cartaProperty().set(carta);
	}
	
    /**
     * Función modificada para poder realizar la eliminación de elementos en la vista.
     */
    @Override
    public boolean equals(Object obj) {
    	
    	CartaView cv = (CartaView) obj;
    	
    	return cv.getCarta().getId().equals(getCarta().getId());
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
