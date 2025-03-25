package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.utils.Config;
import javafx.scene.image.Image;

public class LoadedImage {
    private final Image image;
    private final float scale;
    private final double size;

    public LoadedImage(Image image, float scale) {
        this.image = image;
        this.scale = scale;
        this.size = Config.getInt("tile_size_in_pixels") * scale;
    }

    public float getScale() {
        return scale;
    }

    public Image getImage() {
        return image;
    }

    public double getSize() {
        return size;
    }
}
