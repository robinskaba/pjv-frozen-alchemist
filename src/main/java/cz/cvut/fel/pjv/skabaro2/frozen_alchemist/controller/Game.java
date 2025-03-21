package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.Controls;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Player;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.GameScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.MenuScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.Screen;
import javafx.animation.AnimationTimer;

public class Game {
    private Screen screen;
    private final Player player = new Player(Direction.RIGHT, new Position(0, 0));
    private GameMap gameMap;
    private AnimationTimer gameLoop;
    private Controls controls;

    public Game(Screen screen) {
        this.screen = screen;
        gameMap = new GameMap(player);
        controls = new Controls();
    }

    public void initiate() {
        MenuScene menuScene = new MenuScene(this::playGame, this::resetProgress);
//        screen.setScene(menuScene);
        playGame(); // testing
    }

    private void update() {
    }

    private void playGame() {
        GameScene gameScene = new GameScene();
        screen.setScene(gameScene);

        // pass pressed / released key to Controls
        gameScene.setOnKeyPressed(e -> {
            controls.keyPressed(e.getCode().toString());
        });
        gameScene.setOnKeyReleased(e -> {
            controls.keyReleased(e.getCode().toString());
        });

        controls.bindFunction("W", () -> movePlayer(Direction.UP));
        controls.bindFunction("S", () -> movePlayer(Direction.DOWN));
        controls.bindFunction("A", () -> movePlayer(Direction.LEFT));
        controls.bindFunction("D", () -> movePlayer(Direction.RIGHT));

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

    private void movePlayer(Direction direction) {
        Position position = player.getPosition().getPositionWithDirection(direction);
        if (!gameMap.isPositionInMap(position)) return;

        player.setPosition(position);
        player.setFacingDirection(direction);
    }
}
