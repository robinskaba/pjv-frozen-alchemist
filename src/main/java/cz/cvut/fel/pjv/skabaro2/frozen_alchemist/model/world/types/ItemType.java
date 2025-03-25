package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types;

public enum ItemType {
    PotionOfMelting("Potion of Melting", "A potion that melts ice and snow on contact.", "POM"),
    PotionOfPulverization("Potion of Pulverization", "A potion that shatters brittle objects into dust.", "POP"),
    PotionOfLevitation("Potion of Levitation", "A potion that allows the user to hover above the ground.", "POL"),
    PotionOfFrost("Potion of Frost", "A potion that freezes water and weakens certain materials.", "POF"),

    EmberFlower("Ember Flower", "A rare flower that radiates warmth and glows faintly.", "EF"),
    EmptyGlassFlask("Empty Glass Flask", "A simple flask used for storing various liquids.", "EGF"),
    Feather("Feather", "A light feather, possibly from a bird or magical creature.", "F"),
    GlacialShard("Glacial Shard", "A sharp, frozen fragment of ice infused with magic.", "GS"),
    Gunpowder("Gunpowder", "A volatile substance used for explosives and alchemy.", "G"),
    MysticVine("Mystic Vine", "A mysterious vine with alchemical properties.", "MV"),
    RabbitFoot("Rabbit Foot", "A token of luck, sometimes used in rituals.", "RF"),
    SulfurCrystal("Sulfur Crystal", "A yellow crystal with a pungent smell, used in alchemy.", "SC"),
    VolatileSalt("Volatile Salt", "A reactive compound used in explosive mixtures.", "VS");

    private final String name;
    private final String description;
    private final String saveShortcut;

    ItemType(String fullName, String description, String saveShortcut) {
        this.name = fullName;
        this.description = description;
        this.saveShortcut = saveShortcut;
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
}
