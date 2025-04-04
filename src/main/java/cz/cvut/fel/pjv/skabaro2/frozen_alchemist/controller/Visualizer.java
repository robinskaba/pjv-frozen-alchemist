package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.*;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.*;
import javafx.scene.image.Image;

import java.util.*;

public class Visualizer {
    public static RenderedTexture[] getRenderedData(Entity[] entities) {
        List<RenderedTexture> renderedTextures = new LinkedList<>();

        int i = 0;
        for (Entity entity : entities) {

            Object subtype = entity.getSubType();
            Texture texture = TextureManager.getTexture(subtype);

            // calculating offset because some textures are too large
            int tileSizeInPixels = 64;
            Position position = entity.getPosition();

            double offset = texture.getScale() < 1f ?
                    (tileSizeInPixels - texture.getScale() * tileSizeInPixels) / 2f : 0;
            double x = position.getX() * tileSizeInPixels + offset;
            double y = position.getY() * tileSizeInPixels + offset;

            PixelPosition pixelPosition = new PixelPosition(x, y);

            renderedTextures.add(new RenderedTexture(texture, pixelPosition));
            i++;
        }

        return renderedTextures.toArray(new RenderedTexture[0]);
    }


}
