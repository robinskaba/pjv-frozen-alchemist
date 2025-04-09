package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

import javafx.scene.image.Image;

public class MenuItem {
    private final Image image;
    private final Runnable onItemClick;
    private final Runnable onHintClick;

    public MenuItem(Image image, Runnable onItemClick, Runnable onHintClick) {
        this.image = image;
        this.onItemClick = onItemClick;
        this.onHintClick = onHintClick;
    }

    public Image getImage() {
        return image;
    }

    public Runnable getOnItemClick() {
        return onItemClick;
    }

    public Runnable getOnHintClick() {
        return onHintClick;
    }
}