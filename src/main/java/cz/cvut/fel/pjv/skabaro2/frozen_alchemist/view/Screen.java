package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Represents the main screen of the game, managing the stage, title, icon, and fonts loaded.
 */
public class Screen {
    private final Stage stage;
    private final int width;
    private final int height;

    /**
     * Constructs a new Screen instance.
     *
     * @param stage                 The JavaFX Stage to be used for the game window.
     * @param gameName              The title of the game to be displayed on the window.
     * @param gameIconRelativePath  The relative path to the game's icon image.
     */
    public Screen(
        Stage stage,
        int width,
        int height,
        String gameName,
        String gameIconRelativePath
    ) {
        this.stage = stage;
        this.width = width;
        this.height = height;

        // base window settings
        this.stage.setTitle(gameName);
        this.stage.setResizable(false);
        this.stage.setWidth(width);
        this.stage.setHeight(height);

        this.stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(gameIconRelativePath))));

        loadFonts();

        this.stage.show();
    }

    /**
     * Sets the scene for the stage.
     *
     * @param scene The JavaFX Scene to be displayed on the stage.
     */
    public void setScene(Scene scene) {
        this.stage.setScene(scene);
    }

    /**
     * Loads custom fonts required for the game.
     * This method is used because a workaround was needed for a JavaFX bug related to spaces in file paths.
     */
    private void loadFonts() {
        // loading font (workaround around JavaFX %20 space bug)
        Font.loadFont(getClass().getResourceAsStream("/fonts/minecraft.ttf"), 12);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}