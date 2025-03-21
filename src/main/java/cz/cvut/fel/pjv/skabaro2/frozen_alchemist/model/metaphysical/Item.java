package cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.metaphysical;

import cz.cvut.fel.pjv.skabaro2.frozen_alchemist.model.types.ItemType;

// GENERAL ITEM (does not need to exist on the map!!)
public class Item {
    protected final String name;
    protected final String description;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Item(String name, String description, ItemType itemType) {
        this.name = name;
        this.description = description;
    }
}
