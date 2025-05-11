package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.exceptions;

/**
 * The IllegalLevelStructure exception is thrown when the structure of a level
 * in the game does not meet the required or expected format.
 */
public class IllegalLevelStructure extends RuntimeException {

    /**
     * Constructs a new IllegalLevelStructure exception with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public IllegalLevelStructure(String message) {
        super(message);
    }
}