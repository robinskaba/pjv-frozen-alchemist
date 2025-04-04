package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model;

public class Block extends Entity {
    private final BlockType blockType;

    public Block(BlockType blockType, Position position) {
        super(EntityType.BLOCK, position);
        this.blockType = blockType;
    }

    @Override
    public Object getSubType() {
        return blockType;
    }
}
