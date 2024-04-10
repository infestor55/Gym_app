package Interfaces;

import Models.Coach;
import Services.ServiceCoach;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class CoachInterface extends Application {

    private ServiceCoach serviceCoach;

    private ObservableList<Coach> coachList;

    private TextField userNameField;
    private TextField emailField;
    private TextField salaryField;
    private TextField sportField;
    private DatePicker schedulePicker;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        serviceCoach = new ServiceCoach();

        primaryStage.setTitle("Coach Management");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Username:");
        GridPane.setConstraints(nameLabel, 0, 0);

        userNameField = new TextField();
        userNameField.setPromptText("Enter username");
        GridPane.setConstraints(userNameField, 1, 0);

        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);

        emailField = new TextField();
        emailField.setPromptText("Enter email");
        GridPane.setConstraints(emailField, 1, 1);

        Label salaryLabel = new Label("Salary:");
        GridPane.setConstraints(salaryLabel, 0, 2);

        salaryField = new TextField();
        salaryField.setPromptText("Enter salary");
        GridPane.setConstraints(salaryField, 1, 2);

        Label sportLabel = new Label("Sport:");
        GridPane.setConstraints(sportLabel, 0, 3);

        sportField = new TextField();
        sportField.setPromptText("Enter sport");
        GridPane.setConstraints(sportField, 1, 3);

        Label scheduleLabel = new Label("Schedule:");
        GridPane.setConstraints(scheduleLabel, 0, 4);

        schedulePicker = new DatePicker();
        GridPane.setConstraints(schedulePicker, 1, 4);

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton, 1, 5);
        addButton.setOnAction(e -> addCoach());

        TableView<Coach> table = new TableView<>();
        coachList = FXCollections.observableArrayList();
        table.setItems(coachList);

        TableColumn<Coach, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameDBProperty());
        table.getColumns().add(nameColumn);

        TableColumn<Coach, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().userEmailDBProperty());
        table.getColumns().add(emailColumn);

        /*TableColumn<Coach, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
        table.getColumns().add(salaryColumn);

        TableColumn<Coach, String> sportColumn = new TableColumn<>("Sport");
        sportColumn.setCellValueFactory(cellData -> cellData.getValue().sportProperty());
        table.getColumns().add(sportColumn);

        TableColumn<Coach, String> scheduleColumn = new TableColumn<>("Schedule");
        scheduleColumn.setCellValueFactory(cellData -> cellData.getValue().scheduleProperty().asString());
        table.getColumns().add(scheduleColumn);*/

        GridPane.setConstraints(table, 0, 6, 2, 1);

        grid.getChildren().addAll(nameLabel, userNameField, emailLabel, emailField,
                salaryLabel, salaryField, sportLabel, sportField,
                scheduleLabel, schedulePicker, addButton, table);

        // Scene Setup
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial load of coaches
        refreshCoaches();
    }

    private void addCoach() {
        String username = userNameField.getText();
        String email = emailField.getText();
        double salary = Double.parseDouble(salaryField.getText());
        String sport = sportField.getText();
        // Convert LocalDate to java.sql.Date
        java.sql.Date schedule = java.sql.Date.valueOf(schedulePicker.getValue());

        Coach newCoach = new Coach(username, email, salary, sport, schedule);

        try {
            serviceCoach.add(newCoach);
            coachList.add(newCoach);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding coach", e.getMessage());
        }
    }

    private void refreshCoaches() {
        try {
            List<Coach> coaches = serviceCoach.readAll();
            if (coaches != null) {
                coachList.clear();
                coachList.addAll(coaches);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "No coaches found", "There are no coaches available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching coaches", e.getMessage());
        }
    }

    private void clearFields() {
        userNameField.clear();
        emailField.clear();
        salaryField.clear();
        sportField.clear();
        schedulePicker.getEditor().clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
