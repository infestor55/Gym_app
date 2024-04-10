package Interfaces;

import Models.Responsible;
import Services.ServiceResponsible;
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

public class ResponsibleInterface extends Application {

    private ServiceResponsible serviceResponsible;

    private ObservableList<Responsible> responsibleList;

    private TextField userNameField;
    private TextField emailField;
    private TextField financeField;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        serviceResponsible = new ServiceResponsible();

        primaryStage.setTitle("Responsible Management");

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

        Label financeLabel = new Label("Finance:");
        GridPane.setConstraints(financeLabel, 0, 2);

        financeField = new TextField();
        financeField.setPromptText("Enter finance");
        GridPane.setConstraints(financeField, 1, 2);

        Button addButton = new Button("Add");
        GridPane.setConstraints(addButton, 1, 3);
        addButton.setOnAction(e -> addResponsible());

        TableView<Responsible> table = new TableView<>();
        responsibleList = FXCollections.observableArrayList();
        table.setItems(responsibleList);

        TableColumn<Responsible, String> nameColumn = new TableColumn<>("Username");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameDBProperty());
        table.getColumns().add(nameColumn);

        TableColumn<Responsible, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().userEmailDBProperty());
        table.getColumns().add(emailColumn);
        /*
        TableColumn<Responsible, Double> financeColumn = new TableColumn<>("Finance");
        financeColumn.setCellValueFactory(cellData -> cellData.getValue().financeProperty().asObject());
        table.getColumns().add(financeColumn);*/

        GridPane.setConstraints(table, 0, 4, 2, 1);

        grid.getChildren().addAll(nameLabel, userNameField, emailLabel, emailField,
                financeLabel, financeField, addButton, table);

        // Scene Setup
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initial load of responsibles
        refreshResponsibles();
    }

    private void addResponsible() {
        String username = userNameField.getText();
        String email = emailField.getText();
        double finance = Double.parseDouble(financeField.getText());

        Responsible newResponsible = new Responsible(username, email, finance);

        try {
            serviceResponsible.add(newResponsible);
            responsibleList.add(newResponsible);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding responsible", e.getMessage());
        }
    }

    private void refreshResponsibles() {
        try {
            List<Responsible> responsibles = serviceResponsible.readAll();
            if (responsibles != null) {
                responsibleList.clear();
                responsibleList.addAll(responsibles);
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "No responsibles found", "There are no responsibles available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error fetching responsibles", e.getMessage());
        }
    }

    private void clearFields() {
        userNameField.clear();
        emailField.clear();
        financeField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
