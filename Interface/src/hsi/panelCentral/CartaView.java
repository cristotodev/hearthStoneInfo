package hsi.panelCentral;

import java.net.URL;
import java.util.ResourceBundle;

import hsi.items.Carta;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
				//TODO utilizar bien el modelo, comentado por error temporal
				//imagenView.imageProperty().bind(nv.imgProperty());
				nombreView.textProperty().bind(nv.nombreProperty());
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
    
}
