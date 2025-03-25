package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic.Controls;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.logic.MovementHandler;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.MapLoader;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.GameScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.MenuScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.Screen;
import javafx.animation.AnimationTimer;

public class Game {
    private Screen screen;
    private AnimationTimer gameLoop;

    public Game(Screen screen) {
        this.screen = screen;
    }

    public void initiate() {
//        MenuScene menuScene = new MenuScene(this::playGame, this::resetProgress);
//        screen.setScene(menuScene);
        playGame(); // testing
    }

    private void update() {
    }

    private void playGame() {
        GameScene gameScene = new GameScene();
        screen.setScene(gameScene);

        Controls controls = new Controls();
        Player player = new Player(Direction.RIGHT, new Position(0, 0));
        MapLoader mapLoader = new MapLoader();
        GameMap gameMap = mapLoader.buildMap(1, player); // gameMap is map of one level
        new MovementHandler(gameMap, player, controls);

        // pass pressed / released key to Controls
        gameScene.setOnKeyPressed(e -> {
            controls.keyPressed(e.getCode().toString());
        });
        gameScene.setOnKeyReleased(e -> {
            controls.keyReleased(e.getCode().toString());
        });

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                gameScene.render(gameMap.getEntities());
            }
        };

        gameLoop.start();
    }

    private void resetProgress() {
        // TODO reset player progress
        System.out.println("Reset Progress");
    }
}
