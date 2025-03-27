package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic.Controls;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic.Game;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical.ProgressFileManager;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.*;
import javafx.animation.AnimationTimer;

import java.util.LinkedHashMap;
import java.util.Map;

public class Executor {
    private Screen screen;
    private AnimationTimer gameLoop;

    private final Controls controls = new Controls();

    public Executor(Screen screen) {
        this.screen = screen;
        initiate();
    }

    private void initiate() {
        MenuScene menuScene = new MenuScene(this::playGame, ProgressFileManager::resetFile);
        screen.setScene(menuScene);
        //playGame(); // testing
    }

    private void playGame() {
        GameScene gameScene = new GameScene();
        Game game = new Game(controls, () -> {
            System.out.println("GAME OVER");
        });

        gameScene.setOnKeyPressed(e -> {
            controls.keyPressed(e.getCode().toString());
        });
        gameScene.setOnKeyReleased(e -> {
            controls.keyReleased(e.getCode().toString());
        });

        this.screen.setScene(gameScene);

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Map<LoadedImage, PixelPosition> renderedData = getRenderedData(game.getEntities());
                gameScene.render(renderedData);
            }
        };

        gameLoop.start();
    }

    private Map<LoadedImage, PixelPosition> getRenderedData(Entity[] entities) {
        Map<LoadedImage, PixelPosition> renderedData = new LinkedHashMap<>(); // LinkedHashMap ensures order!

        for (Entity entity : entities) {
            LoadedImage loadedImage = TextureManager.getLoadedImage(entity);

            // calculating offset because some textures are too large
            int tileSizeInPixels = 64;
            Position position = entity.getPosition();

            double offset = loadedImage.getScale() < 1f ?
                    (tileSizeInPixels - loadedImage.getScale() * tileSizeInPixels) / 2f : 0;
            double x = position.getX() * tileSizeInPixels + offset;
            double y = position.getY() * tileSizeInPixels + offset;

            PixelPosition pixelPosition = new PixelPosition(x, y);

            renderedData.put(loadedImage, pixelPosition);
        }

        return renderedData;
    }
}
