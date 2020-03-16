
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {

	private Controller controller;

	private List<Pane> panels;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		
		HBox priceRangeBox = new HBox();
		priceRangeBox.setId("price-range-box");  // <-- ADD AN ID (FOR A SINGLE COMPONENT : #)
		//priceRangeBox.setAlignment(Pos.CENTER_RIGHT);
		
		int maxPrice = 7000; //((controller.getPriceStatistics().getMaxValue() + 99) / 100) * 100;
		int minPrice = 0; //controller.getPriceStatistics().getMinValue() / 100 * 100;
		
		ObservableList<Integer> observableList = FXCollections.observableArrayList();
		
		for (int i = minPrice; i <= maxPrice; i += 100) {
			observableList.add(i);
		}
		
		Label fromLabel = new Label("From:");
		ChoiceBox<Integer> fromChoice = new ChoiceBox<>();
		fromChoice.setItems(observableList);
		
		Label toLabel = new Label("To:");
		ChoiceBox<Integer> toChoice = new ChoiceBox<>();
		toChoice.setItems(observableList);
		
		priceRangeBox.getChildren().addAll(fromLabel, fromChoice, toLabel, toChoice);
		root.setTop(priceRangeBox);

		controller = new Controller(6000, 7000);
		
		panels = new ArrayList<>();
		
		panels.add(createPanel1());
		panels.add(createPanel2());
		panels.add(createPanel3());
	
		// Main content Pane.
		root.setCenter(panels.get(0));
		
		HBox navigationBox = new HBox();
		navigationBox.setId("navigation-box");
		
		Button backButton = new Button("<");
		backButton.setOnAction(e -> { root.setCenter(panels.get(1));
			primaryStage.sizeToScene();
		});
		Pane space = new Pane();
		Button forwardButton = new Button(">");
		forwardButton.setOnAction(e -> { root.setCenter(panels.get(2));
			primaryStage.sizeToScene();
		});
		
		backButton.getStyleClass().add("navigation-button");	// <-- ADD A CLASS (FOR MANY COMPONENTS
		forwardButton.getStyleClass().add("navigation-button");	// <-- 				TO HAVE THE SAME STYLE)
		
		HBox.setHgrow(space, Priority.ALWAYS);
		navigationBox.getChildren().addAll(backButton, space, forwardButton);

		root.setBottom(navigationBox);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("css/style.css");
		
		//primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("London Property Marketlace");

		primaryStage.show();
	}

	private Pane createPanel1() {
		StackPane stackPane = new StackPane();
		stackPane.setMinSize(500, 500);
		stackPane.setAlignment(Pos.CENTER);
		
		VBox vBox = new VBox();
		
		Label label = new Label("Welcome");
		vBox.getChildren().add(label);
		
		HBox priceRangeBox = new HBox();
		
		Label fromLabel = new Label("From:");
		ChoiceBox<Double> fromChoice = new ChoiceBox<>();

		Label toLabel = new Label("To:");
		ChoiceBox<Double> toChoice = new ChoiceBox<>();

		priceRangeBox.getChildren().addAll(fromLabel, fromChoice, toLabel, toChoice);
		vBox.getChildren().add(priceRangeBox);
		
		stackPane.getChildren().add(vBox);
		return stackPane;
	}
	
	// Create the pane and return the Pane.
	// Do all work here.
	private Pane createPanel2() {
		Pane pane = new Pane();

		GridPane gridPane = new GridPane();
		gridPane.setVgap(-20);
		gridPane.setHgap(2);
		//gridPane.setGridLinesVisible(true);
		gridPane.setStyle("-fx-background-color: orange");
		
		for (int i = 0; i < 7 * 2; i++) {
			ColumnConstraints columnConstraints = new ColumnConstraints(40);
			gridPane.getColumnConstraints().add(columnConstraints);
		}
			
		for (int i = 0; i < 7; i++) {
			RowConstraints rowConstraints = new RowConstraints(90);
			gridPane.getRowConstraints().add(rowConstraints);
		}
		
		Map<String,Borough> boroughs = controller.getBoroughs();
	
		for (Boroughs borough : Boroughs.values()) {
			// Exception
			
			int offset = 0;
			if (borough.getRow() % 2 == 0) offset++;
			
			BoroughPane boroughPane = new BoroughPane(borough.toString(), 70, 80);
			
			gridPane.add(boroughPane, borough.getColumn() * 2 + offset, borough.getRow(), 2, 1);
			
			if (boroughs.containsKey(borough.getName())) {
				Borough actualBorough = boroughs.get(borough.getName());
				
				boroughPane.setBorough(actualBorough);
				
				int upperQuartile = controller.getBoroughStatistics().getUpperQuartile();
				int lowerQuartile = controller.getBoroughStatistics().getLowerQuartile();
				
				int numberOfProperties = actualBorough.getNumberOfProperties();
				
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
			
		}
		
		pane.getChildren().add(gridPane);
		return pane;
	}

	private Pane createPanel3() {
		Pane pane = new Pane();
		
		GridPane gridPane = new GridPane();
		
		// Put all components in this pane.
		BorderPane sectionPane = new BorderPane();
		
		Button nextButton = new Button(">");
		sectionPane.setRight(nextButton);
		
		Button backButton = new Button("<");
		sectionPane.setLeft(backButton);
	
		VBox statisticsBox = new VBox();
		Label statisticsLabel = new Label("Number of Properties");
		Label numberLabel = new Label("20");
		statisticsBox.getChildren().addAll(statisticsLabel, numberLabel);
		sectionPane.setCenter(statisticsBox);

		gridPane.add(sectionPane, 0, 0);
		
		pane.getChildren().add(gridPane);
		return pane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
