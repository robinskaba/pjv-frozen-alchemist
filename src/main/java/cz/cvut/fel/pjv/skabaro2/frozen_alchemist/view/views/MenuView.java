package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.views;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import java.io.InputStream;
import java.util.Objects;

/**
 * Represents a menu view in the game, including a background image and a vertical box for buttons.
 */
public class MenuView extends View {
    private VBox buttonBox;

    /**
     * Constructs a new MenuView instance.
     *
     * @param backgroundImagePath The path to the background image for the menu.
     * @throws RuntimeException If the background image cannot be found at the specified path.
     */
    public MenuView(int width, int height, String backgroundImagePath) {
        super(width, height);

        getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/menu_scene.css")).toExternalForm());
        setupBackground(backgroundImagePath);
        setupButtonBox();
    }

    /**
     * Sets up the background image for the menu.
     *
     * @param backgroundImagePath The path to the background image.
     * @throws RuntimeException If the background image cannot be found at the specified path.
     */
    private void setupBackground(String backgroundImagePath) {
        InputStream backgroundImageStream = MenuView.class.getResourceAsStream(backgroundImagePath);
        if (backgroundImageStream == null) throw new RuntimeException("Background image not found at path: " + backgroundImagePath);
        Image background = new Image(backgroundImageStream);
        gc.drawImage(background, 0, 0, width, height);
    }

    /**
     * Sets up the vertical box for laying out the menu buttons.
     * The box is centered horizontally and positioned near the bottom of the window.
     */
    private void setupButtonBox() {
        buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefWidth(250);

        buttonBox.setLayoutX((width - buttonBox.getPrefWidth()) / 2);
        buttonBox.setLayoutY(height - 250);

        this.getChildren().add(buttonBox);
    }

    /**
     * Adds a button to the menu.
     *
     * @param buttonText The text to display on the button.
     * @param onClick    The function to execute when the button is clicked.
     */
    public void addButton(String buttonText, Runnable onClick) {
        Button button = new Button(buttonText);
        button.setMaxWidth(Double.MAX_VALUE);

        // binding button click to a set function
        button.setOnAction(e -> onClick.run());

        buttonBox.getChildren().add(button);
    }
}