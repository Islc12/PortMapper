// Collections (For storing pressed buttons)
import java.util.ArrayList;
// Application and Stage Management
import javafx.application.Application;
import javafx.stage.Stage;
// Layout Containers (For structuring your grid of buttons and controls)
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets; 
import javafx.geometry.Pos;
// UI Controls (For buttons and labels)
import javafx.scene.control.Button;
import javafx.scene.control.Label;
// Scene and Nodes (For handling the graphical interface)
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.control.TextField; 
// Event Handling (For detecting button presses)
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
// Styling and Effects (For color changes when buttons are pressed)
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
// Threading (For running nmap commands asynchronously)
import javafx.concurrent.Task;
import java.lang.ProcessBuilder;
import java.nio.Buffer;
// Used for printing information to the terminal for debugging purposes
import java.io.BufferedReader;
import java.io.InputStreamReader;

// REMINDER USE THIS TO COMPILE
// javac --module-path lib --add-modules javafx.controls,javafx.fxml portmapper.java
// REMINDER USE THIS TO RUN
// java --module-path lib --add-modules javafx.controls,javafx.fxml portmapper

public class portmapper extends Application {
    private VBox mainLayout;
    private GridPane grid;
    private Stage mainStage;
    private TextField ipInputField;
    private ArrayList<Button> buttons = new ArrayList<Button>(); // Added as a reminder to assign buttons to the arraylist
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    public void start(Stage mainStage) throws Exception {
        mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-background-color: #272727");
        gridTable();
        controlButtons();
        ipInputField();

        Scene scene = new Scene(mainLayout); 
        scene.setFill(Color.web("272727")); // Using Color.web() allows you to pass a hex value for a color as an arg rather than Color.<COLOR> otherwise
        
        // Stage Management
        mainStage.setTitle("Port Mapper v1.0");
        mainStage.setWidth(1900); // Comfortable stage width without dealing with the stage hanging off the edge of the screen
        mainStage.setHeight(1000);
        mainStage.setResizable(false); // Prevents user resizing, may be able to change after I see how the table works out
        mainStage.setX(8); // Centers the stage by default in the center of my screen
        Image icon = new Image("Extra/arrow_s.png");
        mainStage.getIcons().add(icon);
        
        mainStage.setScene(scene);
        mainStage.show();
    }

    private void gridTable() {
        grid = new GridPane(2.5,2.5);
        // Create a grid of buttons
        grid.setPrefSize(1600, 800);
        // grid.setGridLinesVisible(true); // Used for debugging the grid
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #272727;");

        // Create all the buttons in the 128x32 table
        for (int row = 0; row < 32; ++row) {
            for (int col = 0; col < 128; ++col) {
                Button button = new Button();
                button.setPrefSize(11, 11);
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setStyle("-fx-background-radius: 50%; "
                                + "-fx-min-width: 1px; -fx-min-height: 1px; -fx-padding: 0; "
                                + "-fx-background-color: #8080FF; -fx-boarder-color: #FFFFFF");
                button.setOnAction(__ -> {
                    if (button.getStyle().contains("#8080FF")) {
                        button.setStyle("-fx-background-radius: 50%; "
                                + "-fx-min-width: 1px; -fx-min-height: 1px; -fx-padding: 0; "
                                + "-fx-background-color: #FF0000; -fx-boarder-color: #FFFFFF");
                    }
                    else {
                        button.setStyle("-fx-background-radius: 50%; "
                                + "-fx-min-width: 1px; -fx-min-height: 1px; -fx-padding: 0; "
                                + "-fx-background-color: #8080FF; -fx-boarder-color: #FFFFFF");
                    }
                });


                button.setOnDragDetected(e -> {
                    button.startFullDrag();
                });

                button.setOnMouseDragEntered(e -> {
                    if (button.getStyle().contains("#8080FF")) {
                        button.setStyle("-fx-background-radius: 50%; "
                                        + "-fx-min-width: 1px; -fx-min-height: 1px; -fx-padding: 0; "
                                        + "-fx-background-color: #FF0000; -fx-boarder-color: #FFFFFF");;
                    }
                });
                int portNumber = (row * 128) + col + 1;
                buttons.add(button);
                grid.add(button, col, row);
            }
        }

        mainLayout.getChildren().add(grid);
    }

    private void controlButtons() {
        HBox buttonContainer = new HBox(5);

        Button resetButton = new Button("Reset");
        resetButton.setPrefSize(100, 25);
        resetButton.setStyle("-fx-background-color:rgb(24, 179, 36); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0); "
                             + "-fx-border-radius: 10px; -fx-background-radius: 10px");
        Button startButton = new Button("Start Scan");
        startButton.setPrefSize(100, 25);
        startButton.setStyle("-fx-background-color:rgb(24, 179, 36); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0); "
                             + "-fx-border-radius: 10px; -fx-background-radius: 10px");
        startButton.setOnAction(__ -> {
            nmapScan(); // Start the scan when the button is clicked
        });

        resetButton.setOnAction(__ -> {
            // Reset the button colors to their default state
            for (Button button : buttons) {
                button.setStyle("-fx-background-radius: 50%; "
                                + "-fx-min-width: 1px; -fx-min-height: 1px; -fx-padding: 0; "
                                + "-fx-background-color: #8080FF; -fx-boarder-color: #FFFFFF");
            }
        });

        buttonContainer.setAlignment(Pos.BOTTOM_RIGHT);
        buttonContainer.setPadding(new Insets(20,20,20,20));

        buttonContainer.getChildren().addAll(resetButton, startButton);

        mainLayout.getChildren().addAll(buttonContainer);
    }

    private void ipInputField() {
        // Create a text field for the target IP address
        TextField ipInputField = new TextField();
        ipInputField.setPromptText("Enter IP address");
        ipInputField.setPrefWidth(200);
        ipInputField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000;"
                                + "-fx-border-color: #000000; -fx-border-radius: 5px; -fx-padding: 5px;");
        mainLayout.getChildren().add(ipInputField);
    }

    private void nmapScan() {
        Task<Void> nmapScanTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < buttons.size(); i++) {
                    Button button = buttons.get(i);
                    if (button.getStyle().contains("#FF0000")) {
                        int portNumber = i + 1;
                        ProcessBuilder pb = new ProcessBuilder("nmap", "-p", String.valueOf(portNumber), "-T5", ipInputField.getText());
                        // Will need to go back and add an input field for the IP address
                    Process process = pb.start();
                    System.out.println(process);

                    // Prints the output of the process to the terminal window; used for debugging
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }

                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    while ((line = errorReader.readLine()) != null) {
                        System.out.println("Error: " + line);
                    }

                    process.waitFor();
                    }
                }
                return null;
            }
        };
        Thread scanThread = new Thread(nmapScanTask);
        scanThread.start();
    }
}
