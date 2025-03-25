package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.Map;

public class GameScene extends Scene {
    private static GraphicsContext gc;

    public GameScene() {
        super(createRoot());
    }

    public void render(Map<LoadedImage, PixelPosition> entities) {
        gc.clearRect(0, 0, Config.getInt("window_width"), Config.getInt("window_height"));

        for (Map.Entry<LoadedImage, PixelPosition> entry : entities.entrySet()) {
            LoadedImage loadedImage = entry.getKey();
            PixelPosition pixelPosition = entry.getValue();
            double size = loadedImage.getSize();

            gc.drawImage(loadedImage.getImage(), pixelPosition.getX(), pixelPosition.getY(), size, size);
        }
    }

    private static Pane createRoot() {
        int windowWidth = Config.getInt("window_width");
        int windowHeight = Config.getInt("window_height");

        Pane root = new Pane();
        Canvas canvas = new Canvas(windowWidth, windowHeight);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(canvas);
        return root;
    }
}
