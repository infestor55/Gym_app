package Interfaces;

import Models.Member;
import Controllers.MemberController;
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
import javafx.stage.Stage;

import java.sql.SQLException;

public class MemberInterface extends Application {

    private MemberController memberController;

    private TextField userNameField;
    private TextField emailField;
    private TextField membershipField;
    private TextField ageField;
    private TextField heightField;
    private TextField weightField;
    private DatePicker schedulePicker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        memberController = new MemberController();
        primaryStage.setTitle("Member Management");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER); // Center the grid content

        // Set background image
        Image backgroundImage = new Image("file:src/images/Gymwallpaper.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        grid.setBackground(new Background(background));

        Label nameLabel = createLabel("Username:");
        userNameField = createTextField();
        Label emailLabel = createLabel("Email:");
        emailField = createTextField();
        Label membershipLabel = createLabel("Membership:");
        membershipField = createTextField();
        Label ageLabel = createLabel("Age:");
        ageField = createTextField();
        Label heightLabel = createLabel("Height:");
        heightField = createTextField();
        Label weightLabel = createLabel("Weight:");
        weightField = createTextField();
        Label scheduleLabel = createLabel("Schedule:");
        schedulePicker = new DatePicker();

        Button addButton = createButton("Add");
        addButton.setOnAction(e -> {
            try {
                addMember();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button backButton = createButton("Back");
        backButton.setOnAction(e -> {
            // Add action for back button
            primaryStage.close(); // Close the current window
            WelcomeInterface welcomeInterface = new WelcomeInterface(); // Open WelcomeInterface
            welcomeInterface.start(new Stage());
        });

        // Set alignment for all nodes
        GridPane.setHalignment(nameLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(emailLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(membershipLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(ageLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(heightLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(weightLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(scheduleLabel, Pos.CENTER.getHpos());
        GridPane.setHalignment(addButton, Pos.CENTER.getHpos());
        GridPane.setHalignment(backButton, Pos.CENTER.getHpos());

        grid.addRow(0, nameLabel, userNameField);
        grid.addRow(1, emailLabel, emailField);
        grid.addRow(2, membershipLabel, membershipField);
        grid.addRow(3, ageLabel, ageField);
        grid.addRow(4, heightLabel, heightField);
        grid.addRow(5, weightLabel, weightField);
        grid.addRow(6, scheduleLabel, schedulePicker);
        grid.addRow(7, addButton, backButton);
        GridPane.setHalignment(addButton, Pos.CENTER.getHpos());
        GridPane.setHalignment(backButton, Pos.CENTER.getHpos());

        // Scene Setup
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        label.setTextFill(Color.SILVER); // Set text color
        return label;
    }

    private TextField createTextField() {
        TextField textField = new TextField();
        textField.setPromptText("Enter value");
        textField.setPrefWidth(500); // Limit the maximum width of text field
        textField.setPrefHeight(20); // Set the preferred height of the text field
        textField.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;"); // Set text field style
        return textField;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(150, 40);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        return button;
    }

    private void addMember() throws SQLException {
        String username = userNameField.getText();
        String email = emailField.getText();
        String membership = membershipField.getText();
        int age = Integer.parseInt(ageField.getText());
        double height = Double.parseDouble(heightField.getText());
        double weight = Double.parseDouble(weightField.getText());
        java.sql.Date schedule = java.sql.Date.valueOf(schedulePicker.getValue());

        Member newMember = new Member(username, email, membership, age, height, weight, schedule, null);

        memberController.addMember(newMember);
        clearFields();
    }

    private void clearFields() {
        userNameField.clear();
        emailField.clear();
        membershipField.clear();
        ageField.clear();
        heightField.clear();
        weightField.clear();
        schedulePicker.getEditor().clear();
    }
}
