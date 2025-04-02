package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.InputStream;

public class EndScene extends Scene {
    public EndScene() {
        super(createRoot());
    }

    // workaround because super "has to be first statement in constructor"
    private static Pane createRoot() {
        int windowWidth = Config.getInt("window_width");
        int windowHeight = Config.getInt("window_height");

        Pane root = new Pane();
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        InputStream backgroundImageStream = EndScene.class.getResourceAsStream("/ui/end_screen_background.png");
        if (backgroundImageStream == null) throw new RuntimeException("End Scene background image not found");
        Image background = new Image(backgroundImageStream);
        gc.drawImage(background, 0, 0, windowWidth, windowHeight);

        root.getChildren().addAll(canvas);
        return root;
    }
}
