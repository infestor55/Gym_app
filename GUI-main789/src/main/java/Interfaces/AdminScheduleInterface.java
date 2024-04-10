package Interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AdminScheduleInterface extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Schedule Interface");

        TableView<Event> table = new TableView<>();
        table.setPrefWidth(600);

        TableColumn<Event, String> nameCol = new TableColumn<>("Event Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        TableColumn<Event, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("eventDate"));

        TableColumn<Event, LocalTime> startTimeCol = new TableColumn<>("Start Time");
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));

        TableColumn<Event, LocalTime> endTimeCol = new TableColumn<>("End Time");
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        TableColumn<Event, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));

        table.getColumns().addAll(nameCol, dateCol, startTimeCol, endTimeCol, locationCol);

        ObservableList<Event> data = FXCollections.observableArrayList();

        // Connect to the database and query for upcoming events
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT event_name, event_date, start_time, end_time, location FROM training_schedule WHERE event_date >= CURRENT_DATE ORDER BY event_date LIMIT 20")) {

            while (resultSet.next()) {
                String eventName = resultSet.getString("event_name");
                LocalDate eventDate = resultSet.getDate("event_date").toLocalDate();
                LocalTime startTime = resultSet.getTime("start_time").toLocalTime();
                LocalTime endTime = resultSet.getTime("end_time").toLocalTime();
                String location = resultSet.getString("location");

                data.add(new Event(eventName, eventDate, startTime, endTime, location));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setItems(data);

        // Calculate event count by location for the pie chart
        Map<String, Integer> locationCounts = new HashMap<>();
        for (Event event : data) {
            String location = event.getLocation();
            locationCounts.put(location, locationCounts.getOrDefault(location, 0) + 1);
        }

        // Create pie chart for event distribution by location
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : locationCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Event Distribution by Location");

        // Back button to return to the home interface
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            AdminInterface adminInterface = new AdminInterface();
            try {
                adminInterface.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        HBox hbox = new HBox(pieChart);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(table, hbox, backButton);

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class Event {
        private final String eventName;
        private final LocalDate eventDate;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final String location;

        public Event(String eventName, LocalDate eventDate, LocalTime startTime, LocalTime endTime, String location) {
            this.eventName = eventName;
            this.eventDate = eventDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.location = location;
        }

        public String getEventName() {
            return eventName;
        }

        public LocalDate getEventDate() {
            return eventDate;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        public String getLocation() {
            return location;
        }
    }
}
