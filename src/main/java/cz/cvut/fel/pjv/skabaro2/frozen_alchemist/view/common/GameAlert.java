package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.common;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.util.logging.Logger;

/**
 * A custom alert dialog for the game, extending the JavaFX Alert class.
 * This class allows setting the owner of the alert dialog to a specific scene's window.
 */
public class GameAlert extends Alert {
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Constructs a new GameAlert instance.
     *
     * @param ownerScene The scene whose window will own this alert dialog.
     * @param alertType  The type of alert (e.g., INFORMATION, WARNING, ERROR).
     */
    public GameAlert(Scene ownerScene, AlertType alertType) {
        super(alertType);

        // for setting the alert dialog icon to the game icon
        initOwner(ownerScene.getWindow());

        LOGGER.fine("Game alert created.");
    }
}