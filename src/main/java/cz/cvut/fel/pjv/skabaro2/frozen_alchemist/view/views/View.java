package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

import java.util.Objects;

/**
 * Represents a base view in the game, providing a canvas for rendering and common styling.
 */
public class View extends Pane {
    protected final int width;
    protected final int height;
    protected final GraphicsContext gc;

    /**
     * Constructs a new View instance.
     * Initializes the canvas with dimensions from the configuration and applies general styles.
     */
    public View(int width, int height) {
        this.width = width;
        this.height = height;

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/general.css")).toExternalForm());

        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }
}
