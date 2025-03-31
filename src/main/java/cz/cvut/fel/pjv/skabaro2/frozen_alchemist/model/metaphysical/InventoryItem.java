package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;

// GENERAL ITEM (does not exist on the map!!)
public class InventoryItem {
    protected final String name;
    protected final String description;
    protected final ItemType itemType;

    public InventoryItem(String name, String description, ItemType itemType) {
        this.name = name;
        this.description = description;
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "name='" + name +
                '}';
    }
}
