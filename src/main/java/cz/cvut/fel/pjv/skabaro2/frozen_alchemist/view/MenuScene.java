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

public class MenuScene extends Scene {
    public MenuScene(
        Runnable onPlayButtonClicked,
        Runnable onResetButtonClicked
    ) {
        super(createRoot(onPlayButtonClicked, onResetButtonClicked));
    }

    // workaround because super "has to be first statement in constructor"
    private static Pane createRoot(Runnable onPlayButtonClicked, Runnable onResetButtonClicked) {
        int windowWidth = Config.getInt("window_width");
        int windowHeight = Config.getInt("window_height");

        Pane root = new Pane();
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image background = new Image(MenuScene.class.getResourceAsStream("/ui/menu_background.png"));
        gc.drawImage(background, 0, 0, windowWidth, windowHeight);

        Button playButton = new Button("Play Game");
        Button restartButton = new Button("Restart Progress");
        playButton.setOnAction(e -> onPlayButtonClicked.run());
        restartButton.setOnAction(e -> onResetButtonClicked.run());
        playButton.setPrefWidth(125);
        restartButton.setPrefWidth(125);

        VBox buttonBox = new VBox(15, playButton, restartButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setMinWidth(windowWidth);
        buttonBox.setMinHeight(windowHeight);

        root.getChildren().addAll(canvas, buttonBox);
        return root;
    }
}
