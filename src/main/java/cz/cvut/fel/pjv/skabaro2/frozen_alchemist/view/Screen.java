package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.InputStream;

public class Screen {
    private final Stage stage;

    public Screen(Stage stage, String gameName, String gameIconRelativePath) {
        this.stage = stage;

        this.stage.setTitle(gameName);
        this.stage.setResizable(false);
        this.stage.setWidth(Config.getDouble("window_width"));
        this.stage.setHeight(Config.getDouble("window_height"));

        InputStream iconStream = getClass().getResourceAsStream(gameIconRelativePath);
        if (iconStream == null) {
            System.err.println("Game icon not found at path: " + gameIconRelativePath);
        } else {
            Image icon = new Image(iconStream);
            this.stage.getIcons().add(icon);
        }

        loadFonts();

        this.stage.show();
    }

    public void setScene(Scene scene) {
        this.stage.setScene(scene);
    }

    private void loadFonts() {
        // loading font (workaround around javafx %20 space bug)
        Font.loadFont(getClass().getResourceAsStream("/fonts/minecraft.ttf"), 12);
    }
}
