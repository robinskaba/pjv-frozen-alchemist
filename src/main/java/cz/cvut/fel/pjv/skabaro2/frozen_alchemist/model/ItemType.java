package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

import java.util.Map;

public enum ItemType implements Saveable, Storable {
    // Ingredient Types
    EmberFlower("Ember Flower", "A flower that burns with a cold flame.", new SaveConfig("EF", "ember_flower.png")),
    EmptyGlassFlask("Empty Glass Flask", "A flask for holding potions.", new SaveConfig("EGF", "empty_glass_flask.png")),
    Feather("Feather", "A light and delicate feather.", new SaveConfig("F", "feather.png")),
    GlacialShard("Glacial Shard", "A shard of pure ice.", new SaveConfig("GS", "glacial_shard.png")),
    Gunpowder("Gunpowder", "Explosive powder.", new SaveConfig("G", "gunpowder.png")),
    MysticVine("Mystic Vine", "A vine with magical properties.", new SaveConfig("MV", "mystic_vine.png")),
    RabbitFoot("Rabbit Foot", "A lucky rabbit's foot.", new SaveConfig("RF", "rabbit_foot.png")),
    SulfurCrystal("Sulfur Crystal", "A crystal of sulfur.", new SaveConfig("SC", "sulfur_crystal.png")),
    VolatileSalt("Volatile Salt", "A salt that reacts violently.", new SaveConfig("VS", "volatile_salt.png")),

    // Potion Types
    PotionOfMelting("Potion of Melting", "Melts blocks.", new SaveConfig("POM", "potion_of_melting.png"),
            Map.of(EmberFlower, 1)),
    PotionOfPulverization("Potion of Pulverization", "Pulverizes obstacles.", new SaveConfig("POP", "potion_of_pulverization.png"),
            Map.of(Gunpowder, 1)),
    PotionOfLevitation("Potion of Levitation", "Allows levitation.", new SaveConfig("POL", "potion_of_levitation.png"),
            Map.of(Feather, 1)),
    PotionOfFrost("Potion of Frost", "Freezes surroundings.", new SaveConfig("POF", "potion_of_frost.png"),
            Map.of(GlacialShard, 1));

    private final String name;
    private final String description;
    private final SaveConfig saveConfig;
    private final Map<ItemType, Integer> recipe;

    ItemType(String name, String description, SaveConfig saveConfig) {
        this.name = name;
        this.description = description;
        this.saveConfig = saveConfig;
        this.recipe = null;
    }

    ItemType(String name, String description, SaveConfig saveConfig, Map<ItemType, Integer> recipe) {
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

    public boolean canCraft() {
        if (this.recipe == null) return false;
        // TODO calculate from array
        return true;
    }

    public static ItemType fromSaveCode(String saveCode) {
        for (ItemType itemType : ItemType.values()) {
            if (itemType.getSaveConfig().getCode().equals(saveCode)) return itemType;
        }
        return null;
    }
}
