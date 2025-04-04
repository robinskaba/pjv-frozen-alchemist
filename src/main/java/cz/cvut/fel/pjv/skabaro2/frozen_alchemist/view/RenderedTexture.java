package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view;

public class RenderedTexture {
    private final Texture texture;
    private final PixelPosition pixelPosition;

    public RenderedTexture(Texture texture, PixelPosition pixelPosition) {
        this.texture = texture;
        this.pixelPosition = pixelPosition;
    }

    public Texture getTexture() {
        return texture;
    }

    public PixelPosition getPixelPosition() {
        return pixelPosition;
    }
}
