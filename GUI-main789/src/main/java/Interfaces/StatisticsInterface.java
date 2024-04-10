package Interfaces;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StatisticsInterface extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Statistics Interface");

        // Create sample data for the chart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Category 1", 30),
                        new PieChart.Data("Category 2", 20),
                        new PieChart.Data("Category 3", 50));

        // Create a pie chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Pie Chart");

        // Create sample data for the bar chart
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Series 1");
        series1.getData().add(new XYChart.Data<>("Category A", 10));
        series1.getData().add(new XYChart.Data<>("Category B", 20));
        series1.getData().add(new XYChart.Data<>("Category C", 30));

        // Create a bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Bar Chart");
        barChart.getData().add(series1);

        // Create a line chart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Line Chart");
        lineChart.getData().add(series1);

        // Create a VBox to hold the charts
        VBox vbox = new VBox(pieChart, barChart, lineChart);

        // Create the Scene
        Scene scene = new Scene(vbox, 800, 600);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(8);
        grid.setHgap(10);

        // Set the Scene and show the Stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
