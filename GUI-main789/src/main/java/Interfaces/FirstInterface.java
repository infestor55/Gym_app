package Interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class FirstInterface extends Application {
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("User Interface");

        BorderPane borderPane = new BorderPane();

        // Set the top of the border pane
        borderPane.setTop(createHeader());

        // Set the center of the border pane
        borderPane.setCenter(createCenterPane());

        // Set the border pane to the scene
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TilePane createHeader() {
        TilePane tilePane = new TilePane();
        tilePane.setPadding(new Insets(10));
        tilePane.setAlignment(Pos.CENTER);
        tilePane.setStyle("-fx-background-color: black;");
        tilePane.setHgap(20); // Horizontal gap between buttons
        tilePane.setVgap(10); // Vertical gap between buttons

        Button profileButton = createButton("PROFILE");
        Button eventsButton = createButton("EVENTS");
        Button statisticsButton = createButton("STATISTICS");
        Button sportsButton = createButton("SPORTS");

        // Add event handler to Profile button
        profileButton.setOnAction(e -> {
            // Close the current window
            primaryStage.close();
            // Open the ProfileInterface
            ProfileInterface profileInterface = new ProfileInterface();
            profileInterface.start(new Stage());
        });

        tilePane.getChildren().addAll(profileButton, eventsButton, statisticsButton, sportsButton);

        return tilePane;
    }


    private Button createButton(String text) {
        Button button = new Button(text);
        button.setTextFill(Color.WHITE);
        button.setStyle("-fx-background-color: #af4c6f; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 0 10px;");
        button.setPrefWidth(120);
        button.setPrefHeight(40);
        button.setOnAction(event -> {
            // Add action for button click
        });
        return button;
    }

    private StackPane createCenterPane() {
        Image image = new Image("file:src/images/Gymwallpaper.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        StackPane centerPane = new StackPane();
        centerPane.getChildren().add(imageView);

        // Create a StackPane for the welcome text
        StackPane welcomePane = new StackPane();
        welcomePane.setAlignment(Pos.CENTER); // Align the welcome text to the bottom center

        // Welcome Text
        Text welcomeText = new Text("Welcome to Sporti'Z");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        welcomePane.getChildren().add(welcomeText);

        // Bind the size of the welcome pane to the size of the center pane
        welcomePane.prefWidthProperty().bind(centerPane.widthProperty());
        welcomePane.prefHeightProperty().bind(centerPane.heightProperty());

        // Add the welcome pane to the center pane
        centerPane.getChildren().add(welcomePane);

        return centerPane;
    }

}
