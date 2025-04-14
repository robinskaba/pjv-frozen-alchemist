package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

import javafx.scene.image.Image;

/**
 * A record representing a menu item in the game.
 *
 * @param image       The image associated with the menu item.
 * @param onItemClick The action to be executed when the menu item is clicked.
 * @param onHintClick The action to be executed when the hint for the menu item is clicked.
 */
public record MenuItem(Image image, Runnable onItemClick, Runnable onHintClick) {}