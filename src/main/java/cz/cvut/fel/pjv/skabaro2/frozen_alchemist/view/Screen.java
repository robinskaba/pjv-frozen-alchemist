package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Screen {
    private Stage stage;

    public Screen(Stage stage) {
        this.stage = stage;

        this.stage.setTitle("Frozen Alchemist");
        this.stage.setResizable(false);
        this.stage.setWidth(Config.getDouble("window_width"));
        this.stage.setHeight(Config.getDouble("window_height"));

        Image icon = new Image(getClass().getResourceAsStream("/textures/potions/potion_of_frost.png"));
        this.stage.getIcons().add(icon);

        loadFonts();

        this.stage.show();
    }

    public void setScene(Scene scene) {
        this.stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/styles/general.css").toExternalForm());
    }

    private void loadFonts() {
        // loading font (workaround around javafx %20 space bug)
        Font.loadFont(getClass().getResourceAsStream("/fonts/blockstepped.ttf"), 12);
    }
}
