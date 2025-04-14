package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

import javafx.scene.image.Image;

public record MenuItem(Image image, Runnable onItemClick, Runnable onHintClick) {}