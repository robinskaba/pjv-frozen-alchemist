package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.exceptions.UnknownSaveCode;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.SaveConfig;

/**
 * Enum representing different types of blocks in the game.
 */
public enum BlockType implements Savable {
    FloorBlock(new SaveConfig("X", "floor_ice.png")),
    WallBlock(new SaveConfig("I", "regular_ice.png")),
    MeltableWallBlock(new SaveConfig("M", "meltable_ice.png")),
    ExitBlock(new SaveConfig("E", "exit.png")),
    ChasmBlock(new SaveConfig("C", "chasm.png")),
    RubbleBlock(new SaveConfig("R", "rubble.png")),
    Water(new SaveConfig("W", "water.png"));

    private final SaveConfig saveConfig; // The save configuration associated with the block type.

    /**
     * Constructs a BlockType enum with the specified save configuration.
     *
     * @param saveConfig The save configuration associated with the block type.
     */
    BlockType(SaveConfig saveConfig) {
        this.saveConfig = saveConfig;
    }

    @Override
    public SaveConfig getSaveConfig() {
        return saveConfig;
    }

    /**
     * Retrieves a BlockType based on its save code.
     *
     * @param saveCode The save code to look up.
     * @return The corresponding BlockType, or null if no match is found.
     */
    public static BlockType fromSaveCode(String saveCode) {
        for (BlockType blockType : BlockType.values()) {
            if (blockType.getSaveConfig().code().equals(saveCode)) return blockType;
        }
        throw new UnknownSaveCode(saveCode, EntityType.BLOCK);
    }
}