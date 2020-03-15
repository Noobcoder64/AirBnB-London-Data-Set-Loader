import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

/**
 * Write a description of class Template here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Template extends BorderPane
{
    private BorderPane layout = new BorderPane();
    
    /**
     * Constructor for objects of class Template
     */
    public Template(String title, String Context)
    {
        
        Button next = new Button(">");
        layout.setRight(next);
        
        Button back = new Button("<");
        layout.setLeft(back);
        
        VBox centerView = new VBox();
        Label LabelTitle = new Label(title);
        Label statistics = new Label(Context);
        centerView.getChildren().addAll(LabelTitle, statistics);
        layout.setCenter(centerView);
        seeBorderPane();
    }
    
    public BorderPane seeBorderPane(){
        return layout;
    }
}
