package Interfaces;

import Models.Member;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ProfileMemberInterface extends Application {

    private Member member; // Member whose profile is being displayed

    public ProfileMemberInterface(Member member) {
        this.member = member;
    }
    public ProfileMemberInterface() {
        // Default constructor
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Member Profile");

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

        // Create labels to display member information
        Label nameLabel = createLabel("Username: " + (member != null ? member.getUserNameDB() : ""));
        Label emailLabel = createLabel("Email: " + (member != null ? member.getUserEmailDB() : ""));
        Label membershipLabel = createLabel("Membership: " + (member != null ? member.getMembership() : ""));
        Label ageLabel = createLabel("Age: " + (member != null ? member.getAge() : ""));
        Label heightLabel = createLabel("Height: " + (member != null ? member.getHeight() : ""));
        Label weightLabel = createLabel("Weight: " + (member != null ? member.getWeight() : ""));
        Label scheduleLabel = createLabel("Schedule: " + (member != null ? member.getSchedule() : ""));

        // Add labels to grid
        grid.addRow(0, nameLabel);
        grid.addRow(1, emailLabel);
        grid.addRow(2, membershipLabel);
        grid.addRow(3, ageLabel);
        grid.addRow(4, heightLabel);
        grid.addRow(5, weightLabel);
        grid.addRow(6, scheduleLabel);

        // Scene Setup
        Scene scene = new Scene(grid, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.SILVER); // Set text color
        return label;
    }
}
