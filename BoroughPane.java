import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * Represents a borough in the form of an hexagonal button used for the map.
 * Clicking the button pops up a new window containing the properties available in the borough.
 * Hovering over the button flips it and the back displays the number of properties in the borough.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class BoroughPane extends StackPane {
	
	private static final Polygon hexagon = new Polygon(new double[]{   		
			2.0, 0.0,
			
			0.0, 1.0,
			0.0, 3.0,
			
			2.0, 4.0,
			
			4.0, 3.0,
			4.0, 1.0
		});
	
	private Borough borough;	// The represented borough.
	private Button button;	// Button which pops up properties window.
	
	private String displayName;	// Name to be displayed on the button.
	private boolean showFront;	// Boolean to decide whether to show the front or the back.
	
	public BoroughPane(String displayName, double minWidth, double minHeight) {
		getStyleClass().add("borough-pane");
		
		this.displayName = displayName;
		this.showFront = true;
		
		button = new Button(displayName);
		button.setDisable(true);
		button.setMinSize(minWidth, minHeight);
		button.setShape(hexagon);
		button.setPickOnBounds(false);
		
		getChildren().add(button);
		
		setStyle("-fx-background-color: grey");
		setMinSize(minWidth + 10, minHeight + 10);
		setShape(hexagon);
		setPickOnBounds(false);
	}
	
	/**
	 * Set the borough to represent and enable the button.
	 * @param Borough
	 */
	public void setBorough(Borough borough) {
		this.borough = borough;
		
		button.setDisable(false);
		button.setOnMouseClicked(this::showProperties);
		setOnMouseEntered(this::flip);
		setOnMouseExited(this::flip);
	}
	
	/**
	 * Sets the color of the border.
	 * @param Color
	 */
	public void setColor(String color) {
		setStyle("-fx-background-color: " + color);
	}
	
	/**
	 * Pops up a new window containing the properties in the borough.
	 */
	private void showProperties(MouseEvent event) {
		BoroughPropertiesStage boroughPropertiesWindow = new BoroughPropertiesStage(borough);
		boroughPropertiesWindow.show();
	}
	
	/**
	 * Flips the button horizontally and shows the front or the back accordingly.
	 */
	private void flip(MouseEvent event) {
		// Flip the button by 90 degrees.
		RotateTransition rotator90 = createRotator(0, 90);
		rotator90.play();
		
		showFront = !showFront;	
		if (showFront) {
			button.setText(String.valueOf(displayName));
		} else {
			button.setText(String.valueOf(borough.getNumberOfProperties()));
		}
		
		// Flip the button back to original position to give the illusion of a 180 degree flip.
		RotateTransition rotator0 = createRotator(90, 0);
		rotator0.play();
	}
	
	/**
	 * Create a RotateTransition to be used when flipping.
	 * @param fromAngle Angle to start from.
	 * @param toAngle Angle to end to.
	 */
	private RotateTransition createRotator(double fromAngle, double toAngle) {
		RotateTransition rotator = new RotateTransition(Duration.millis(200), button);
        rotator.setAxis(Rotate.Y_AXIS);
	    rotator.setFromAngle(fromAngle);
	    rotator.setToAngle(toAngle);
	    rotator.setCycleCount(1);
	        
	    return rotator;
	}

}
	