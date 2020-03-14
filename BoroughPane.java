
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
	public BoroughPane(Borough borough, String displayName, String availabilityColor, double minWidth, double minHeight) {
		super();
		this.borough = borough;
		this.displayName = displayName;
		this.showFront = true;
		
		button = new Button(displayName);
		getChildren().add(button);
		
		button.setMinSize(minWidth, minHeight);
		button.setShape(hexagon);
		button.setPickOnBounds(false);
		
		setStyle("-fx-background-color: " + availabilityColor);
		setMinSize(minWidth + 10, minHeight + 10);
		setShape(hexagon);
		setPickOnBounds(false);
		
		button.setOnMouseClicked(this::showProperties);
		setOnMouseEntered(this::flip);
		setOnMouseExited(this::flip);
	}
	
	private void showProperties(MouseEvent event) {
		Pane pane = new Pane();
		
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
		
		GridPane bodyPane = new GridPane();
		
		bodyPane.getColumnConstraints().add(columnConstraints);
		bodyPane.getColumnConstraints().add(columnConstraints);
		bodyPane.getColumnConstraints().add(columnConstraints);
		bodyPane.getColumnConstraints().add(columnConstraints);
		
		int row = 0;
		for (AirbnbListing property : borough.getProperties()) {
			
			
			
			bodyPane.add(new Label(property.getHost_name()), 0, row);
			bodyPane.add(new Label(String.valueOf(property.getPrice())), 1, row);
			bodyPane.add(new Label(String.valueOf(property.getNumberOfReviews())), 2, row);
			bodyPane.add(new Label(String.valueOf(property.getMinimumNights())), 3, row);
			
			row++;
		}
		
		scrollPane.setContent(bodyPane);
		
		vBox.getChildren().add(scrollPane);
		
		pane.getChildren().add(vBox);
		
		
		Scene scene = new Scene(pane);
		
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle(borough.getName());
		 stage.initModality(Modality.APPLICATION_MODAL);

		stage.show();
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
	