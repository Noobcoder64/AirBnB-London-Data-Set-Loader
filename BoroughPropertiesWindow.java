import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BoroughPropertiesWindow extends Pane {
	
	private Borough borough;
	
	private List<AirbnbListing> properties;
	
	private HBox sortBox;
	
	private ChoiceBox<Comparator<AirbnbListing>> sortChoice;
	
	private Pane bodyPane;
	
	public BoroughPropertiesWindow(Borough borough) {
		this.borough = borough;
		this.properties = new ArrayList<>(borough.getProperties());
		
		VBox root = new VBox();
		
		sortBox = new HBox();
		
		Label sortLabel = new Label("Sort By:");
		sortChoice = new ChoiceBox<>();
		
		sortChoice.getItems().add(new HostNameComparator());
		sortChoice.getItems().add(new PriceComparator());
		sortChoice.getItems().add(new ReviewsNumberComparator());
		sortChoice.getItems().add(new MinimumNightsComparator());
		
		Button sortButton = new Button("Sort");
		sortButton.setOnAction(this::sortProperties);
		
		sortBox.getChildren().addAll(sortLabel, sortChoice, sortButton);
		
		/*
		TableColumn<AirbnbListing,String> hostNameColumn = new TableColumn<>("Host name");
		hostNameColumn.setMinWidth(200);
		hostNameColumn.setCellValueFactory(new PropertyValueFactory<AirbnbListing,String>("host_name"));
		
		TableColumn<AirbnbListing,Integer> priceColumn = new TableColumn<>("Price");
		priceColumn.setMinWidth(200);
		priceColumn.setCellValueFactory(new PropertyValueFactory<AirbnbListing,Integer>("price"));
		
		TableColumn<AirbnbListing,Integer> numberOfReviewsColumn = new TableColumn<>("Number of reviews");
		numberOfReviewsColumn.setMinWidth(200);
		numberOfReviewsColumn.setCellValueFactory(new PropertyValueFactory<AirbnbListing,Integer>("numberOfReviews"));
		
		TableColumn<AirbnbListing,Integer> minimumNightsColumn = new TableColumn<>("Minimum number of nights");
		minimumNightsColumn.setMinWidth(200);
		minimumNightsColumn.setCellValueFactory(new PropertyValueFactory<AirbnbListing,Integer>("minimumNights"));
		
		ObservableList<AirbnbListing> observableList = FXCollections.observableArrayList(borough.getProperties());
		
		TableView<AirbnbListing> tableView = new TableView<>();
		tableView.setItems(observableList);
		
		tableView.getColumns().addAll(hostNameColumn, priceColumn, numberOfReviewsColumn, minimumNightsColumn);
		
		pane.getChildren().add(tableView);
		*/
		
		VBox vBox = new VBox();
		
		GridPane headerPane = new GridPane();
		ColumnConstraints columnConstraints = new ColumnConstraints(200);
		headerPane.getColumnConstraints().add(columnConstraints);
		headerPane.getColumnConstraints().add(columnConstraints);
		headerPane.getColumnConstraints().add(columnConstraints);
		headerPane.getColumnConstraints().add(columnConstraints);
		
		headerPane.add(new Label("Host name"), 0, 0);
		headerPane.add(new Label("Price"), 1, 0);
		headerPane.add(new Label("Number of reviews"), 2, 0);
		headerPane.add(new Label("Minimum number of nights"), 3, 0);
		
		vBox.getChildren().add(headerPane);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPrefHeight(500);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		
		bodyPane = new VBox();
		
		showProperties();
		
		scrollPane.setContent(bodyPane);
		
		vBox.getChildren().add(scrollPane);
		
		root.getChildren().add(sortBox);
		root.getChildren().add(vBox);
		
		
		Scene scene = new Scene(root);
		
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle(borough.getName());
		stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();
	}
	
	private void sortProperties(ActionEvent event) {
		bodyPane.getChildren().clear();
		
		Collections.sort(properties, sortChoice.getValue());
		showProperties();
	}
	
	private void showProperties() {
		bodyPane.getChildren().clear();
		
		for (AirbnbListing property : properties) {
			PropertyRow propertyRow = new PropertyRow(property);
			bodyPane.getChildren().add(propertyRow);
		}
		
	}
	
}
