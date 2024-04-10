package Interfaces;

import Models.Users;
import Services.ServiceUsers;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.SQLException;

public class SignUpInterface extends Application {

    private ServiceUsers serviceUsers;

    private ObservableList<Users> userList;

    private TextField userNameField;
    private TextField emailField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        serviceUsers = new ServiceUsers();

        primaryStage.setTitle("User Management");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Sporti'Z");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        welcomeText.setFill(Color.BLACK);
        welcomeText.setWrappingWidth(400);
        welcomeText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Description Text
        Text descriptionText = new Text("Register now *__*");
        descriptionText.setFont(Font.font("Arial", 15));
        descriptionText.setFill(Color.BLACK);
        descriptionText.setWrappingWidth(400);
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Label nameLabel = new Label("Username:");

        userNameField = new TextField();
        userNameField.setPromptText("Enter username");

        Label emailLabel = new Label("Email:");

        emailField = new TextField();
        emailField.setPromptText("Enter email");

        Button addButton = new Button("Sign up");
        addButton.setOnAction(e -> addUser());

        Button refreshButton = new Button("Back");
        refreshButton.setOnAction(e -> openInterface(new WelcomeInterface(), primaryStage));

        TableView<Users> table = new TableView<>();
        userList = FXCollections.observableArrayList();
        table.setItems(userList);

        TableColumn<Users, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameDBProperty());
        table.getColumns().add(nameColumn);

        TableColumn<Users, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().userEmailDBProperty());
        table.getColumns().add(emailColumn);


        grid.getChildren().addAll(welcomeText, descriptionText, nameLabel, userNameField, emailLabel, emailField, addButton, refreshButton);
        // grid.getChildren().addAll(nameLabel, userNameField, emailLabel, emailField, addButton, table);

        // Set constraints for welcomeText
        GridPane.setColumnSpan(welcomeText, 2);
        GridPane.setConstraints(welcomeText, 0, 0);

        // Set constraints for descriptionText
        GridPane.setColumnSpan(descriptionText, 2);
        GridPane.setConstraints(descriptionText, 0, 2);

        // Set constraints for nameLabel
        GridPane.setConstraints(nameLabel, 0, 5);

        // Set constraints for userNameField
        GridPane.setConstraints(userNameField, 1, 5);

        // Set constraints for emailLabel
        GridPane.setConstraints(emailLabel, 0, 6);

        // Set constraints for emailField
        GridPane.setConstraints(emailField, 1, 6);

        // Set constraints for addButton
        GridPane.setColumnSpan(addButton, 2);
        GridPane.setConstraints(addButton, 0, 9);

        // Set constraints for refreshButton
        GridPane.setColumnSpan(refreshButton, 2);
        GridPane.setConstraints(refreshButton, 1, 9);

        // Set constraints for table
        GridPane.setColumnSpan(table, 2);
        GridPane.setConstraints(table, 0, 12);


        // Scene Setup
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial load of users
        // refreshUsers();
    }

    private void openInterface(Application interfaceClass, Stage primaryStage) {
        try {
            interfaceClass.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        primaryStage.close();
    }

    private void addUser() {
        String username = userNameField.getText();
        String email = emailField.getText();

        Users newUser = new Users(username, email);

        try {
            serviceUsers.add(newUser);
            userList.add(newUser);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding user", e.getMessage());
        }
    }

    private void refreshUsers() {
        userList.clear();
        try {
            userList.addAll(serviceUsers.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching users", e.getMessage());
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

