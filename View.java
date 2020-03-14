
import java.util.List;
import java.util.Map;

import javafx.application.Application;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {

	private AirbnbDataLoader airbnbDataLoader;

	private Pane panel1;
	private Pane panel2;
	
	public View() {
		airbnbDataLoader = new AirbnbDataLoader();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane root = new BorderPane();
		
		HBox priceRangeBox = new HBox();
		priceRangeBox.setId("price-range-box");  // <-- ADD AN ID (FOR A SINGLE COMPONENT : #)
		priceRangeBox.setAlignment(Pos.CENTER_RIGHT);
		
		Label fromLabel = new Label("From:");
		ChoiceBox<Double> fromChoice = new ChoiceBox<>();

		Label toLabel = new Label("To:");
		ChoiceBox<Double> toChoice = new ChoiceBox<>();

		priceRangeBox.getChildren().addAll(fromLabel, fromChoice, toLabel, toChoice);

		root.setTop(priceRangeBox);

		panel1 = createPanel1();
		panel2 = createPanel2();
		
		// Main content Pane.
		root.setCenter(panel1);
		
		HBox navigationBox = new HBox();
		navigationBox.setId("navigation-box");
		
		Button backButton = new Button("<");
		backButton.setOnAction(e -> root.setCenter(panel1));
		Pane space = new Pane();
		Button forwardButton = new Button(">");
		forwardButton.setOnAction(e -> root.setCenter(panel2));
		
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

	// Create the pane and return the Pane.
	// Do all work here.
	private Pane createPanel1() {
		Pane pane = new Pane();

		GridPane gridPane = new GridPane();
		gridPane.setVgap(-17);
		//gridPane.setGridLinesVisible(true);
		gridPane.setStyle("-fx-background-color: orange");
		
		for (int i = 0; i < 7 * 2; i++) {
			ColumnConstraints columnConstraints = new ColumnConstraints(30);
			gridPane.getColumnConstraints().add(columnConstraints);
		}
		
		RowConstraints rowConstraints = new RowConstraints(70 / 3);
		gridPane.getRowConstraints().add(rowConstraints);		
		
		Map<String,Borough> boroughs = airbnbDataLoader.getBoroughs();
	
		for (Boroughs borough : Boroughs.values()) {
			// Exception
			int offset = 0;
			if (borough.getRow() % 2 == 0) offset++;
			
			String color;
			
			int upperQuartile = airbnbDataLoader.getBoroughStatistics().getUpperQuartile();
			int lowerQuartile = airbnbDataLoader.getBoroughStatistics().getLowerQuartile();
			
			int numberOfProperties = boroughs.get(borough.getName()).getNumberOfProperties();
			
			if (numberOfProperties > upperQuartile) {
				color = "red";
			} else if (numberOfProperties < lowerQuartile) {
				color = "green";
			} else {
				color = "yellow";
			}
			
			gridPane.add(new BoroughPane(boroughs.get(borough.getName()), borough.toString(), color, 50, 60), borough.getColumn() * 2 + offset, borough.getRow() * 3, 2, 3);
		}
		
		pane.getChildren().add(gridPane);
		return pane;
	}

	private Pane createPanel2() {
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
