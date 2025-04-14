package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller.TextureManager;
import javafx.scene.image.Image;

import java.io.InputStream;

/**
 * Represents a texture in the game, including its image, scale, and size.
 * The texture is loaded from a specified relative path within the resources.
 */
public class Texture {
    private final Image image;
    private final float scale;
    private final double size;

    /**
     * Constructs a new Texture instance.
     *
     * @param relativePath The relative path to the texture file within the resources.
     * @param scale        The scale factor to apply to the texture.
     * @throws IllegalArgumentException If the resource cannot be found at the specified path.
     */
    public Texture(String relativePath, float scale) {
        InputStream resourceStream = TextureManager.class.getResourceAsStream("/textures/" + relativePath);
        if (resourceStream == null) throw new IllegalArgumentException("Resource not found: " + relativePath);

        this.image = new Image(resourceStream);
        this.scale = scale;
        this.size = 64 * scale;
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