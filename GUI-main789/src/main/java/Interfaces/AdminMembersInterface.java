package Interfaces;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Models.Member;
import Services.ServiceMember;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class AdminMembersInterface extends Application {

    private final ServiceMember serviceMember = new ServiceMember();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Admin Members Interface");

        // Create a pie chart to display membership distribution
        PieChart membershipChart = new PieChart();
        membershipChart.setTitle("Membership Distribution");
        ObservableList<PieChart.Data> membershipData = FXCollections.observableArrayList();
        membershipData.addAll(
                new PieChart.Data("Gold", countMembersByMembership("Gold")),
                new PieChart.Data("Silver", countMembersByMembership("Silver")),
                new PieChart.Data("Bronze", countMembersByMembership("Bronze")),
                new PieChart.Data("Platinum", countMembersByMembership("Platinum"))
        );
        membershipChart.setData(membershipData);

        TableView<Member> table = new TableView<>();
        ObservableList<Member> data = FXCollections.observableArrayList();

        TableColumn<Member, String> userNameCol = new TableColumn<>("Username");
        userNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserNameDB()));

        TableColumn<Member, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUserEmailDB()));

        TableColumn<Member, String> membershipCol = new TableColumn<>("Membership");
        membershipCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMembership()));

        TableColumn<Member, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAge()));

        TableColumn<Member, Double> heightCol = new TableColumn<>("Height");
        heightCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHeight()));

        TableColumn<Member, Double> weightCol = new TableColumn<>("Weight");
        weightCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getWeight()));

        TableColumn<Member, Date> scheduleCol = new TableColumn<>("Schedule");
        scheduleCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSchedule()));

        table.getColumns().addAll(userNameCol, emailCol, membershipCol, ageCol, heightCol, weightCol, scheduleCol);

        try {
            data.addAll(serviceMember.readAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(data);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            Member selectedMember = table.getSelectionModel().getSelectedItem();
            if (selectedMember != null) {
                try {
                    serviceMember.delete(selectedMember);
                    data.remove(selectedMember);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Please select a member to delete.");
                alert.showAndWait();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            primaryStage.close();
            try {
                new AdminInterface().start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Create an HBox to hold the table and buttons horizontally
        HBox hbox = new HBox(10, table, new VBox(deleteButton, backButton));

        // Create a VBox to hold the HBox and the pie chart vertically
        VBox root = new VBox(10, hbox, membershipChart);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to count members based on membership type
    private int countMembersByMembership(String membership) {
        int count = 0;
        try {
            List<Member> members = serviceMember.readAll();
            for (Member member : members) {
                if (member.getMembership().equals(membership)) {
                    count++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
