package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.InputStream;

public class MenuView extends View {
    private VBox buttonBox;

    public MenuView(String backgroundImagePath) {
        super();

        getStylesheets().add(getClass().getResource("/styles/menu_scene.css").toExternalForm());
        setupBackground(backgroundImagePath);
        setupButtonBox();
    }

    // workaround because super "has to be first statement in constructor"
    private void setupBackground(String backgroundImagePath) {
        int windowWidth = Config.getInt("window_width");
        int windowHeight = Config.getInt("window_height");

        System.out.println("Loading background image from path: " + backgroundImagePath);
        InputStream backgroundImageStream = MenuView.class.getResourceAsStream(backgroundImagePath);
        if (backgroundImageStream == null) {
            throw new RuntimeException("Background image not found at path: " + backgroundImagePath);
        }
        Image background = new Image(backgroundImageStream);
        gc.drawImage(background, 0, 0, windowWidth, windowHeight);
    }

    private void setupButtonBox() {
        int windowWidth = Config.getInt("window_width");
        int windowHeight = Config.getInt("window_height");

        buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER); // Keep buttons centered within the VBox
        buttonBox.setPrefWidth(windowWidth / 2.0); // Ensures the VBox isn't stretched too wide

        double buttonOffsetY = 250; // Adjust this to move the buttons up
        buttonBox.setLayoutY(windowHeight - buttonOffsetY);
        buttonBox.setLayoutX((windowWidth - buttonBox.getPrefWidth()) / 2);

        // Add buttonBox to the root pane
        this.getChildren().add(buttonBox);
    }

    public void addButton(String buttonText, Runnable onClick) {
        Button button = new Button(buttonText);
        button.setOnAction(e -> onClick.run());
        buttonBox.getChildren().add(button);
    }
}
