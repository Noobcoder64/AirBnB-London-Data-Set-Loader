import java.util.Map;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Represents the map containing boroughs to be interacted with.
 * All the available boroughs are displayed but those that don't have any properties
 * within the selected price range are disabled.
 * Layout designed as a honeycomb with the boroughs positioned somewhat geographically accurately.
 * The border of each borough's representation changes in colour depending on the number of properties availabe for rental in it.
 * Statistics such as upper and lower quartile are used to determine the range in which a borough is cosidered
 * to have more or less properties than others.
 */
public class MapPane extends GridPane {
	
	private Map<String,Borough> boroughs;
	
	private int upperQuartile;	// Prices above this value will have a red border.
	private int lowerQuartile;	// Prices below this value will have a green border.
	// Prices between the upper and lower quartile will have a yellow border.
	
	public MapPane(Map<String,Borough> boroughs, int upperQuartile, int lowerQuartile) {
		getStyleClass().add("map-pane");
		
		this.boroughs = boroughs;
		this.upperQuartile = upperQuartile;
		this.lowerQuartile = lowerQuartile;
		
		setVgap(-20);
		setHgap(2);
		
		for (int i = 0; i < 7 * 2; i++) {
			ColumnConstraints columnConstraints = new ColumnConstraints(40);
			getColumnConstraints().add(columnConstraints);
		}
			
		for (int i = 0; i < 7; i++) {
			RowConstraints rowConstraints = new RowConstraints(90);
			getRowConstraints().add(rowConstraints);
		}
		
		// Add the boroughs.
		
		addBorough("Enfield", "ENFI", 1, 3, 0);
		
		addBorough("Barnet", "BARN", 0, 2, 1);
		addBorough("Haringey", "HRGY", 0, 3, 1);
		addBorough("Waltham Forest", "WALT", 0, 4, 1);
		
		addBorough("Harrow", "HRRW", 1, 0, 2);
		addBorough("Brent", "BREN", 1, 1, 2);
		addBorough("Camden", "CAMD", 1, 2, 2);
		addBorough("Islington", "ISLI", 1, 3, 2);
		addBorough("Hackney", "HACK", 1, 4, 2);
		addBorough("Redbridge", "REDB", 1, 5, 2);
		addBorough("Havering", "HAVE", 1, 6, 2);
		
		addBorough("Hillingdon", "HILL", 0, 0, 3);
		addBorough("Ealing", "EALI", 0, 1, 3);
		addBorough("Kensington and Chelsea", "KENS", 0, 2, 3);
		addBorough("Westminster", "WSTM", 0, 3, 3);
		addBorough("Tower Hamlets", "TOWH", 0, 4, 3);
		addBorough("Newham", "NEWH", 0, 5, 3);
		addBorough("Barking and Dagenham", "BARK", 0, 6, 3);
		
		addBorough("Hounslow", "HOUN", 1, 0, 4);
		addBorough("Hammersmith and Fulham", "HAMM", 1, 1, 4);
		addBorough("Wandsworth", "WAND", 1, 2, 4);
		addBorough("City of London", "CITY", 1, 3, 4);
		addBorough("Greenwich", "GWCH", 1, 4, 4);
		addBorough("Bexley", "BEXL", 1, 5, 4);
		
		addBorough("Richmond upon Thames", "RICH", 0, 1, 5);
		addBorough("Merton", "MERT", 0, 2, 5);
		addBorough("Lambeth", "LAMB", 0, 3, 5);
		addBorough("Southwark", "STHW", 0, 4, 5);
		addBorough("Lewisham", "LEWS", 0, 5, 5);
		
		addBorough("Kingston upon Thames", "KING", 1, 1, 6);
		addBorough("Sutton", "SUTT", 1, 2, 6);
		addBorough("Croydon", "CROY", 1, 3, 6);
		addBorough("Bromley", "BROM", 1, 4, 6);
	}
	
	/**
	 * Adds a borough in the map
	 * 
	 * @param name Name of the borough.
	 * @param displayName Name to be displayed on the button.
	 * @param offset Used for honeycomb alignment.
	 * @param column Column to be positioned in the honeycomb layout.
	 * @param row Row to be positioned in the honeycomb layout.
	 */
	private void addBorough(String name, String displayName, int offset, int column, int row) {
		BoroughPane boroughPane = new BoroughPane(displayName, 70, 80);
		
		if (boroughs.containsKey(name)) {
			Borough borough = boroughs.get(name);
			
			boroughPane.setBorough(borough);
			
			int numberOfProperties = borough.getNumberOfProperties();
			
			String color;
			if (numberOfProperties > upperQuartile) {
				color = "red";
			} else if (numberOfProperties < lowerQuartile) {
				color = "green";
			} else {
				color = "yellow";
			}
			
			boroughPane.setColor(color);
		}
		
		add(boroughPane, column * 2 + offset, row, 2, 1);
	}

}
