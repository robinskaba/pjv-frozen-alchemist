package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.BlockType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.EntityType;

public class Block extends Entity {
    private final BlockType blockType;

    public Block(BlockType blockType, Position position) {
        super(EntityType.BLOCK, position);
        this.blockType = blockType;
    }

    @Override
    public Object getSubtype() {
        return blockType;
    }
}
