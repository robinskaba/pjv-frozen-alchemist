package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class MenuScene extends Scene {
    public MenuScene(
        Runnable onPlayButtonClicked,
        Runnable onResetButtonClicked
    ) {
        super(createRoot(onPlayButtonClicked, onResetButtonClicked));
    }

    // workaround because super "has to be first statement in constructor"
    private static Pane createRoot(Runnable onPlayButtonClicked, Runnable onResetButtonClicked) {
        Pane root = new Pane();

        int windowWidth = 854;
        int windowHeight = 480;

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Load background image and draw it
        Image background = new Image(MenuScene.class.getResourceAsStream("/ui/menu_background.png"));
        gc.drawImage(background, 0, 0, windowWidth, windowHeight);

        // Buttons
        Button playButton = new Button("Play Game");
        Button restartButton = new Button("Restart Progress");

        playButton.setOnAction(e -> onPlayButtonClicked.run());
        restartButton.setOnAction(e -> onResetButtonClicked.run());

        playButton.setPrefWidth(125);
        restartButton.setPrefWidth(125);

        // Layout for buttons
        VBox buttonBox = new VBox(15, playButton, restartButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMinWidth(windowWidth);
        buttonBox.setMinHeight(windowHeight);

        // Add elements to root pane
        root.getChildren().addAll(canvas, buttonBox);
        return root;
    }
}
