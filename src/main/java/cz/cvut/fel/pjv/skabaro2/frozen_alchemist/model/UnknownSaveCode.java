package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.EntityType;

/**
 * Exception thrown when an unknown save code is encountered for a specific entity type.
 * This exception is used to indicate that the provided save code does not match any
 * known or expected save codes for the given entity type.
 */
public class UnknownSaveCode extends IllegalArgumentException {

    /**
     * Constructs a new UnknownSaveCode exception with a detailed error message.
     *
     * @param saveCode   The unknown save code that caused the exception.
     * @param entityType The type of entity for which the save code was expected.
     */
    public UnknownSaveCode(String saveCode, EntityType entityType) {
        super(String.format("Unknown save code <%s> for entities of type <%s>.", saveCode, entityType.toString()));
    }
}