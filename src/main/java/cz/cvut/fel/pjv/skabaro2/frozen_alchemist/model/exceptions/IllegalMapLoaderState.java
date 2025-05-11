package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.exceptions;

/**
 * The IllegalMapLoaderState exception is thrown when the MapLoader encounters
 * an invalid or unexpected state during its operation.
 */
public class IllegalMapLoaderState extends IllegalStateException {

    /**
     * Constructs a new IllegalMapLoaderState exception with the specified detail message.
     *
     * @param message The detail message explaining the reason for the exception.
     */
    public IllegalMapLoaderState(String message) {
        super(message);
    }
}