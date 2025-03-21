package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.GameScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.MenuScene;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.Screen;

public class Game {
    private Screen screen;

    public Game(Screen screen) {
        this.screen = screen;
    }

    public void initiate() {
        MenuScene menuScene = new MenuScene(this::playGame, this::resetProgress);
        screen.setScene(menuScene);
    }

    private void playGame() {
        // TODO load map and tp to scene
        System.out.println("Playing Game");

        GameScene gameScene = new GameScene(this::playGame, this::resetProgress);

        screen.setScene(gameScene);
    }

    private void resetProgress() {
        // TODO reset player progress
        System.out.println("Reset Progress");
    }
}
