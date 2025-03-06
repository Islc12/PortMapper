/*
 * PortMapper: A program that maps ports to IP addresses using a GUI interface.
 * Copyright (C) 2025 Islc12
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */


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
import javafx.scene.control.TextField;
// Scene and Nodes (For handling the graphical interface)
import javafx.scene.Scene;
import javafx.scene.image.Image;
// Styling and Effects (For color changes when buttons are pressed)
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
// Threading (For running nmap commands asynchronously)
import javafx.concurrent.Task;
import java.lang.ProcessBuilder;
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
    private String targetIP;
    private Label instructionBox;
    private Label titleBox;
    private ArrayList<Button> buttons = new ArrayList<Button>(); // Added as a reminder to assign buttons to the arraylist
    
    public void start(Stage mainStage) throws Exception {
        mainLayout = new VBox(10);
        mainLayout.setStyle("-fx-background-color: #272727");
        titleBox();
        gridTable();
        instructionBox();
        controlButtons();

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
        grid = new GridPane(1.5, 1.5);
        // Create a grid of buttons
        grid.setPrefSize(1600, 500);
        // grid.setGridLinesVisible(true); // Used for debugging the grid
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #272727;");

        // Create all the buttons in the 128x32 table
        for (int row = 0; row < 32; ++row) {
            for (int col = 0; col < 128; ++col) {
                Button button = new Button();
                button.setPrefSize(12, 12);
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

    private void titleBox() {
        HBox titleContainer = new HBox(5);
        Label titleLabel = new Label("Port Mapper v1.0");
        titleLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 48px;");
        titleLabel.setPadding(new Insets(40, 0 , 0, 0));
        
        titleContainer.getChildren().add(titleLabel);
        titleContainer.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(titleContainer);
    }

    private void instructionBox() {
        HBox directionsContainer = new HBox(25);
        Label instructionLabel = new Label("To use the Port Mapper v1.0 simply enter the target IP address and then press the confirm IP button to confirm the IP. Afterwards simply click on the individual buttons to map out ports to scan or click and drag for fast selection. Afterwards click Scan Now to start scanning ports. Press the reset button to clear the board or click the open ports to individually close them.");
        instructionLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 18px;");
        instructionLabel.setWrapText(true);
        instructionLabel.setMaxWidth(1600);
        
        directionsContainer.getChildren().add(instructionLabel);
        directionsContainer.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(directionsContainer);
    }

    private void controlButtons() {
        HBox buttonContainer = new HBox(25);

        Button resetButton = new Button("Reset");
        resetButton.setPrefSize(100, 25);
        resetButton.setStyle("-fx-background-color:rgb(24, 179, 36); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0); "
                             + "-fx-border-radius: 10px; -fx-background-radius: 10px");
        Button startButton = new Button("Start Scan");
        startButton.setPrefSize(100, 25);
        startButton.setStyle("-fx-background-color:rgb(24, 179, 36); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0); "
                             + "-fx-border-radius: 10px; -fx-background-radius: 10px");
        startButton.setOnAction(__ -> {
            startButton.setStyle("-fx-background-color:rgb(255, 0, 0); -fx-border-color:rgb(0, 0, 0);"
                                + "-fx-text-fill:rgb(0, 0, 0); -fx-border-radius: 10px; -fx-background-radius: 10px");
            nmapScan(); // Start the scan when the button is clicked
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.25),
                e -> startButton.setStyle("-fx-background-color: rgb(24, 179, 36); -fx-border-color:rgb(0, 0, 0);"
                                    + "-fx-text-fill:rgb(0, 0, 0); -fx-border-radius: 10px; -fx-background-radius: 10px")
            ));
            timeline.play();
        });

        resetButton.setOnAction(__ -> {
            // Reset the button colors to their default state
            for (Button button : buttons) {
                button.setStyle("-fx-background-radius: 50%; "
                                + "-fx-min-width: 1px; -fx-min-height: 1px; -fx-padding: 0; "
                                + "-fx-background-color: #8080FF; -fx-boarder-color: #FFFFFF");
            }
            resetButton.setStyle("-fx-background-color:rgb(255, 0, 0); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0);"
                                + "-fx-border-radius: 10px; -fx-background-radius: 10px");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.25),
            e -> resetButton.setStyle("-fx-background-color: rgb(24, 179, 36); -fx-border-color: rgb(0, 0, 0); -fx-text-fill: rgb(0, 0, 0); "
                                    + "-fx-border-radius: 10px; -fx-background-radius: 10px")));
            timeline.play();
        });

        // Create a text field for the target IP address
        TextField ipInputField = new TextField();
        ipInputField.setPromptText("Enter IP address");
        ipInputField.setPrefWidth(150);
        ipInputField.setMaxWidth(150);
        ipInputField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: #000000;"
                                + "-fx-border-color: #000000; -fx-border-radius: 5px; -fx-padding: 5px;");
        

        // Create a submit button for the ip input
        Button submitButton = new Button("Confirm IP");
        submitButton.setPrefSize(100, 25);
        submitButton.setStyle("-fx-background-color:rgb(24, 179, 36); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0); "
                             + "-fx-border-radius: 10px; -fx-background-radius: 10px");
        submitButton.setOnAction(__ -> {
            this.targetIP = ipInputField.getText();
            submitButton.setStyle("-fx-background-color:rgb(255, 0, 0); -fx-border-color:rgb(0, 0, 0); -fx-text-fill:rgb(0, 0, 0);"
                                + "-fx-border-radius: 10px; -fx-background-radius: 10px");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(.25),
            e -> submitButton.setStyle("-fx-background-color: rgb(24, 179, 36); -fx-border-color: rgb(0, 0, 0); -fx-text-fill: rgb(0, 0, 0); "
                                        + "-fx-border-radius: 10px; -fx-background-radius: 10px")));
timeline.play();
        });

        buttonContainer.setAlignment(Pos.BOTTOM_CENTER);
        buttonContainer.setPadding(new Insets(20,20,20,20));

        buttonContainer.getChildren().addAll(ipInputField, submitButton, resetButton, startButton);

        mainLayout.getChildren().addAll(buttonContainer);
    }

    // Currently having issues with this function, as of right now its not picking up from the ipInputField, words with manual input of IP
    private void nmapScan() {
        Task<Void> nmapScanTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i < buttons.size(); i++) {
                    Button button = buttons.get(i);
                    // Check if there is an IP address set - used for debugging
                    if (targetIP == null || targetIP.isEmpty()) {
                        System.out.println("No target IP set");
                        return null;
                    }
                    // Gathers buttons that have been changed to red, and then scans the port of the IP address
                    if (button.getStyle().contains("#FF0000")) {
                        int portNumber = i + 1;
                        ProcessBuilder pb = new ProcessBuilder("nmap", "-p", String.valueOf(portNumber), "-T5", targetIP);
                        
                    Process process = pb.start();
                    
                    // Prints the output of the process to the terminal window; used for debugging
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    // Prints the error output of the process to the terminal window; used for debugging
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

    // Main method to launch the application
    public static void main(String[] args) {
        Application.launch(args);
    }
}
