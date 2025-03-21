package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.EntityType;
import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;

public class Item extends Entity {
    private final ItemType itemType;

    public Item(ItemType itemType, Position position) {
        super(EntityType.ITEM, position);
        this.itemType = itemType;
    }

    @Override
    public Object getSubtype() {
        return itemType;
    }
}
