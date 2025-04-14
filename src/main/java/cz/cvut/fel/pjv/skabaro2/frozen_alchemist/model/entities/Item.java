package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.entities;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.data.Position;

/**
 * Represents an item entity in the game, which is a specific type of entity with an item type.
 */
public class Item extends Entity {
    private final ItemType itemType;

    /**
     * Constructs a new Item object.
     *
     * @param itemType The type of the item.
     * @param position The position of the item in the world.
     */
    public Item(ItemType itemType, Position position) {
        super(EntityType.ITEM, position);
        this.itemType = itemType;
    }

    /**
     * Gets the subtype of the item, which is its item type.
     *
     * @return The item type of the item.
     */
    @Override
    public Object getSubType() {
        return itemType;
    }
}