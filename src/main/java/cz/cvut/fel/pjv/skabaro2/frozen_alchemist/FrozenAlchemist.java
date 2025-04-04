package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.Executor;
import javafx.application.Application;
import javafx.stage.Stage;

public class FrozenAlchemist extends Application {
    @Override
    public void start(Stage stage) {
        new Executor(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
