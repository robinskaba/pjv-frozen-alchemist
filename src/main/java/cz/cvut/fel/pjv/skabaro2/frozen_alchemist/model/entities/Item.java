package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

public class Item extends Entity {
    private final ItemType itemType;

    public Item(ItemType itemType, Position position) {
        super(EntityType.ITEM, position);
        this.itemType = itemType;
    }

    @Override
    public Object getSubType() {
        return itemType;
    }
}
