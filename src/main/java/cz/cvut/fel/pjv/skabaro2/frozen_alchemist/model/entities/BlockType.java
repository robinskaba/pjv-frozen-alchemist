package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.SaveConfig;

public enum BlockType implements Savable {
    FloorBlock(new SaveConfig("X", "floor_ice.png")),
    WallBlock(new SaveConfig("I", "regular_ice.png")),
    MeltableWallBlock(new SaveConfig("M", "meltable_ice.png")),
    ExitBlock(new SaveConfig("E", "exit.png")),
    ChasmBlock(new SaveConfig("C", "chasm.png")),
    RubbleBlock(new SaveConfig("R", "rubble.png")),
    Water(new SaveConfig("W", "water.png"));

    private final SaveConfig saveConfig;

    BlockType(SaveConfig saveConfig) {
        this.saveConfig = saveConfig;
    }

    @Override
    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    public static BlockType fromSaveCode(String saveCode) {
        for (BlockType blockType : BlockType.values()) {
            if (blockType.getSaveConfig().getCode().equals(saveCode)) return blockType;
        }
        return null;
    }
}
