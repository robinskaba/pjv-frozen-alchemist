package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.HashMap;
import java.util.Map;

// handles functions and pressed keys - what key are pressed is decided elsewhere
public class Controls {
    private final Map<String, Boolean> currentlyActiveKeys = new HashMap<>();
    private final Map<String, Runnable> boundFunctions = new HashMap<>();

    public void keyPressed(String key) {
        if (currentlyActiveKeys.containsKey(key)) return; // prevent holding down key
        currentlyActiveKeys.put(key, true);
        handleKey(key);
    }

    public void keyReleased(String key) {
        if (!currentlyActiveKeys.containsKey(key)) return;
        currentlyActiveKeys.remove(key);
    }

    public void bindFunction(String key, Runnable runnable) {
        if (boundFunctions.containsKey(key)) return; // dont allow multiple funcs for one key
        boundFunctions.put(key, runnable);
    }

    private void handleKey(String key) {
        Runnable func = boundFunctions.get(key);
        if (func != null) func.run();
    }
}
