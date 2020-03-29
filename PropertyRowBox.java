import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Represents a row in a table.
 * Also represents a property.
 * Clicking the row pops up a new window containing the description of the property.
 * 
 * @author
 */
public class PropertyRowBox extends NormalRowBox {

	private AirbnbListing property;
	
	public PropertyRowBox(AirbnbListing property) {
		getStyleClass().add("property-row-box");
		
		this.property = property;
		
		addColumn(property.getHost_name());
		addColumn(String.valueOf(property.getPrice()));
		addColumn(String.valueOf(property.getNumberOfReviews()));
		addColumn(String.valueOf(property.getMinimumNights()));
		
		setOnMouseClicked(this::showPropertyDescription);
	}
	
	/**
	 * Pops up a new window containing the description of a property.
	 */
	private void showPropertyDescription(MouseEvent event) {
		PropertyDescriptionStage propertyDescriptionStage = new PropertyDescriptionStage(property);
		propertyDescriptionStage.show();
	}
	
}
