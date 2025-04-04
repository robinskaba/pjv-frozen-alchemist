package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

public class LevelData {
    private final Block[] blocks;
    private final Item[] items;
    private final Position initialPlayerPosition;

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
