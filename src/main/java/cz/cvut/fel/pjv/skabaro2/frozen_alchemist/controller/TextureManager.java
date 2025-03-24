package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.controller;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Direction;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities.Entity;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.view.LoadedImage;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

// maps images to entities
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
        blockTextures.put(BlockType.RegularIce, buildImage(BLOCKS, "regular_ice.png"));
        blockTextures.put(BlockType.MeltableIce, buildImage(BLOCKS, "meltable_ice.png"));
        blockTextures.put(BlockType.Chasm, buildImage(BLOCKS, "chasm.png"));
        blockTextures.put(BlockType.Rubble, buildImage(BLOCKS, "rubble.png"));
        blockTextures.put(BlockType.Exit, buildImage(BLOCKS, "exit.png"));
        blockTextures.put(BlockType.Floor, buildImage(BLOCKS, "floor_ice.png"));
        blockTextures.put(BlockType.Water, buildImage(BLOCKS, "water.png"));

        itemTextures.put(ItemType.PotionOfFrost, buildImage(POTIONS, "potion_of_frost.png"));
        itemTextures.put(ItemType.PotionOfLevitation, buildImage(POTIONS, "potion_of_levitation.png"));
        itemTextures.put(ItemType.PotionOfPulverization, buildImage(POTIONS, "potion_of_pulverization.png"));
        itemTextures.put(ItemType.PotionOfMelting, buildImage(POTIONS, "potion_of_melting.png"));

        itemTextures.put(ItemType.EmberFlower, buildImage(INGREDIENTS, "ember_flower.png"));
        itemTextures.put(ItemType.EmptyGlassFlask, buildImage(INGREDIENTS, "empty_glass_flask.png"));
        itemTextures.put(ItemType.Feather, buildImage(INGREDIENTS, "feather.png"));
        itemTextures.put(ItemType.GlacialShard, buildImage(INGREDIENTS, "glacial_shard.png"));
        itemTextures.put(ItemType.Gunpowder, buildImage(INGREDIENTS, "gunpowder.png"));
        itemTextures.put(ItemType.MysticVine, buildImage(INGREDIENTS, "mystic_vine.png"));
        itemTextures.put(ItemType.RabbitFoot, buildImage(INGREDIENTS, "rabbit_foot.png"));
        itemTextures.put(ItemType.SulfurCrystal, buildImage(INGREDIENTS, "sulfur_crystal.png"));
        itemTextures.put(ItemType.VolatileSalt, buildImage(INGREDIENTS, "volatile_salt.png"));

        playerVariantTextures.put(Direction.UP, buildImage(PLAYER_VARIANTS, "player_up.png"));
        playerVariantTextures.put(Direction.DOWN, buildImage(PLAYER_VARIANTS, "player_down.png"));
        playerVariantTextures.put(Direction.LEFT, buildImage(PLAYER_VARIANTS, "player_left.png"));
        playerVariantTextures.put(Direction.RIGHT, buildImage(PLAYER_VARIANTS, "player_right.png"));
    }

    private static Image buildImage(String folder, String fileName) {
        InputStream stream = TextureManager.class.getResourceAsStream(folder + fileName);
        if (stream == null) stream = TextureManager.class.getResourceAsStream(TEXTURES_FOLDER + "missing_texture.png");
        assert stream != null; // idea otherwise highlights because even missing texture might be missing

        return new Image(stream);
    }

    public static LoadedImage getLoadedImage(Entity entity) {
        Object subtype = entity.getSubtype();

        Image img = null;
        float scale = 1;

        if (subtype instanceof BlockType) {
            img = blockTextures.get(subtype);
        } else if (subtype instanceof ItemType) {
            img = itemTextures.get(subtype);
            scale = 0.5f;
        } else if (subtype instanceof Direction) {
            img = playerVariantTextures.get(subtype);
            scale = 0.75f;
        }

        // default to missing texture if unknown type passed
        if (img == null) img = new Image(TEXTURES_FOLDER + "missing_texture.png");

        return new LoadedImage(img, scale);
    }
}

