package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.TextureManager;
import javafx.scene.image.Image;

import java.io.InputStream;

public class Texture {
    private final Image image;
    private final float scale;
    private final double size;

    public Texture(String relativePath, float scale) {
        InputStream resourceStream = TextureManager.class.getResourceAsStream("/textures/" + relativePath);
        if (resourceStream == null) throw new IllegalArgumentException("Resource not found: " + relativePath);

        this.image = new Image(resourceStream);
        this.scale = scale;
//        this.size = Config.getInt("tile_size_in_pixels") * scale;
        this.size = 64 * scale; // TODO make customizable
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
