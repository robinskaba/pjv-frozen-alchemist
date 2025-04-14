package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data;

/**
 * A record representing a rendered texture in the game.
 *
 * @param texture       The texture to be rendered.
 * @param pixelPosition The position in pixel coordinates where the texture is rendered.
 */
public record RenderedTexture(Texture texture, PixelPosition pixelPosition) {}