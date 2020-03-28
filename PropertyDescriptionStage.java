import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PropertyDescriptionStage extends Stage {
	
	private int row = 0;
	private GridPane root;
	
	public PropertyDescriptionStage(AirbnbListing property) {
		root = new GridPane();
		root.getStyleClass().add("property-description-pane");
		
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
		
		Scene scene = new Scene(root);
		
		setResizable(false);
		setScene(scene);
		setTitle("Property Description");
		initModality(Modality.APPLICATION_MODAL);
	}
	
	private void addDescription(String label, String description) {
		root.add(new Label(label + ":"), 0, row);
		root.add(new Label(description), 1, row);
		row++;
	}
	
}
