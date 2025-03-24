package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types;

public enum BlockType {
    RegularIce('I'),
    MeltableIce('M'),
    Chasm('C'),
    Rubble('R'),
    Water('W'),
    Exit('E'),
    Floor('X'),
    MissingBlock('€');

    private final Character saveSign;

    BlockType(Character saveSign) {
        this.saveSign = saveSign;
    }

    public Character getSaveSign() {
        return saveSign;
    }
}
