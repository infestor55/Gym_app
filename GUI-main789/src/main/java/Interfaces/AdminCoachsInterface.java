package Interfaces;

import Models.Coach;
import Models.Users;
import Services.ServiceCoach;
import Services.ServiceUsers;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AdminCoachsInterface extends Application {
    private final ServiceCoach serviceCoach = new ServiceCoach();
    private final ServiceUsers serviceUser = new ServiceUsers();
    private final ObservableList<Coach> coachList = FXCollections.observableArrayList();
    private final TableView<Coach> coachTable = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        // Set up the table columns
        TableColumn<Coach, String> userNameColumn = new TableColumn<>("Username");
        userNameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameDBProperty());

        TableColumn<Coach, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().userEmailDBProperty());

        TableColumn<Coach, String> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getSalary())));

        TableColumn<Coach, String> sportColumn = new TableColumn<>("Sport");
        sportColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSport()));

        TableColumn<Coach, String> scheduleColumn = new TableColumn<>("Schedule");
        scheduleColumn.setCellValueFactory(cellData -> {
            Date schedule = cellData.getValue().getSchedule();
            return new SimpleStringProperty(schedule != null ? schedule.toString() : "");
        });

        // Add columns to the table
        coachTable.getColumns().addAll(userNameColumn, emailColumn, salaryColumn, sportColumn, scheduleColumn);

        // Load data from the database
        loadCoachData();

        // Create buttons for CRUD operations
        Button addButton = new Button("Add Coach");
        addButton.setOnAction(event -> addCoach());

        Button deleteButton = new Button("Delete Coach");
        deleteButton.setOnAction(event -> deleteCoach());

        Button updateButton = new Button("Update Coach");
        updateButton.setOnAction(event -> updateCoach());

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            primaryStage.close();
            try {
                new AdminInterface().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Add buttons to a VBox
        VBox buttonBox = new VBox(10, addButton, deleteButton, updateButton, backButton);

        // Create salary chart
        LineChart<String, Number> salaryChart = createSalaryChart();

        // Add the table, buttons, and salary chart to the root layout
        VBox root = new VBox(10, coachTable, buttonBox, salaryChart);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);

        // Set up the stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Coach Interface");
        primaryStage.show();
    }

    private void loadCoachData() {
        try {
            List<Coach> coaches = serviceCoach.readAll();
            coachList.addAll(coaches);
            coachTable.setItems(coachList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCoach() {
        Dialog<Coach> dialog = new Dialog<>();
        dialog.setTitle("Add Coach");
        dialog.setHeaderText("Enter Coach Details");

        // Set up the buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Set up the fields
        TextField userNameField = new TextField();
        userNameField.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");
        TextField sportField = new TextField();
        sportField.setPromptText("Sport");
        DatePicker dateField = new DatePicker(); // Date field

        // Add fields to the dialog pane
        dialog.getDialogPane().setContent(new VBox(10, userNameField, emailField, salaryField, sportField, dateField));

        // Convert the result to a Coach object when the add button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                LocalDate date = dateField.getValue(); // Get the selected date
                Instant instant = date.atStartOfDay(ZoneId.systemDefault()).toInstant(); // Convert LocalDate to Instant
                Date coachDate = Date.from(instant); // Convert Instant to Date
                new Users(userNameField.getText(), emailField.getText());
                return new Coach(userNameField.getText(), emailField.getText(), Double.parseDouble(salaryField.getText()), sportField.getText(), coachDate);
            }
            return null;
        });

        // Show the dialog
        Optional<Coach> result = dialog.showAndWait();

        // Add the coach if the result is present
        result.ifPresent(coach -> {
            try {
                serviceUser.add(coach);
                serviceCoach.add(coach);
                coachList.add(coach);
                reloadSalaryChart();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void deleteCoach() {
        Coach selectedCoach = coachTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Coach");
            alert.setHeaderText("Are you sure you want to delete this coach?");
            alert.setContentText(selectedCoach.getUserNameDB() + " - " + selectedCoach.getUserEmailDB());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    serviceCoach.delete(selectedCoach);
                    coachList.remove(selectedCoach);
                    reloadSalaryChart();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Coach Selected");
            alert.setContentText("Please select a coach from the table.");
            alert.showAndWait();
        }
    }

    private void updateCoach() {
        Coach selectedCoach = coachTable.getSelectionModel().getSelectedItem();
        if (selectedCoach != null) {
            Dialog<Coach> dialog = new Dialog<>();
            dialog.setTitle("Update Coach");
            dialog.setHeaderText("Update Coach Details");

            // Set up the buttons
            ButtonType updateButton = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

            // Set up the fields
            TextField emailField = new TextField(selectedCoach.getUserEmailDB());
            emailField.setPromptText("Email");
            TextField salaryField = new TextField(String.valueOf(selectedCoach.getSalary()));
            salaryField.setPromptText("Salary");
            TextField sportField = new TextField(selectedCoach.getSport());
            sportField.setPromptText("Sport");

            // Add fields to the dialog pane
            dialog.getDialogPane().setContent(new VBox(10, emailField, salaryField, sportField));

            // Convert the result to a Coach object when the update button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == updateButton) {
                    selectedCoach.setUserEmailDB(emailField.getText());
                    selectedCoach.setSalary(Double.parseDouble(salaryField.getText()));
                    selectedCoach.setSport(sportField.getText());
                    return selectedCoach;
                }
                return null;
            });

            // Show the dialog
            Optional<Coach> result = dialog.showAndWait();

            // Update the coach if the result is present
            result.ifPresent(coach -> {
                try {
                    serviceCoach.update(coach);
                    reloadSalaryChart();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Coach Selected");
            alert.setContentText("Please select a coach from the table.");
            alert.showAndWait();
        }
    }

    private LineChart<String, Number> createSalaryChart() {
        // Define axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();

        // Create the line chart
        LineChart<String, Number> salaryChart = new LineChart<>(xAxis, yAxis);
        salaryChart.setTitle("Salary Chart");
        xAxis.setLabel("Coach");
        yAxis.setLabel("Salary");

        // Prepare data series
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Salary");

        // Add data to series
        for (Coach coach : coachList) {
            series.getData().add(new XYChart.Data<>(coach.getUserNameDB(), coach.getSalary()));
        }

        // Add series to chart
        salaryChart.getData().add(series);

        return salaryChart;
    }

    private void reloadSalaryChart() {
        LineChart<String, Number> salaryChart = createSalaryChart();
        VBox root = (VBox) coachTable.getParent();
        root.getChildren().remove(root.getChildren().size() - 1); // Remove the last child (which is the current chart)
        root.getChildren().add(salaryChart); // Add the updated chart
    }

    public static void main(String[] args) {
        launch(args);
    }
}
