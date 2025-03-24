package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types;

public enum ItemType {
    PotionOfMelting("POM"),
    PotionOfPulverization("POP"),
    PotionOfLevitation("POL"),
    PotionOfFrost("POF"),

    EmberFlower("EF"),
    EmptyGlassFlask("EGF"),
    Feather("F"),
    GlacialShard("GS"),
    Gunpowder("G"),
    MysticVine("MV"),
    RabbitFoot("RF"),
    SulfurCrystal("SC"),
    VolatileSalt("VS");

    private final String saveShortcut;

    ItemType(String saveShortcut) {
        this.saveShortcut = saveShortcut;
    }

    public String getSaveShortcut() {
        return saveShortcut;
    }
}
