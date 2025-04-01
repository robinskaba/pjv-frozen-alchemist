package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types;

public enum ItemType {
    PotionOfMelting("Potion of Melting", "A potion that melts ice and snow on contact.", "POM", ItemCategory.POTION),
    PotionOfPulverization("Potion of Pulverization", "A potion that shatters brittle objects into dust.", "POP", ItemCategory.POTION),
    PotionOfLevitation("Potion of Levitation", "A potion that allows the user to hover above the ground.", "POL", ItemCategory.POTION),
    PotionOfFrost("Potion of Frost", "A potion that freezes water and weakens certain materials.", "POF", ItemCategory.POTION),

    EmberFlower("Ember Flower", "A rare flower that radiates warmth and glows faintly.", "EF", ItemCategory.INGREDIENT),
    EmptyGlassFlask("Empty Glass Flask", "A simple flask used for storing various liquids.", "EGF", ItemCategory.INGREDIENT),
    Feather("Feather", "A light feather, possibly from a bird or magical creature.", "F", ItemCategory.INGREDIENT),
    GlacialShard("Glacial Shard", "A sharp, frozen fragment of ice infused with magic.", "GS", ItemCategory.INGREDIENT),
    Gunpowder("Gunpowder", "A volatile substance used for explosives and alchemy.", "G", ItemCategory.INGREDIENT),
    MysticVine("Mystic Vine", "A mysterious vine with alchemical properties.", "MV", ItemCategory.INGREDIENT),
    RabbitFoot("Rabbit Foot", "A token of luck, sometimes used in rituals.", "RF", ItemCategory.INGREDIENT),
    SulfurCrystal("Sulfur Crystal", "A yellow crystal with a pungent smell, used in alchemy.", "SC", ItemCategory.INGREDIENT),
    VolatileSalt("Volatile Salt", "A reactive compound used in explosive mixtures.", "VS", ItemCategory.INGREDIENT),;

    private final String name;
    private final String description;
    private final String saveShortcut;
    private final ItemCategory category;

    ItemType(String fullName, String description, String saveShortcut, ItemCategory category) {
        this.name = fullName;
        this.description = description;
        this.saveShortcut = saveShortcut;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSaveShortcut() {
        return saveShortcut;
    }

    public ItemCategory getCategory() {
        return category;
    }
}
