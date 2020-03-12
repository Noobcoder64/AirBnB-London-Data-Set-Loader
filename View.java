
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane root = new BorderPane();

		HBox priceRangeBox = new HBox();
		priceRangeBox.setAlignment(Pos.CENTER_RIGHT);

		Label fromLabel = new Label("From:");
		ChoiceBox<Double> fromChoice = new ChoiceBox<>();

		Label toLabel = new Label("To:");
		ChoiceBox<Double> toChoice = new ChoiceBox<>();

		priceRangeBox.getChildren().addAll(fromLabel, fromChoice, toLabel, toChoice);

		root.setTop(priceRangeBox);

		// Main content Pane.
		Pane contentPane = createContentPane();
		root.setCenter(contentPane);
		
		HBox navigationBox = new HBox();

		Button backButton = new Button("<");
		Pane space = new Pane();
		Button forwardButton = new Button(">");

		HBox.setHgrow(space, Priority.ALWAYS);
		navigationBox.getChildren().addAll(backButton, space, forwardButton);

		root.setBottom(navigationBox);

		Scene scene = new Scene(root, 600, 400);

		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setTitle("London Property Marketlace");

		primaryStage.show();
	}

	// Create the pane and return the Pane.
	// Do all work here.
	private Pane createContentPane() {
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
