package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.TextureManager;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.List;

public class GameScene extends Scene {
    private final int widthInTiles = 16;
    private final int heightInTiles = 9;
    private final int tileSizeInPixels = 64;

    private static GraphicsContext gc;

    public GameScene() {
        super(createRoot());
    }

    public void render(List<Entity> entities) {
        gc.clearRect(0, 0, Config.getInt("window_width"), Config.getInt("window_height"));

        for (Entity entity : entities) {
            LoadedImage loadedImage = TextureManager.getLoadedImage(entity);
            Position position = entity.getPosition();

            // calculating offset because some textures are too large
            double offset = loadedImage.getScale() < 1f ?
                (tileSizeInPixels - loadedImage.getScale() * tileSizeInPixels) / 2f : 0;
            double x = position.getX() * tileSizeInPixels + offset;
            double y = position.getY() * tileSizeInPixels + offset;
            double size = loadedImage.getScale() * tileSizeInPixels;

            gc.drawImage(loadedImage.getImage(), x, y, size, size);
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
