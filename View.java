
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {

	private Stage stage;
	
	private BorderPane root;
	
	private Controller controller;

	private List<Pane> panels;
	
	private int panelIndex;
	
	ChoiceBox<Integer> fromChoice;
	ChoiceBox<Integer> toChoice;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		BorderPane root = new BorderPane();
		this.root = root;
		
		controller = new Controller();
		
		HBox priceRangeBox = new HBox();
		priceRangeBox.setId("price-range-box");
		
		int maxPrice = ((controller.getAllPriceStatistics().getMaxValue() + 99) / 100) * 100;
		int minPrice = controller.getAllPriceStatistics().getMinValue() / 100 * 100;
		
		ObservableList<Integer> observableList = FXCollections.observableArrayList();
		for (int i = minPrice; i <= maxPrice; i += 100) {
			observableList.add(i);
		}
		
		Label fromLabel = new Label("From:");
		fromChoice = new ChoiceBox<>();
		fromChoice.setItems(observableList);
		
		Label toLabel = new Label("To:");
		toChoice = new ChoiceBox<>();
		toChoice.setItems(observableList);
		
		Button goButton = new Button("Go");
		goButton.setOnAction(this::processSelectedPriceRange);
		
		priceRangeBox.getChildren().addAll(fromLabel, fromChoice, toLabel, toChoice, goButton);
		root.setTop(priceRangeBox);

		panels = new ArrayList<>();
		
		panels.add(createPanel1());
		panels.add(null);
		panels.add(null);
		
		// Main content Pane.
		root.setCenter(panels.get(0));
		
		HBox navigationBox = new HBox();
		navigationBox.setId("navigation-box");
		
		Button backButton = new Button("<");
		backButton.setOnAction(this::previousPanel);
		Pane space = new Pane();
		Button forwardButton = new Button(">");
		forwardButton.setOnAction(this::nextPanel);
		
		backButton.getStyleClass().add("navigation-button");	// <-- ADD A CLASS (FOR MANY COMPONENTS
		forwardButton.getStyleClass().add("navigation-button");	// <-- 				TO HAVE THE SAME STYLE)
		
		HBox.setHgrow(space, Priority.ALWAYS);
		navigationBox.getChildren().addAll(backButton, space, forwardButton);

		root.setBottom(navigationBox);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		
		//primaryStage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("London Property Marketlace");

		stage.show();
	}

	// Panel 1
	private Pane createPanel1() {
		StackPane stackPane = new StackPane();
		stackPane.setMinSize(500, 500);

		return stackPane;
	}
	
	// Panel 2
	private Pane createPanel2() {
		Pane pane = new Pane();

		MapPane mapPane = new MapPane(controller.getBoroughs(), controller.getBoroughStatistics().getUpperQuartile(), controller.getBoroughStatistics().getLowerQuartile());
	
		pane.getChildren().add(mapPane);
		return pane;
	}

	// Panel 3
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
	
	private void nextPanel(ActionEvent event) {
		panelIndex = (panelIndex + 1 ) % panels.size();
		changePanel();
	}
	
	private void previousPanel(ActionEvent event) {
		panelIndex = (panels.size() + panelIndex - 1) % panels.size();
		changePanel();
	}
	
	private void changePanel() {
		root.setCenter(panels.get(panelIndex));
		stage.sizeToScene();
	}
	
	private void processSelectedPriceRange(ActionEvent event) {
		controller.setStartPrice(fromChoice.getValue());
		controller.setEndPrice(toChoice.getValue());
		controller.processRange();
		panels.set(1, createPanel2());
		panels.set(2, createPanel3());
		changePanel();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
