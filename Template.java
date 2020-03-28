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
    public Template(VBox v1, VBox v2, VBox v3)
    {
        layout.setCenter(v1);
        
        Button next = new Button(">");
        layout.setRight(next);
        next.setOnAction(e -> layout.setCenter(v2));
        
        Button back = new Button("<");
        layout.setLeft(back);
        back.setOnAction(e -> layout.setCenter(v3));
                
        seeBorderPane();
    }
        
    public BorderPane seeBorderPane(){
        return layout;
    }
}
