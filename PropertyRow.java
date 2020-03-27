import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PropertyRow extends HBox {

	private AirbnbListing property;
	
	public PropertyRow(AirbnbListing property) {
		this.property = property;
		
		addColumn(property.getHost_name());
		addColumn(String.valueOf(property.getPrice()));
		addColumn(String.valueOf(property.getNumberOfReviews()));
		addColumn(String.valueOf(property.getMinimumNights()));
		
		setOnMouseClicked(this::showPropertyDescription);
	}
	
	private void addColumn(String text) {
		Label label = new Label(text);
		//label.setStyle("-fx-border-color: red");
		HBox.setHgrow(label, Priority.ALWAYS);
		label.setMinWidth(200);
		getChildren().add(label);
	}
	
	private void showPropertyDescription(MouseEvent event) {
		VBox root = new VBox();
		
		root.getChildren().add(new Label(property.getHost_name()));
		
		Scene scene = new Scene(root, 100, 100);
		
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		//stage.setTitle(borough.getName());
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();
	}
	
}
