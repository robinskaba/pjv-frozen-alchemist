package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.Executor;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * The main entry point for the Frozen Alchemist application.
 * This class extends the JavaFX Application class and initializes the application.
 */
public class FrozenAlchemist extends Application {
    public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
        System.out.println(args.length);
        if (args.length > 0) {
            setUpLogger(args[0]);
        } else {
            setUpLogger("off");
        }

        launch(args);
    }

    private static void setUpLogger(String level) {
        Level logLvl;
        switch (level) {
            case "severe":
                logLvl = Level.SEVERE;
                System.out.println("Logger level is SEVERE");
                break;
            case "info":
                logLvl = Level.INFO;
                System.out.println("Logger level is INFO");
                break;
            case "config":
                logLvl = Level.CONFIG;
                System.out.println("Logger level is CONFIG");
                break;
            case "fine":
                logLvl = Level.FINE;
                System.out.println("Logger level is FINE");
                break;
            case "finer":
                logLvl = Level.FINER;
                System.out.println("Logger level is FINER");
                break;
            case "finest":
                logLvl = Level.FINEST;
                System.out.println("Logger level is FINEST");
                break;
            case "all":
                logLvl = Level.ALL;
                System.out.println("Logger level is ALL");
                break;
            case "off":
                logLvl = Level.OFF;
                System.out.println("Logger is OFF");
                break;
            default:
                logLvl = Level.WARNING;
                System.out.println("Logger level is WARNING");
                break;
        }
        LogManager.getLogManager().reset();
        LOGGER.setLevel(logLvl);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(logLvl);
        LOGGER.addHandler(handler);
    }
}