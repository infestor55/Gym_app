package Interfaces;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WelcomeInterface extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Sports Application");

        // Background Image
        Image backgroundImage = new Image("file:src/images/Gymwallpaper.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background backgroundStyle = new Background(background);

        // Logo Image
        Image logoImage = new Image("file:logo.png");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(200);
        logoImageView.setPreserveRatio(true);

        // Welcome Text
        Text welcomeText = new Text("Welcome to Sporti'Z");
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        welcomeText.setFill(Color.WHITE);

        // Description Text
        Text descriptionText = new Text("“Train like an athlete, eat like a nutritionist, sleep like a baby, win like a champion”");
        descriptionText.setFont(Font.font("Arial", 20));
        descriptionText.setFill(Color.WHITE);
        descriptionText.setWrappingWidth(400);
        descriptionText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Buttons
        Button registerFirstTimeButton = createRegisterButton("Sign in");
        Button registerSecondTimeButton = createSignButton("Sign up");
        Button registerResponsibleButton = createButton("Register as Responsible");
        Button registerCoachButton = createButton("Register as Coach");
        Button registerMemberButton = createButton("Register as Member");

        // Admin Button
        Button adminButton = createAdminButton("\uD83D\uDC51");
        adminButton.setOnAction(e -> openAdminInterface(primaryStage));

        // Layout Setup
        HBox topBar = new HBox(2);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.getChildren().add(adminButton);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(backgroundStyle);
        layout.getChildren().addAll(topBar, logoImageView, welcomeText, descriptionText, registerFirstTimeButton, registerSecondTimeButton);
        layout.setPadding(new Insets(50));

        // Scene Setup
        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Button Actions
        registerFirstTimeButton.setOnAction(e -> openInterface(new SignInInterface(), primaryStage));
        registerSecondTimeButton.setOnAction(e -> openInterface(new SignUpInterface(), primaryStage));
        registerResponsibleButton.setOnAction(e -> openInterface(new ResponsibleInterface(), primaryStage));
        registerCoachButton.setOnAction(e -> openInterface(new CoachInterface(), primaryStage));
        registerMemberButton.setOnAction(e -> openInterface(new MemberInterface(), primaryStage));
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #af4c6f; -fx-text-fill: white;");
        button.setPrefSize(200, 40);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return button;
    }

    private Button createRegisterButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        button.setPrefSize(200, 40);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return button;
    }

    private Button createSignButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #15c7e0; -fx-text-fill: white;");
        button.setPrefSize(200, 40);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return button;
    }

    private Button createAdminButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #e0a015; -fx-text-fill: white;");
        button.setPrefSize(40, 40);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        return button;
    }

    private void openInterface(Application interfaceClass, Stage primaryStage) {
        try {
            interfaceClass.start(new Stage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        primaryStage.close();
    }

    private void openAdminInterface(Stage primaryStage) {
        // Add code to open the admin interface
        // For now, let's just print a message
        System.out.println("Admin Interface");
        openInterface(new SignInAdmin(), primaryStage);
    }
}
