package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Screen {
    private final int windowWidth = 854;
    private final int windowHeight = 480;

    private Stage stage;

    public Screen(Stage stage) {
        this.stage = stage;

        this.stage.setTitle("Frozen Alchemist");
        this.stage.setResizable(false);
        this.stage.setWidth(windowWidth);
        this.stage.setHeight(windowHeight);

        this.stage.show();
    }

    public void setScene(Scene scene) {
        this.stage.setScene(scene);
    }
}
