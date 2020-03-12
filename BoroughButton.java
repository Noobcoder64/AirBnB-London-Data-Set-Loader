
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class BoroughButton extends StackPane {
	
	private static final Polygon hexagon = new Polygon(new double[]{   		
			2.0, 0.0,
			
			0.0, 1.0,
			0.0, 3.0,
			
			2.0, 4.0,
			
			4.0, 3.0,
			4.0, 1.0
		});
	
	private Button button;
	
	public BoroughButton(String name, double minWidth, double minHeight) {
		super();
		button = new Button(name);
		getChildren().add(button);
		
		button.setMinSize(minWidth, minHeight);
		button.setShape(hexagon);
		button.setPickOnBounds(false);
		
		setStyle("-fx-background-color: yellow");
		setMinSize(minWidth + 10, minHeight + 10);
		setShape(hexagon);
		setPickOnBounds(false);
		
		setOnMouseEntered(this::flipToBack);
		setOnMouseExited(this::flipToFront);
	}
	
	private void flipToBack(MouseEvent event) {
		RotateTransition rotator = createRotator(0, 180);
		
        rotator.play();
	}
	
	private void flipToFront(MouseEvent event) {
		RotateTransition rotator = createRotator(180, 0);
		
        rotator.play();
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
