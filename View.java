
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	
	private boolean[] isSelected;
	
	private Button backButton;
	private Button forwardButton;
	
	private Label fromPriceLabel;
	private Label toPriceLabel;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		BorderPane root = new BorderPane();
		this.root = root;
		
		controller = new Controller();
		
		isSelected = new boolean[2];
		
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
		fromChoice.setOnAction(e -> {
			isSelected[0] = true;
			validateInput();
		});
		
		
		Label toLabel = new Label("To:");
		toChoice = new ChoiceBox<>();
		toChoice.setItems(observableList);
		toChoice.setOnAction(e -> {
			isSelected[1] = true;
			validateInput();
		});

		priceRangeBox.getChildren().addAll(fromLabel, fromChoice, toLabel, toChoice);
		root.setTop(priceRangeBox);

		panels = new ArrayList<>();
		
		panels.add(createPanel1());
		panels.add(null);
		panels.add(null);
		
		// Main content Pane.
		root.setCenter(panels.get(0));
		
		HBox navigationBox = new HBox();
		navigationBox.setId("navigation-box");
		
		backButton = new Button("<");
		backButton.setOnAction(this::previousPanel);
		backButton.setDisable(true);
		Pane space = new Pane();
		forwardButton = new Button(">");
		forwardButton.setOnAction(this::nextPanel);
		forwardButton.setDisable(true);
		
		backButton.getStyleClass().add("navigation-button");	// <-- ADD A CLASS (FOR MANY COMPONENTS
		forwardButton.getStyleClass().add("navigation-button");	// <-- 				TO HAVE THE SAME STYLE)
		
		HBox.setHgrow(space, Priority.ALWAYS);
		navigationBox.getChildren().addAll(backButton, space, forwardButton);

		root.setBottom(navigationBox);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("London Property Marketlace");

		stage.show();
	}

	// Panel 1
	private Pane createPanel1() {
		BorderPane pane = new BorderPane();
		
		StackPane stackPane = new StackPane();
		stackPane.setId("welcome-pane");
		
		Label welcomeLabel = new Label("Welcome to London's Property MarketPlace.\r\n" +
				"Please enter a price range to start.\r\n" +
				"Subsequently, on the right panel you will be able to see Boroughs in London which\r\n" +
				"have properties for rental within your selected price range.\r\n" +
				"The border colour in each borough suggests the availibility of properties. \r\n" +
				"Hover over a borough to see the number of properties. \r\n" +
				"Click on a borough to view the properties in a table. \r\n" +
				"Click a property row to view the description of that property.");
		welcomeLabel.setId("welcome-label");
		
		stackPane.getChildren().add(welcomeLabel);
		
		HBox selectedRangeBox = new HBox();
		selectedRangeBox.setAlignment(Pos.CENTER);
		selectedRangeBox.setId("selected-range-box");
		
		fromPriceLabel = new Label("N/A");
		toPriceLabel = new Label("N/A");
		
		selectedRangeBox.getChildren().add(new Label("Selected price range:"));
		selectedRangeBox.getChildren().add(fromPriceLabel);
		selectedRangeBox.getChildren().add(new Label("-"));
		selectedRangeBox.getChildren().add(toPriceLabel);
		
		pane.setCenter(stackPane);
		pane.setBottom(selectedRangeBox);
		return pane;
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
	
	private void validateInput() {
		if (!isSelected[0] || !isSelected[1]) return;
		
		if (fromChoice.getValue() >= toChoice.getValue()) {
			showInvalidRangeAlert();
		} else {
			backButton.setDisable(false);
			forwardButton.setDisable(false);
			processSelectedPriceRange();
			fromPriceLabel.setText(String.valueOf(fromChoice.getValue()));
			toPriceLabel.setText(String.valueOf(toChoice.getValue()));
		}
	}
	
	private void processSelectedPriceRange() {
		controller.setStartPrice(fromChoice.getValue());
		controller.setEndPrice(toChoice.getValue());
		controller.processRange();
		panels.set(1, createPanel2());
		panels.set(2, createPanel3());
		changePanel();
	}
	
	private void showInvalidRangeAlert() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid");
		alert.setHeaderText(null);
		alert.setContentText("Please input a valid range");
		alert.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
