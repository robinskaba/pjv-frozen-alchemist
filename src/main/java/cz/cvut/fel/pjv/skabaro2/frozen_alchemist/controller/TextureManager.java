package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class TextureManager {
    private static final String TEXTURES_FOLDER = "/textures/";
    private static final String BLOCKS = TEXTURES_FOLDER + "blocks/";
    private static final String POTIONS = TEXTURES_FOLDER + "potions/";
    private static final String INGREDIENTS = TEXTURES_FOLDER + "ingredients/";
    private static final String PLAYER_VARIANTS = TEXTURES_FOLDER + "player/";

    private static final Map<BlockType, Image> blockTextures = new HashMap<>();
    private static final Map<ItemType, Image> itemTextures = new HashMap<>();
    private static final Map<Direction, Image> playerVariantTextures = new HashMap<>();

    static {
        blockTextures.put(BlockType.RegularIce, getImage(BLOCKS, "regular_ice.png"));
        blockTextures.put(BlockType.MeltableIce, getImage(BLOCKS, "meltable_ice.png"));
        blockTextures.put(BlockType.Chasm, getImage(BLOCKS, "chasm.png"));
        blockTextures.put(BlockType.Rubble, getImage(BLOCKS, "rubble.png"));

        itemTextures.put(ItemType.PotionOfFrost, getImage(  POTIONS, "potion_of_frost.png"));
        itemTextures.put(ItemType.PotionOfLevitation, getImage(POTIONS, "potion_of_levitation.png"));
        itemTextures.put(ItemType.PotionOfPulverization, getImage(POTIONS, "potion_of_pulverization.png"));
        itemTextures.put(ItemType.PotionOfMelting, getImage(POTIONS, "potion_of_melting.png"));

        itemTextures.put(ItemType.EmberFlower, getImage(INGREDIENTS, "ember_flower.png"));
        itemTextures.put(ItemType.EmptyGlassFlask, getImage(INGREDIENTS, "empty_glass_flask.png"));
        itemTextures.put(ItemType.Feather, getImage(INGREDIENTS, "feather.png"));
        itemTextures.put(ItemType.GlacialShard, getImage(INGREDIENTS, "glacial_shard.png"));
        itemTextures.put(ItemType.Gunpowder, getImage(INGREDIENTS, "gunpowder.png"));
        itemTextures.put(ItemType.MysticVine, getImage(INGREDIENTS, "mystic_vine.png"));
        itemTextures.put(ItemType.RabbitFoot, getImage(INGREDIENTS, "rabbit_foot.png"));
        itemTextures.put(ItemType.SulfurCrystal, getImage(INGREDIENTS, "sulfur_crystal.png"));
        itemTextures.put(ItemType.VolatileSalt, getImage(INGREDIENTS, "volatile_salt.png"));

        playerVariantTextures.put(Direction.UP, getImage(PLAYER_VARIANTS, "player_up.png"));
        playerVariantTextures.put(Direction.DOWN, getImage(PLAYER_VARIANTS, "player_down.png"));
        playerVariantTextures.put(Direction.LEFT, getImage(PLAYER_VARIANTS, "player_left.png"));
        playerVariantTextures.put(Direction.RIGHT, getImage(PLAYER_VARIANTS, "player_right.png"));
    }

    private static Image getImage(String folder, String fileName) {
        InputStream stream = TextureManager.class.getResourceAsStream(folder + fileName);
        if (stream == null) stream = TextureManager.class.getResourceAsStream(TEXTURES_FOLDER + "missing_texture.png");
        assert stream != null; // idea otherwise highlights because even missing texture might be missing
        return new Image(stream);
    }

    public static Image getTexture(Entity entity) {
        Object subtype = entity.getSubtype();

        if (subtype instanceof BlockType) {
            return blockTextures.get(subtype);
        } else if (subtype instanceof ItemType) {
            return itemTextures.get(subtype);
        } else if (subtype instanceof Direction) {
            return playerVariantTextures.get(subtype);
        }

        return new Image(TEXTURES_FOLDER + "missing_texture.png");
    }
}

