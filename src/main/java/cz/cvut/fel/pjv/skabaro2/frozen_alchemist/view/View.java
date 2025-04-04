package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

public class View extends Pane {
    protected final GraphicsContext gc;

    public View() {
        getStylesheets().add(getClass().getResource("/styles/general.css").toExternalForm());

        Canvas canvas = new Canvas(Config.getInt("window_width"), Config.getInt("window_height"));
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }
}
