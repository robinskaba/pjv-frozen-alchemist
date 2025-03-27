package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Font;

import java.io.InputStream;

public class MenuScene extends Scene {
    public MenuScene(
        Runnable onPlayButtonClicked,
        Runnable onResetButtonClicked
    ) {
        super(createRoot(onPlayButtonClicked, onResetButtonClicked));

        getStylesheets().add(getClass().getResource("/styles/menu_scene.css").toExternalForm());
    }

    // workaround because super "has to be first statement in constructor"
    private static Pane createRoot(Runnable onPlayButtonClicked, Runnable onResetButtonClicked) {
        int windowWidth = Config.getInt("window_width");
        int windowHeight = Config.getInt("window_height");

        Pane root = new Pane();
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        InputStream backgroundImageStream = MenuScene.class.getResourceAsStream("/ui/background_w_title.png");
        if (backgroundImageStream == null) throw new RuntimeException("Background image not found");
        Image background = new Image(backgroundImageStream);
        gc.drawImage(background, 0, 0, windowWidth, windowHeight);

        Button playButton = new Button("Play Game");
        Button restartButton = new Button("Restart Progress");

        playButton.setOnAction(e -> onPlayButtonClicked.run());
        restartButton.setOnAction(e -> onResetButtonClicked.run());

        VBox buttonBox = new VBox(15, playButton, restartButton);
        buttonBox.setAlignment(Pos.CENTER); // Keep buttons centered within the VBox
        buttonBox.setPrefWidth(windowWidth / 2.0); // Ensures the VBox isn't stretched too wide

        double buttonOffsetY = 250; // Adjust this to move the buttons up
        buttonBox.setLayoutY(windowHeight - buttonOffsetY);
        buttonBox.setLayoutX((windowWidth - buttonBox.getPrefWidth()) / 2);

        root.getChildren().addAll(canvas, buttonBox);
        return root;
    }
}
