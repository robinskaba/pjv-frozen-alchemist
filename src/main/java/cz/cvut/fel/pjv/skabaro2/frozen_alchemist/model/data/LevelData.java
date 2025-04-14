package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Block;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities.Item;

/**
 * Represents the loaded data for a level in the game, including blocks, items, and the player's initial position.
 */
public class LevelData {
    private final Block[] blocks;
    private final Item[] items;
    private final Position initialPlayerPosition;

    /**
     * Creates a new LevelData object.
     *
     * @param blocks An array of blocks present in the level.
     * @param items An array of items present in the level.
     * @param initialPlayerPosition The initial position of the player in the level.
     */
    public LevelData(Block[] blocks, Item[] items, Position initialPlayerPosition) {
        this.blocks = blocks;
        this.items = items;
        this.initialPlayerPosition = initialPlayerPosition;
    }

    public Block[] getBlocks() {
        return blocks;
    }

    public Item[] getItems() {
        return items;
    }

    public Position getInitialPlayerPosition() {
        return initialPlayerPosition;
    }
}