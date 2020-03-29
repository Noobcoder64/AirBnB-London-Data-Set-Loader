import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * London Property Marketplace
 * 
 * Main Stage containing all GUI functionalities.
 * The user can navigate through different panels to discover properties and their statistics,
 * however a price range must be selected initially.
 * 
 * @author Fahim Ahmed K1921959,
 * Issa Kabir K19014844,
 * Jehan Bhuyan K19017993,
 * Ork Hossain Muntaqin  K19016476
 */
public class View extends Application {

	private Stage stage;
	
	private BorderPane root;
	
	private AirbnbDataLoader airbnbDataLoader;
	
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
	
	private Stage panel3;
	
	public View() {
		airbnbDataLoader = new AirbnbDataLoader();
        airbnbDataLoader.load("airbnb-london.csv");
		
		isSelected = new boolean[2];
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		BorderPane root = new BorderPane();
		this.root = root;
		
		// Price range drop-down
		Pane priceRangeBox = createPriceRangeBox();
		root.setTop(priceRangeBox);
		
		panels = new ArrayList<>();
		panels.add(createPanel1());
		panels.add(null);
		panels.add(null);
		
		// Main content Pane.
		root.setCenter(panels.get(0));
		
		// Navigation buttons
		Pane navigationBox = createNavigationBox();
		root.setBottom(navigationBox);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("style.css");
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("London Property Marketlace");

		stage.show();
	}

	/**
	 * Creates the drop-down menu from which a price range can be specified.
	 * This price range is used to prepare panel 2 and 3.
	 */
	private Pane createPriceRangeBox() {
		HBox priceRangeBox = new HBox();
		priceRangeBox.setId("price-range-box");
		
		int maxPrice = ((airbnbDataLoader.getPriceStatistics().getMaxValue() + 99) / 100) * 100;
		int minPrice = airbnbDataLoader.getPriceStatistics().getMinValue() / 100 * 100;
		
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
		return priceRangeBox;
	}
	
	/**
	 * Creates the forward and back button that allow to navigate through the next and previous panel.
	 */
	private Pane createNavigationBox() {
		HBox navigationBox = new HBox();
		navigationBox.setId("navigation-box");
		
		backButton = new Button("<");
		backButton.setOnAction(this::previousPanel);
		backButton.setDisable(true);
		Pane space = new Pane();
		forwardButton = new Button(">");
		forwardButton.setOnAction(this::nextPanel);
		forwardButton.setDisable(true);
		
		backButton.getStyleClass().add("navigation-button");
		forwardButton.getStyleClass().add("navigation-button");
		
		HBox.setHgrow(space, Priority.ALWAYS);
		navigationBox.getChildren().addAll(backButton, space, forwardButton);
		return navigationBox;
	}
	
	/**
	 * Creates panel 1 which contains a welcoming messaging that informs the
	 * user on how to use the program.
	 * Upon the selection of a starting and ending price, the values are displayed
	 * to allow the user to confirm their choice.
	 */
	private Pane createPanel1() {
		BorderPane pane = new BorderPane();
		
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		
		FileInputStream inputstream = null;
		try {
			inputstream = new FileInputStream("images/background.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		Image image = new Image(inputstream); 
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(600);
		imageView.setPreserveRatio(true);
		
		vBox.getChildren().add(imageView);
		
		Label welcomeLabel = new Label("Welcome to London's Property MarketPlace.\r\n" +
				"Please enter a price range to start.\r\n" +
				"The next panel will contain a map displaying Boroughs in London which\r\n" +
				"have properties for rental within your selected price range.\r\n" +
				"- The border colour in each borough suggests the availibility of properties. \r\n" +
				"- Hover over a borough to see the number of properties. \r\n" +
				"- Click on a borough to view the properties in a table. \r\n" +
				"- Click a property row to view the description of that property.");
		welcomeLabel.setId("welcome-label");
		
		vBox.getChildren().add(welcomeLabel);
		
		HBox selectedRangeBox = new HBox();
		selectedRangeBox.setAlignment(Pos.CENTER);
		selectedRangeBox.setId("selected-range-box");
		
		fromPriceLabel = new Label("N/A");
		toPriceLabel = new Label("N/A");
		
		selectedRangeBox.getChildren().add(new Label("Selected price range:"));
		selectedRangeBox.getChildren().add(fromPriceLabel);
		selectedRangeBox.getChildren().add(new Label("-"));
		selectedRangeBox.getChildren().add(toPriceLabel);
		
		pane.setCenter(vBox);
		pane.setBottom(selectedRangeBox);
		return pane;
	}
	
	/**
	 * Creates panel 2 which contains a map of London with its boroughs.
	 * Only boroughs within the selected range can be interacted with while the others are disabled.
	 * For further information of the map visit the MapPane class.
	 */
	private Pane createPanel2() {
		Pane pane = new Pane();

		MapPane mapPane = new MapPane(controller.getBoroughs(), controller.getBoroughStatistics().getUpperQuartile(), controller.getBoroughStatistics().getLowerQuartile());
		
		pane.getChildren().add(mapPane);
		return pane;
	}

	/**
	 * Creates panel 3 which is a stage that contains several statistics related to the properties.
	 * This panel automatically appears as panel 2 is visited.
	 */
    private Stage createPanel3() {
    	Stage stage = new Stage();
    	
        Pane pane = new Pane();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setId("gridPane");

        //Setting vertical and horizontal spacing between each cell.
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        //Creating VBox's to display the statistics
        //The parameters accept one String, thus data is being concatinated
        VBox v1 = createVBox("Number of Properties\n\n"+ String.valueOf(controller.getAvailableProperties()));
        VBox v2 = createVBox("Total average reviews\n\n"+ String.valueOf(controller.getAverageReviews()));
        VBox v3 = createVBox("Total Number of Home/Apartments\n\n"+ String.valueOf(controller.getHomeApartments()));
        VBox v4 = createVBox("Most expensive borough\n\n"+ String.valueOf(controller.getMostExpensiveBorough().getName()));
        VBox v5 = createVBox("Cheapest borough\n\n"+ String.valueOf(controller.getCheapestBorough().getName()));
        VBox v6 = createVBox("Most expensive property description\n\n"+ String.valueOf(controller.getMostExpensiveDescription() + "\n\n Host Name: " + controller.getExpensiveHost()));
        VBox v7 = createVBox("Cheapest property description\n\n"+ String.valueOf(controller.getCheapestBoroughDescription()) + "\n\n Host Name: " + controller.getCheapestHost());
        VBox v8 = createVBox("Most reviewed borough\n\n"+ String.valueOf(controller.getMostReviewedBorough()));
        
        //Creating BorderPane and adding the VBox as parameter to change in button action
        BorderPane b1 = createBorderPane(v1, v3);
        BorderPane b2 = createBorderPane(v2, v8);
        BorderPane b3 = createBorderPane(v4, v5);
        BorderPane b4 = createBorderPane(v6, v7);

        //Laying the borderpane accordingly
        //4 section of BorderPane with 2 statistics each
        gridPane.add(b1, 0, 0);
        gridPane.add(b2, 0, 1);
        gridPane.add(b3, 1, 0);
        gridPane.add(b4, 1, 1);

        pane.getChildren().add(gridPane);
        pane.setMinSize(800, 400);
        
        Scene scene = new Scene(pane);
		scene.getStylesheets().add("style.css");
		
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Statistics");

        return stage;
    }

    private Pane createPanel4() {
        Pane pane = new Pane();
        pane.setId("panel-4");
        
        BorderPane borderPane = new BorderPane();
        
        Label label = new Label("Enter your location:");
        TextField text = new TextField();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(label, text);

        Label label1 = new Label("Get direction to the cheapest property:");
        Button btn1 = new Button("Go");
        btn1.setOnAction(e -> viewOnMapsButton(text.getText(), controller.getLatitudeOfCheapProperty(), controller.getLongitudeOfCheapProperty()));

        Label label2 = new Label("Get direction to the most expensive property:");
        Button btn2 = new Button("Go");
        btn2.setOnAction(e -> viewOnMapsButton(text.getText(), controller.getLatitudeOfExpensiveProperty(), controller.getLongitudeOfExpensiveProperty()));

        VBox buttonVbox = new VBox();
        vbox.getChildren().addAll(label1, btn1, label2, btn2);

        borderPane.setTop(vbox);
        borderPane.setLeft(buttonVbox);

        pane.getChildren().addAll(borderPane);
        return pane;
    }

    private void viewOnMapsButton(String direction, double latitude, double longitude){
        try{
            direction = direction.replace(" ",",");
            viewMap(direction, latitude, longitude);
        }
        catch(Exception e){
            System.out.println("URL INVALID");
        }
    }

    private void viewMap(String direction, double latitude, double longitude) throws Exception
    {
        URI uri = new URI("https://www.google.com/maps/dir/" + direction + "/" + latitude + "," + longitude);
        java.awt.Desktop.getDesktop().browse(uri); 
    }

    /**
     * Creating VBox and help with less code duplication
     * @param a concatinated String of the statistics title and the actual value
     * @return VBox which is then used in panel3 as statistics view
     */
    public VBox createVBox(String Context){
        VBox centerView = new VBox();
        Label statistics = new Label(Context);
        statistics.setPrefSize(315, 200);
        centerView.getChildren().add(statistics);
        centerView.setId("statistic-v-box");
        return centerView;
    }

    /**
     * Creating BorderPane method for panel3 to reduce duplication
     * @param VBoxs 1st VBox for the current and 2nd VBox for the previous statistics
     * @return BorderPane which is then used in panel3 as statistics view
     */
    public BorderPane createBorderPane(VBox v1, VBox v2){
        BorderPane layout = new BorderPane();
        layout.setCenter(v1);
        
        Button next = new Button(">");  //Next button
        next.setDisable(false);
        next.setMinSize(50, 200);
        layout.setRight(next);
        next.setOnAction(e -> layout.setCenter(v2));    //Changing VBox to next VBox
        Button back = new Button("<");  //Back button
        back.setMinSize(50, 200);
        layout.setLeft(back);
        back.setOnAction(e -> layout.setCenter(v1));    //Changing VBox to previous VBox

        layout.setId("statistic-border-pane");
        return layout;
    }
	
    /**
     * Allows the user to visit the next panel.
     */
	private void nextPanel(ActionEvent event) {
		panelIndex = (panelIndex + 1 ) % panels.size();
		changePanel();
	}
	
	/**
     * Allows the user to visit the previous panel.
     */
	private void previousPanel(ActionEvent event) {
		panelIndex = (panels.size() + panelIndex - 1) % panels.size();
		changePanel();
	}
	
	/**
	 * Changes the current panel with another one at the center of the program.
	 */
	private void changePanel() {
		root.setCenter(panels.get(panelIndex));
		
		// Displays panel 3 whenever panel 2 is viewed
		if (panelIndex == 1) {
			panel3.show();
		} else {
			panel3.hide();
		}
		
		stage.sizeToScene();
	}
	
	/**
	 * Validates the selected starting and ending price when both of them have been selected.
	 * A starting price bigger than or equale to the ending price is considered invalid and will display
	 * an error dialog.
	 * If the prices are valid, the forward and back button are enabled, allowing the user to progress.
	 */
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
	
	/**
	 * Prepares panel 2 and panel 3.
	 * Once a valid pair of prices are selected statitics are calculated
	 * and the map is built.
	 */
	private void processSelectedPriceRange() {
		controller = new Controller(airbnbDataLoader.getProperties());
		controller.setStartPrice(fromChoice.getValue());
		controller.setEndPrice(toChoice.getValue());
		controller.processRange();
		panels.set(1, createPanel2());
		panels.set(2, createPanel4());
		
		if (panel3 != null) panel3.hide();
		panel3 = createPanel3();
		changePanel();
	}
	
	/**
	 * Pops up a warning dialog.
	 */
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