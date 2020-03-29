import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Pane that displays the properties available for rental in a borough.
 * A dropdown menu enables the sorting of the properties by host name, price, number of reviews and minimum number of nights.
 * Clicking a property pops up a new window containing the description of that property.
 */
public class BoroughPropertiesStage extends Stage {
	
	private List<AirbnbListing> properties;
	
	private ChoiceBox<Comparator<AirbnbListing>> sortChoice;
	
	private Pane bodyPane;
	
	public BoroughPropertiesStage(Borough borough) {
		this.properties = new ArrayList<>(borough.getProperties());
		
		VBox root = new VBox();
		
		Pane sortBox = createSortBox();
		Pane table = createTable();
		
		root.getChildren().add(sortBox);
		root.getChildren().add(table);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		
		setResizable(false);
		setScene(scene);
		setTitle(borough.getName());
		initModality(Modality.APPLICATION_MODAL);
	}
	
	/**
	 * Creates a drop-down menu that allows to sort the properties by number of reviews, price, minimum nights or alphabetically by host name.
	 */
	private Pane createSortBox() {
		HBox sortBox = new HBox();
		sortBox.setId("sort-box");
		
		Label sortLabel = new Label("Sort By:");
		sortChoice = new ChoiceBox<>();
		
		sortChoice.getItems().add(new HostNameComparator());
		sortChoice.getItems().add(new PriceComparator());
		sortChoice.getItems().add(new ReviewsNumberComparator());
		sortChoice.getItems().add(new MinimumNightsComparator());
		
		sortChoice.getSelectionModel().selectFirst();
		Collections.sort(properties, sortChoice.getValue());
		
		sortChoice.setOnAction(this::sortProperties);
		
		sortBox.getChildren().addAll(sortLabel, sortChoice);
		return sortBox;
	}
	
	/**
	 * Creates a table that allows the properties for rental in the borough to be displayed.
	 */
	private Pane createTable() {
		VBox tableBox = new VBox();
		tableBox.setId("table-box");
		
		NormalRowBox headerPane = new NormalRowBox();
		headerPane.getStyleClass().add("header-pane");
		headerPane.addColumn("Host name");
		headerPane.addColumn("Price");
		headerPane.addColumn("Number of reviews");
		headerPane.addColumn("Minimum number of nights");
		
		tableBox.getChildren().add(headerPane);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefHeight(500);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		
		bodyPane = new VBox();
		bodyPane.getStyleClass().add("body-pane");
		
		showProperties();
		
		scrollPane.setContent(bodyPane);
		
		tableBox.getChildren().add(scrollPane);
		return tableBox;
	}
	
	/**
	 * Sorts properties by the selected option in the dropdown menu (choice box).
	 */
	private void sortProperties(ActionEvent event) {
		bodyPane.getChildren().clear();
		
		Collections.sort(properties, sortChoice.getValue());
		showProperties();
	}
	
	/**
	 * Displays the properties in the body of the table.
	 */
	private void showProperties() {
		bodyPane.getChildren().clear();
		
		for (AirbnbListing property : properties) {
			PropertyRowBox propertyRow = new PropertyRowBox(property);
			bodyPane.getChildren().add(propertyRow);
		}
	}
	
}
