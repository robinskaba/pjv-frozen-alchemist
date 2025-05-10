package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Handles the mapping of keys to functions and tracks the currently active keys.
 * This class allows binding functions to singular presses of a specific key.
 */
public class Controls {
    private final Map<String, Boolean> currentlyActiveKeys = new HashMap<>(); // held down keys
    private final Map<String, Runnable> boundFunctions = new HashMap<>(); // bound functions

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Handles the event of a key being pressed.
     * If the key is already active, the method does nothing to prevent repeated actions
     * from holding down the key.
     *
     * @param key The key that was pressed.
     */
    public void keyPressed(String key) {
        if (currentlyActiveKeys.containsKey(key)) return; // prevent holding down the key.
        currentlyActiveKeys.put(key, true);
        handleKey(key);
    }

    /**
     * Removes the key from the list of currently active keys.
     *
     * @param key The key that was released.
     */
    public void keyReleased(String key) {
        if (!currentlyActiveKeys.containsKey(key)) return;
        currentlyActiveKeys.remove(key);
    }

    /**
     * Binds a function to a specific key.
     * If a function is already bound to the key, the method does nothing.
     *
     * @param key The key to bind the function to.
     * @param runnable The function to be executed when the key is pressed.
     */
    public void bindFunction(String key, Runnable runnable) {
        if (boundFunctions.containsKey(key)) return; // prevent multiple functions for one key.
        boundFunctions.put(key, runnable);

        LOGGER.finer(String.format("Function bound to key: %s", key));
    }

    /**
     * Executes the function bound to the specified key, if one exists.
     *
     * @param key The key to handle.
     */
    private void handleKey(String key) {
        Runnable func = boundFunctions.get(key);
        if (func != null) func.run();

        LOGGER.finest(String.format("Handling function bound to key: %s", key));
    }
}