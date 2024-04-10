package Interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminInterface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("User Interface");

        BorderPane borderPane = new BorderPane();

        // Header
        HBox header = new HBox();
        header.setPadding(new Insets(10, 10, 10, 10));
        // header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #336699;");

        // List of options
        ListView<String> optionsListView = new ListView<>();
        optionsListView.getItems().addAll("Home", "Schedule", "See Coachs", "See Members");
        // Set the size of the ListView
        optionsListView.setPrefWidth(400);
        optionsListView.setPrefHeight(40);

        optionsListView.setOrientation(Orientation.HORIZONTAL); // Set the orientation to horizontal

        optionsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                switch (newValue) {
                    case "Home":
                        openInterface(new AdminInterface(), primaryStage);
                        break;
                    case "Schedule":
                        openInterface(new AdminScheduleInterface(), primaryStage);
                        break;
                    case "See Coachs":
                        openInterface(new AdminCoachsInterface(), primaryStage);
                        break;
                    case "See Members":
                        openInterface(new AdminMembersInterface(), primaryStage);
                        break;
                    default:
                        break;
                }
            }
        });

        // Set the list view as the center of the header
        header.getChildren().add(optionsListView);

        // Set the header as the top of the border pane
        borderPane.setTop(header);

        // Center content
        VBox root = new VBox();
        Scene scene = new Scene(borderPane, 800, 600);

        // Connect to the database
        String url = "jdbc:mysql://localhost:3306/esprit";
        String login = "root";
        String pwd = "";
        Connection connection = DriverManager.getConnection(url, login, pwd);

        // Query for training session data
        String trainingQuery = "SELECT session_name, participants FROM training_sessions";
        Statement trainingStatement = connection.createStatement();
        ResultSet trainingResultSet = trainingStatement.executeQuery(trainingQuery);

        // Create a bar chart for training sessions
        CategoryAxis trainingXAxis = new CategoryAxis();
        NumberAxis trainingYAxis = new NumberAxis();
        BarChart<String, Number> trainingChart = new BarChart<>(trainingXAxis, trainingYAxis);
        trainingChart.setTitle("Training Sessions");
        trainingXAxis.setLabel("Session");
        trainingYAxis.setLabel("Participants");

        XYChart.Series<String, Number> trainingSeries = new XYChart.Series<>();
        while (trainingResultSet.next()) {
            String sessionName = trainingResultSet.getString("session_name");
            int participants = trainingResultSet.getInt("participants");
            trainingSeries.getData().add(new XYChart.Data<>(sessionName, participants));
        }
        trainingChart.getData().add(trainingSeries);

        // Query for finance data
        String financeQuery = "SELECT description, amount FROM finance";
        Statement financeStatement = connection.createStatement();
        ResultSet financeResultSet = financeStatement.executeQuery(financeQuery);

        // Create a bar chart for finance
        CategoryAxis financeXAxis = new CategoryAxis();
        NumberAxis financeYAxis = new NumberAxis();
        BarChart<String, Number> financeChart = new BarChart<>(financeXAxis, financeYAxis);
        financeChart.setTitle("Finance");
        financeXAxis.setLabel("Transaction");
        financeYAxis.setLabel("Amount");

        XYChart.Series<String, Number> financeSeries = new XYChart.Series<>();
        while (financeResultSet.next()) {
            String description = financeResultSet.getString("description");
            double amount = financeResultSet.getDouble("amount");
            financeSeries.getData().add(new XYChart.Data<>(description, amount));
        }
        financeChart.getData().add(financeSeries);

        root.getChildren().addAll(trainingChart, financeChart);
        borderPane.setCenter(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Gym Admin Dashboard");
        primaryStage.show();
    }

    private void openInterface(Application interfaceClass, Stage primaryStage) {
        try {
            interfaceClass.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        primaryStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
