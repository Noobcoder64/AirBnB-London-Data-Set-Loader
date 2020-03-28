import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Stage that displays the descriptions of a property.
 * 
 * @author
 *
 */
public class PropertyDescriptionStage extends Stage {
	
	private GridPane gridPane;
	int row;
	
	public PropertyDescriptionStage(AirbnbListing property) {
		gridPane = new GridPane();
		gridPane.setId("property-description-grid-pane");
		
		addDescription("Name", property.getName());
		addDescription("Host Name", property.getHost_name());
		
		addDescription("Neighbourhood", property.getNeighbourhood());
		
		addDescription("Latitude", String.valueOf(property.getLatitude()));
		
		addDescription("Longitude", String.valueOf(property.getLongitude()));
		
		addDescription("Room Type", property.getRoom_type());
		
		addDescription("Price", String.valueOf(property.getPrice()));
		
		addDescription("Minimum Number Of Nights", String.valueOf(property.getMinimumNights()));
		
		addDescription("Number Of Reviews", String.valueOf(property.getNumberOfReviews()));
		
		addDescription("Last Review Date", property.getLastReview());
		
		addDescription("Reviews Per Month", String.valueOf(property.getReviewsPerMonth()));
		
		addDescription("Availability", String.valueOf(property.getAvailability365()));
		
		Scene scene = new Scene(gridPane);
		scene.getStylesheets().add("style.css");
		
		setResizable(false);
		setScene(scene);
		setTitle("Property Description");
		initModality(Modality.APPLICATION_MODAL);
	}
	
	/**
	 * Adds a description to the property.
	 * 
	 * @param name Information that the description gives
	 * @param description Description of the property
	 */
	private void addDescription(String name, String description) {
		Label nameLabel = new Label(name + ":");
		nameLabel.getStyleClass().add("name-label");
		
		Label descriptionLabel = new Label(description);
		descriptionLabel.getStyleClass().add("description-label");
		
		gridPane.add(nameLabel, 0 , row);
		gridPane.add(descriptionLabel, 1, row);
		row++;
	}
	
}
