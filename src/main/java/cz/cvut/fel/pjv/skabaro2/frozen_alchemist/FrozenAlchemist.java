package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.Executor;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.LogManager;

/**
 * The main entry point for the Frozen Alchemist application.
 * This class extends the JavaFX Application class and initializes the application.
 */
public class FrozenAlchemist extends Application {
    /**
     * Starts the JavaFX application.
     * This method sets up the primary stage and initiate the game executor.
     *
     * @param stage The primary stage for the application.
     */
    @Override
    public void start(Stage stage) {
        new Executor(stage);
    }

    /**
     * Application entry point - executes JavaFX.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        boolean loggingEnabled = true; // logging on by default #TODO change to false

        for (String arg : args) {
            if (arg.startsWith("--logging=")) {
                String value = arg.substring("--logging=".length()).toLowerCase();
                loggingEnabled = value.equals("true");
                break;
            }
        }

        if (!loggingEnabled) {
            LogManager.getLogManager().reset(); // disable all logging
        }

        launch(args);
    }
}