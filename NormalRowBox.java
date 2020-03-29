import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Represents a row in a table.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class NormalRowBox extends HBox {
	
	/**
	 * Adds a column to the row.
	 * @param text 
	 */
	public void addColumn(String text) {
		Label label = new Label(text);
		HBox.setHgrow(label, Priority.ALWAYS);
		label.setMinWidth(200);
		getChildren().add(label);
	}
}
