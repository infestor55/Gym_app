package Interfaces;

import Models.Users;
import Services.ServiceUsers;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignInInterface extends Application {

    private ServiceUsers serviceUsers;

    private TextField userNameField;
    private TextField emailField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        serviceUsers = new ServiceUsers();

        primaryStage.setTitle("Sign In");

        // Background Image
        Image backgroundImage = new Image("file:src/images/Gymwallpaper.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background backgroundStyle = new Background(background);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(backgroundStyle);
        layout.setPadding(new Insets(20));

        // Welcome Text
        Text welcomeText = new Text("Welcome back to Sporti'Z");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeText.setFill(Color.SILVER);

        // Label and TextFields
        Label nameLabel = new Label("Username:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set font size and weight
        nameLabel.setTextFill(Color.SILVER); // Set label color to silver

        userNameField = new TextField();
        userNameField.setPromptText("Enter username");
        userNameField.setMaxWidth(400); // Limit the maximum width of text field
        userNameField.setPrefHeight(40); // Set the preferred height of the text field

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set font size and weight
        emailLabel.setTextFill(Color.SILVER); // Set label color to silver

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        emailField.setMaxWidth(400); // Limit the maximum width of text field
        emailField.setPrefHeight(40); // Set the preferred height of the text field

        Button addButton = createButton("Sign In");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"); // Green color
        addButton.setOnAction(e -> addUser(primaryStage));

        Button refreshButton = createButton("Back");
        refreshButton.setStyle("-fx-background-color: #1565c0; -fx-text-fill: white;"); // Blue color
        refreshButton.setOnAction(e -> openInterface(new WelcomeInterface(), primaryStage));

        // HBox for buttons
        HBox buttonBox = new HBox(10); // Spacing between buttons
        buttonBox.setAlignment(Pos.CENTER); // Center align buttons
        buttonBox.getChildren().addAll(addButton, refreshButton);

        // Add nodes to layout
        layout.getChildren().addAll(welcomeText, nameLabel, userNameField, emailLabel, emailField, buttonBox);

        // Scene Setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(150, 30);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return button;
    }

    private void openInterface(Application interfaceClass, Stage primaryStage) {
        try {
            interfaceClass.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        primaryStage.close();
    }

    private void addUser(Stage primaryStage) {
        String username = userNameField.getText();
        String email = emailField.getText();

        Users newUser = new Users(username, email);

        primaryStage.setTitle("Sign In"); // Now, primaryStage is not null

        try {
            boolean verif = serviceUsers.verify(newUser);
            if (verif) {
                clearFields();
                openInterface(new FirstInterface(), primaryStage);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Username does not exist", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding user", e.getMessage());
        }
    }

    private void clearFields() {
        userNameField.clear();
        emailField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
