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
public class PropertyRow extends NormalRow {

	private AirbnbListing property;
	
	public PropertyRow(AirbnbListing property) {
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
		GridPane root = new GridPane();
		
		root.add(new Label("Name:"), 0, 0);
		root.add(new Label(property.getName()), 1, 0);
		
		root.add(new Label("Host Name:"), 0, 1);
		root.add(new Label(property.getHost_name()), 1, 1);
		
		root.add(new Label("Neighbourhood:"), 0, 2);
		root.add(new Label(property.getNeighbourhood()), 1, 2);
		
		root.add(new Label("Latitude:"), 0, 3);
		root.add(new Label(String.valueOf(property.getLatitude())), 1, 3);
		
		root.add(new Label("Longitude:"), 0, 4);
		root.add(new Label(String.valueOf(property.getLongitude())), 1, 4);
		
		root.add(new Label("Room Type:"), 0, 5);
		root.add(new Label(property.getRoom_type()), 1, 5);
		
		root.add(new Label("Price:"), 0, 6);
		root.add(new Label(String.valueOf(property.getPrice())), 1, 6);
		
		root.add(new Label("Minimum Number Of Nights:"), 0, 7);
		root.add(new Label(String.valueOf(property.getMinimumNights())), 1, 7);
		
		root.add(new Label("Number Of Reviews:"), 0, 8);
		root.add(new Label(String.valueOf(property.getNumberOfReviews())), 1, 8);
		
		root.add(new Label("Last Review Date:"), 0, 9);
		root.add(new Label(property.getLastReview()), 1, 9);
		
		root.add(new Label("Reviews Per Month:"), 0, 10);
		root.add(new Label(String.valueOf(property.getReviewsPerMonth())), 1, 10);
		
		root.add(new Label("Availability:"), 0, 11);
		root.add(new Label(String.valueOf(property.getAvailability365())), 1, 11);
		
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Property Description");
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();
	}
	
}
