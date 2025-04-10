package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.data.Texture;

import java.util.EnumMap;

public class TextureManager {
    private static final EnumMap<BlockType, Texture> blockTextures = new EnumMap<>(BlockType.class);
    private static final EnumMap<ItemType, Texture> itemTextures = new EnumMap<>(ItemType.class);
    private static final EnumMap<Direction, Texture> playerOrientedTextures = new EnumMap<>(Direction.class);

    public static void load() {
        for (BlockType blockType : BlockType.values()) {
            Texture texture = new Texture(blockType.getSaveConfig().getRelativePath(), 1f);
            blockTextures.put(blockType, texture);
        }

        for (ItemType itemType : ItemType.values()) {
            Texture texture = new Texture(itemType.getSaveConfig().getRelativePath(), 0.6f);
            itemTextures.put(itemType, texture);
        }

        float playerSpriteScale = 0.7f;
        playerOrientedTextures.put(Direction.UP, new Texture("player_up.png", playerSpriteScale));
        playerOrientedTextures.put(Direction.DOWN, new Texture("player_down.png", playerSpriteScale));
        playerOrientedTextures.put(Direction.RIGHT, new Texture("player_right.png", playerSpriteScale));
        playerOrientedTextures.put(Direction.LEFT, new Texture("player_left.png", playerSpriteScale));
    }

    public static Texture getTexture(Object subtype) {
        if (subtype instanceof BlockType) return blockTextures.get(subtype);
        if (subtype instanceof ItemType) return itemTextures.get(subtype);
        if (subtype instanceof Direction) return playerOrientedTextures.get(subtype);

        return new Texture("missing_texture.png", 1f);
    }
}
