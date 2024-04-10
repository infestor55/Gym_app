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
import javafx.stage.Stage;

public class ProfileInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Profile - Sports Application");

        // Background Image
        Image backgroundImage = new Image("file:src/images/Gymwallpaper.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background backgroundStyle = new Background(background);

        // Logo Image
        Image logoImage = new Image("file:logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200);
        logoImageView.setPreserveRatio(true);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Sporti'Z");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        welcomeText.setFill(Color.WHITE);

        // Description Text
        Text descriptionText = new Text("“Train like an athlete, eat like a nutritionist, sleep like a baby, win like a champion”");
        descriptionText.setFont(Font.font("Arial", 20));
        descriptionText.setFill(Color.WHITE);
        descriptionText.setWrappingWidth(400);
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        // Buttons
        Button profileButton = createButton("My Profile");
        Button updateProfileButton = createButton("Update Profile");
        Button backButton = createButton("Back"); // New Back Button
        Button logoutButton = createButton("Logout");

        // Layout Setup
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(backgroundStyle);
        layout.getChildren().addAll(logoImageView, welcomeText, descriptionText, profileButton, updateProfileButton, backButton, logoutButton); // Add backButton
        layout.setPadding(new Insets(50));

        // Scene Setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button Actions
        profileButton.setOnAction(e -> {
            // Add action for profile button
        });
        updateProfileButton.setOnAction(e -> {
            // Add action for update profile button
        });
        backButton.setOnAction(e -> {
            // Add action for back button
            primaryStage.close(); // Close the current window
            FirstInterface firstInterface = new FirstInterface(); // Open FirstInterface
            firstInterface.start(new Stage());
        });
        logoutButton.setOnAction(e -> {
            // Add action for logout button
            primaryStage.close(); // Close the current window
            SignInInterface signInInterface = new SignInInterface(); // Open SignInInterface
            signInInterface.start(new Stage());
        });
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #af4c6f; -fx-text-fill: white;");
        button.setPrefSize(200, 40);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return button;
    }
}
