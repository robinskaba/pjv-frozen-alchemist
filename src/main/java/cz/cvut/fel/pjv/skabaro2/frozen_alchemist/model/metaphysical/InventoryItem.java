package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.world.types.ItemType;

// GENERAL ITEM (does not exist on the map!!)
public class InventoryItem {
    protected final String name;
    protected final String description;

    public InventoryItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "name='" + name +
                '}';
    }
}
