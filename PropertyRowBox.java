import javafx.scene.input.MouseEvent;

/**
 * Represents a row in a table.
 * Also represents a property.
 * Clicking the row pops up a new window containing the description of the property.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
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
