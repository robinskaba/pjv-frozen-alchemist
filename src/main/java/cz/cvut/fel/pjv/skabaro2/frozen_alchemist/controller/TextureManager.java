package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data.Texture;

import java.util.EnumMap;
import java.util.logging.Logger;

/**
 * The TextureManager class is responsible for managing and loading textures for various game entities,
 * including blocks, items, and player orientations.
 */
public class TextureManager {
    // main texture keeping structures
    private static final EnumMap<BlockType, Texture> blockTextures = new EnumMap<>(BlockType.class);
    private static final EnumMap<ItemType, Texture> itemTextures = new EnumMap<>(ItemType.class);
    private static final EnumMap<Direction, Texture> playerOrientedTextures = new EnumMap<>(Direction.class);

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Loads all textures for blocks, items, and player orientations.
     * This method initializes the texture maps with the appropriate textures.
     */
    public static void load() {
        // load all textures for blocks
        for (BlockType blockType : BlockType.values()) {
            Texture texture = new Texture(blockType.getSaveConfig().relativePath(), 1f);
            blockTextures.put(blockType, texture);
        }

        // load all textures for items
        for (ItemType itemType : ItemType.values()) {
            Texture texture = new Texture(itemType.getSaveConfig().relativePath(), 0.6f);
            itemTextures.put(itemType, texture);
        }

        // bind directional textures to player orientation
        float playerSpriteScale = 0.7f;
        playerOrientedTextures.put(Direction.UP, new Texture("player_up.png", playerSpriteScale));
        playerOrientedTextures.put(Direction.DOWN, new Texture("player_down.png", playerSpriteScale));
        playerOrientedTextures.put(Direction.RIGHT, new Texture("player_right.png", playerSpriteScale));
        playerOrientedTextures.put(Direction.LEFT, new Texture("player_left.png", playerSpriteScale));

        LOGGER.info("Textures loaded.");
    }

    /**
     * Retrieves the texture associated with the given entity subtype.
     *
     * @param subtype The subtype of the entity (BlockType, ItemType, or Direction).
     * @return The corresponding Texture object, or a default "missing texture" if the subtype is unknown.
     */
    public static Texture getTexture(Object subtype) {
        // returns texture based on the passed entity's subtype
        if (subtype instanceof BlockType) return blockTextures.get(subtype);
        if (subtype instanceof ItemType) return itemTextures.get(subtype);
        if (subtype instanceof Direction) return playerOrientedTextures.get(subtype);

        LOGGER.warning("Unknown subtype when loading texture: " + subtype);

        // no texture loaded for this subtype
        return new Texture("missing_texture.png", 1f);
    }
}