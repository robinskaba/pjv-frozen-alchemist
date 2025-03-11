package cz.cvut.fel.pjv.skabaro2.frozen_alchemist;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class FrozenAlchemist extends Application {
    private final Image birdImg = new Image("bird1.png");
    private final Image backgroundImg = new Image("background.png");

    private final double windowWidth = backgroundImg.getWidth();
    private final double windowHeight = backgroundImg.getHeight();

    @Override
    public void start(Stage stage) throws IOException {
        final Canvas canvas = new Canvas(windowWidth, windowHeight);
        StackPane root = new StackPane(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(backgroundImg, 0, 0);

        Scene scene = new Scene(root, windowWidth, windowHeight);
        stage.setTitle("Frozen Alchemist");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}