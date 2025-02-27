// Collections (For storing pressed buttons)
import java.util.ArrayList;
// Application and Stage Management
import javafx.application.Application;
import javafx.stage.Stage;
// Layout Containers (For structuring your grid of buttons and controls)
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
// UI Controls (For buttons and labels)
import javafx.scene.control.Button;
import javafx.scene.control.Label;
// Scene and Nodes (For handling the graphical interface)
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
// Event Handling (For detecting button presses)
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
// Styling and Effects (For color changes when buttons are pressed)
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
// Threading (For running nmap commands asynchronously)
import javafx.concurrent.Task;

// REMINDER USE THIS TO COMPILE
// javac --module-path lib --add-modules javafx.controls,javafx.fxml portmapper.java
// REMINDER USE THIS TO RUN
// java --module-path lib --add-modules javafx.controls,javafx.fxml portmapper

public class Main extends Application {
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage mainStage) throws Exception {
        
        Group root = new Group(); // Creates root node to run the scene
        Scene scene = new Scene(root,Color.web("272727")); // Using Color.web() allows you to pass a hex value for a color as an arg rather than Color.<COLOR> otherwise
        mainStage.setTitle("Port Mapper v1.0");
        mainStage.setWidth(1800); // Comfortable stage width without dealing with the stage hanging off the edge of the screen
        mainStage.setHeight(1000);
        mainStage.setResizable(false); // Prevents user resizing, may be able to change after I see how the table works out
        mainStage.setX(60); // Centers the stage by default in the center of my screen
        Image icon = new Image("Extra/arrow_s.png");
        mainStage.getIcons().add(icon);
        

        mainStage.setScene(scene);
        mainStage.show();
    }
}
