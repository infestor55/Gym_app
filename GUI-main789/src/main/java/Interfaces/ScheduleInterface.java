package Interfaces;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ScheduleInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Schedule Interface");

        BorderPane borderPane = new BorderPane();

        // Create a DatePicker for date selection
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now()); // Set the initial value to today's date

        // Add the DatePicker to the center of the BorderPane
        borderPane.setCenter(datePicker);

        // Create the Scene
        Scene scene = new Scene(borderPane, 800, 600);

        // Set the Scene and show the Stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

