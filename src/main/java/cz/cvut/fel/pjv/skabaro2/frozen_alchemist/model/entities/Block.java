package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

/**
 * Represents a block entity in the game, which is a specific type of entity with a block type.
 */
public class Block extends Entity {
    private final BlockType blockType; // The type of the block.

    /**
     * Constructs a new Block object.
     *
     * @param blockType The type of the block.
     * @param position The position of the block in the game world.
     */
    public Block(BlockType blockType, Position position) {
        super(EntityType.BLOCK, position);
        this.blockType = blockType;
    }

    /**
     * Gets the subtype of the block, which is its block type.
     *
     * @return The block type of the block.
     */
    @Override
    public Object getSubType() {
        return blockType;
    }
}