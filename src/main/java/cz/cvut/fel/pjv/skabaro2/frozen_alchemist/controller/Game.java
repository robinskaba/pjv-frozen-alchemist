package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.GameMap;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.GameScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.MenuScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.Screen;
import javafx.animation.AnimationTimer;

public class Game {
    private Screen screen;
    private GameMap gameMap;
    private AnimationTimer gameLoop;

    boolean b = false;

    public Game(Screen screen) {
        this.screen = screen;
        gameMap = new GameMap();
    }

    public void initiate() {
        MenuScene menuScene = new MenuScene(this::playGame, this::resetProgress);
//        screen.setScene(menuScene);
        playGame(); // testing
    }

    private void update() {
        Direction d = b ? Direction.RIGHT : Direction.LEFT;
        b = !b;
        gameMap.testPlayerMove(d);
    }

    private void playGame() {
        GameScene gameScene = new GameScene();
        screen.setScene(gameScene);


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
