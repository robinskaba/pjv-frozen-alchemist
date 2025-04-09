package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class GameAlert extends Alert {
    public GameAlert(Scene ownerScene, AlertType alertType) {
        super(alertType);

        initOwner(ownerScene.getWindow());
//        Stage stage = (Stage) getDialogPane().getScene().getWindow();
//        stage.getIcons().add(new Image(getClass().getResourceAsStream("/ui/game_icon.png"))); // To add an icon
    }
}
