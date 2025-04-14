package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

/**
 * Represents an interface for objects that can be stored in the inventory.
 * Classes implementing this interface must provide methods to retrieve their name and description.
 */
public interface Storable {
    /**
     * Retrieves the name of the object.
     *
     * @return The name of the object.
     */
    String getName();

    /**
     * Retrieves the description of the object.
     *
     * @return The description of the object.
     */
    String getDescription();
}