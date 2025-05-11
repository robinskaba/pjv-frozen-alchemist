package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.exceptions.UnknownSaveCode;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.SaveConfig;

import java.util.Map;

/**
 * Enum representing different types of items in the game.
 */
public enum ItemType implements Savable, Storable {
    EmberFlower(
            "Ember Flower",
            "A flower that burns with a cold flame.",
            new SaveConfig("EF", "ember_flower.png")
    ),
    EmptyGlassFlask(
            "Empty Glass Flask",
            "A flask for holding potions.",
            new SaveConfig("EGF", "empty_glass_flask.png")
    ),
    Feather(
            "Feather",
            "A light and delicate feather. Makes you feel like flying.",
            new SaveConfig("F", "feather.png")
    ),
    GlacialShard(
            "Glacial Shard",
            "A shard of pure ice. Very chilling.",
            new SaveConfig("GS", "glacial_shard.png")
    ),
    Gunpowder(
            "Gunpowder",
            "Explosive powder. Be careful with it.",
            new SaveConfig("G", "gunpowder.png")
    ),
    MysticVine(
            "Mystic Vine",
            "A vine with magical properties.",
            new SaveConfig("MV", "mystic_vine.png")
    ),
    RabbitFoot(
            "Rabbit Foot",
            "Where is the difference between a long jump and flying?",
            new SaveConfig("RF", "rabbit_foot.png")
    ),
    SulfurCrystal(
            "Sulfur Crystal",
            "A crystal of sulfur. It smells bad.",
            new SaveConfig("SC", "sulfur_crystal.png")
    ),
    VolatileSalt(
            "Volatile Salt",
            "A salt that reacts violently. Handle with care.",
            new SaveConfig("VS", "volatile_salt.png")
    ),

    PotionOfMelting(
            "Potion of Melting",
            "Some blocks are beginning to melt, this will finish the job.",
            new SaveConfig("POM", "potion_of_melting.png"),
            Map.of(EmptyGlassFlask, 1, MysticVine, 1, EmberFlower, 1, SulfurCrystal, 1)
    ),
    PotionOfPulverization(
            "Potion of Pulverization",
            "Pulverizes certain obstacles, even a large pile of rubble.",
            new SaveConfig("POP", "potion_of_pulverization.png"),
            Map.of(EmptyGlassFlask, 1, Gunpowder, 2, VolatileSalt, 1)
    ),
    PotionOfLevitation(
            "Potion of Levitation",
            "Allows levitation over the deep chasms.",
            new SaveConfig("POL", "potion_of_levitation.png"),
            Map.of(EmptyGlassFlask, 1, RabbitFoot, 1, Feather, 1)
    ),
    PotionOfFrost(
            "Potion of Frost",
            "Ever heard of the frozen Bering Strait?",
            new SaveConfig("POF", "potion_of_frost.png"),
            Map.of(EmptyGlassFlask, 1, MysticVine, 1, GlacialShard, 2)
    );

    private final String name;
    private final String description;
    private final SaveConfig saveConfig;
    private final Map<ItemType, Integer> recipe;

    /**
     * @param name The name of the item.
     * @param description A brief description of the item.
     * @param saveConfig The save configuration for the item.
     */
    ItemType(
        String name,
        String description,
        SaveConfig saveConfig
    ) {
        this.name = name;
        this.description = description;
        this.saveConfig = saveConfig;
        this.recipe = null;
    }

    /**
     * @param name The name of the item.
     * @param description A brief description of the item.
     * @param saveConfig The save configuration for the item.
     * @param recipe The crafting recipe for the item.
     */
    ItemType(
        String name,
        String description,
        SaveConfig saveConfig,
        Map<ItemType, Integer> recipe
    ) {
        this.name = name;
        this.description = description;
        this.saveConfig = saveConfig;
        this.recipe = recipe;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public Map<ItemType, Integer> getRecipe() {
        return recipe;
    }

    /**
     * Returns an ItemType that uses the passed save code.
     *
     * @param saveCode The save code to look up.
     * @return The corresponding ItemType, or null if no match is found.
     */
    public static ItemType fromSaveCode(String saveCode) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getSaveConfig().code().equals(saveCode)) return itemType;
        }
        throw new UnknownSaveCode(saveCode, EntityType.ITEM);
    }
}