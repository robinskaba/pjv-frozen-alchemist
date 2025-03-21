package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import javafx.scene.image.Image;

public class LoadedImage {
    private final Image image;
    private final float scale;

    public LoadedImage(Image image, float scale) {
        this.image = image;
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public Image getImage() {
        return image;
    }
}
