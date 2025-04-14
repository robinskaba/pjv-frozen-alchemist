package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.SaveConfig;

/**
 * Represents an interface for objects that can be saved.
 * Classes implementing this interface must provide a method to retrieve their save configuration.
 */
public interface Savable {
    /**
     * Retrieves the save configuration for the object.
     *
     * @return The save configuration of the object.
     */
    SaveConfig getSaveConfig();
}