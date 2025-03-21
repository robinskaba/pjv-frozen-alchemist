package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.Game;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.Screen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class FrozenAlchemist extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Screen screen = new Screen(stage);
        Game game = new Game(screen);

        game.initiate();
    }

    public static void main(String[] args) {
        launch(args);
    }
}