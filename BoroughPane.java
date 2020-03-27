
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BoroughPane extends StackPane {
	
	private static final Polygon hexagon = new Polygon(new double[]{   		
			2.0, 0.0,
			
			0.0, 1.0,
			0.0, 3.0,
			
			2.0, 4.0,
			
			4.0, 3.0,
			4.0, 1.0
		});
	
	private Borough borough;
	private Button button;
	
	private String displayName;
	private boolean showFront;
	
	public BoroughPane(String displayName, double minWidth, double minHeight) {
		this.displayName = displayName;
		this.showFront = true;
		
		button = new Button(displayName);
		button.setDisable(true);
		getChildren().add(button);
		
		button.setMinSize(minWidth, minHeight);
		button.setShape(hexagon);
		button.setPickOnBounds(false);
		
		setStyle("-fx-background-color: grey");
		setMinSize(minWidth + 10, minHeight + 10);
		setShape(hexagon);
		setPickOnBounds(false);
	}
	
	public void setBorough(Borough borough) {
		this.borough = borough;
		
		button.setDisable(false);
		button.setOnMouseClicked(this::showProperties);
		setOnMouseEntered(this::flip);
		setOnMouseExited(this::flip);
	}
	
	public void setColor(String color) {
		setStyle("-fx-background-color: " + color);
	}
	
	private void showProperties(MouseEvent event) {
		BoroughPropertiesWindow boroughPropertiesWindow = new BoroughPropertiesWindow(borough);
	}
	
	private void flip(MouseEvent event) {
		RotateTransition rotator90 = createRotator(0, 90);
		rotator90.play();
		
		showFront = !showFront;
		
		if (showFront) {
			button.setText(String.valueOf(displayName));
		} else {
			button.setText(String.valueOf(borough.getNumberOfProperties()));
		}
		
		RotateTransition rotator0 = createRotator(90, 0);
		rotator0.play();
	}
	
	private RotateTransition createRotator(double fromAngle, double toAngle) {
		RotateTransition rotator = new RotateTransition(Duration.millis(200), button);
        rotator.setAxis(Rotate.Y_AXIS);
	    rotator.setFromAngle(fromAngle);
	    rotator.setToAngle(toAngle);
	    rotator.setCycleCount(1);
	        
	    return rotator;
	}

}
	